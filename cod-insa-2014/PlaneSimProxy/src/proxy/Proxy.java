package proxy;
import genbridge.Data;
import genbridge.InitData;
import genbridge.PlaneFullData;
import genbridge.PlaneStateData;
import genbridge.ProgressAxisData;

import java.util.HashMap;
import java.util.Map;

import model.Base;
import model.Coord;
import model.Plane;
import model.Plane.BasicView;
import model.Plane.State;
import model.ProgressAxis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.AbstractAI;
import command.Command;
import common.MapView;
import common.Util;


public class Proxy 
{
	// Incoming and Outcoming data manager.
	private IncomingData idm;
	private CommandSender cm;
	private AbstractAI client_ai;
	
	// Datas
	private Map<Integer,Base> all_bases;
	private Map<Integer,Base> ai_bases;
	private Map<Integer,Base> other_visible_bases;
	private Map<Integer,Base> other_notvisible_bases;
	
	private Map<Integer,Plane> ai_planes;
	private Map<Integer,Plane> killed_planes;
	private Map<Integer,Plane> ennemy_planes;
	private Map<Integer, ProgressAxis> map_axis;

	private double mapWidth, mapHeight;
	
	private int player_id;
	private int numFrame;
	
	public static final Logger log = LoggerFactory.getLogger(Proxy.class);
	
	/**
	 * Constructor that initialize the proxy
	 * @param ip Ip of the serveur
	 * @param port Port of the client
	 * @param ai Reference to the AI. Put "this" for this parameter
	 */
	public Proxy(String ip, int port, AbstractAI ai)
	{
		client_ai = ai;
		idm = new IncomingData(ip,port,this);
		ai_planes = new HashMap<Integer,Plane>();
		killed_planes = new HashMap<Integer,Plane>();
		ennemy_planes = new HashMap<Integer,Plane>();
		all_bases = new HashMap<Integer,Base>();
		ai_bases = new HashMap<Integer,Base>();
		other_visible_bases = new HashMap<Integer,Base>();
		other_notvisible_bases = new HashMap<Integer,Base>();
		map_axis = new HashMap<Integer,ProgressAxis>();
		idm.retrieveInitialData();

		player_id = idm.getPlayerId();
		
		cm = new CommandSender(ip, port+1, idm.getIdConnection(), this);
		cm.start();
	}
	

	/**
	 * Initialize the model in the client.
	 * @param d Initial data retrieved from the server
	 */
	void setInitData(InitData d) {
		
		for (genbridge.BaseInitData b : d.bases)
		{
			Base base = new Base(b.base_id, new Coord.Unique(b.posit.x,b.posit.y));
			other_notvisible_bases.put(base.id, base);
			all_bases.put(base.id, base);
		}
		mapWidth = d.mapWidth;
		mapHeight = d.mapHeight;
		
		for (genbridge.ProgressAxisInitData a : d.progressAxis)
			if (! all_bases.containsKey(a.base1_id) || ! all_bases.containsKey(a.base2_id))
				map_axis.put(a.id,new ProgressAxis(a.id, all_bases.get(a.base1_id), all_bases.get(a.base2_id)));
			else
				log.error("One or both of the base " + a.base1_id + " and " + a.base2_id + " are unknown. Failed to add the axis");
		
		// TODO Get the country
		// TODO Get the others countries
	}
	
	/**
	 * Private function to update the bases
	 * @param d Data retrieved from the server
	 */
	private void updateBases(Data d)
	{
		// Clear the bases :
		ai_bases.clear();
		other_notvisible_bases.clear();
		other_visible_bases.clear();
		
		class UpdateBasicInfo { public UpdateBasicInfo(Base base, genbridge.BaseBasicData b) {
			base.ownerId(b.base_id);
		}}
		
		class UpdateFullInfo { public UpdateFullInfo(Base base, genbridge.BaseFullData b) {
			new UpdateBasicInfo(base,b.basic_info);
			base.militaryGarrison = b.militarRessource;
			base.fuelInStock = b.fuelRessource;
		}}
		
		// Update owned bases
		for (genbridge.BaseFullData b : d.owned_bases)
		{
			int base_id = b.basic_info.base_id;
			Base base = all_bases.get(base_id);
			if (base == null)
				throw new Error("The base with id " + base_id + " does not exist");
			new UpdateFullInfo(base,b);
			ai_bases.put(base_id, base);
		}
		
		for (genbridge.BaseFullData b : d.not_owned_visible_bases)
		{
			int base_id = b.basic_info.base_id;
			Base base = all_bases.get(base_id);
			if (base == null)
				throw new Error("The base with id " + base_id + " does not exist");
			new UpdateFullInfo(base,b);
			other_visible_bases.put(base_id, base);
		}
		
		for (genbridge.BaseBasicData b : d.not_owned_not_visible_bases)
		{
			int base_id = b.base_id;
			Base base = all_bases.get(base_id);
			if (base == null)
				throw new Error("The base with id " + base_id + " does not exist");
			new UpdateBasicInfo(base,b);
			other_notvisible_bases.put(base_id, base);
		}
	}
	
	/**
	 * Private function to update the planes 
	 * @param d Data retrieved from the server
	 */
	private void updatePlanes(Data d) {
		
		// The goal is to do : killed_planes += ai_planes - planeFromData and ai_planes = planesFromData
		// So we begin by doing killed_planes = ai_planes and then ai_planes = empty

		killed_planes.putAll(ai_planes); // We put all the planes in killed_planes as if all planes were destroyed
		ai_planes.clear();
		
		class UpdateBasicInfo { public UpdateBasicInfo(Plane plane, genbridge.PlaneBasicData p) {
			plane.position.x = p.posit.x;
			plane.position.y = p.posit.y;
			plane.health = p.health;
			plane.ownerId(p.ai_id);
		}}
		
		// Closure for the poor
		class UpdateFullInfo { public UpdateFullInfo(Plane plane, PlaneFullData p) {
			new UpdateBasicInfo(plane,p.basic_info);
			plane.fuelInTank = p.remainingGaz;
			plane.militaryInHold = p.militarResourceCarried;
			plane.fuelInHold = p.fuelResourceCarried;
			
			// fireRange and radarRange not updated

			plane.state = StateConverter.make(p.state);
			if (plane.state == State.AT_AIRPORT) // Update the plane 
				plane.assignTo(all_bases.get(p.base_id));
			else
				plane.unAssign();
		}}
		
		for (genbridge.PlaneFullData p : d.owned_planes)
		{
			if (p.basic_info.ai_id != player_id) // unit belongs to the ai
				log.warn("A not owned plane is in the owned ones: may generate errors");
			
			if (killed_planes.containsKey(p.basic_info.plane_id)) // So this plane is alive
			{
				Plane plane = killed_planes.remove(p.basic_info.plane_id);
				new UpdateFullInfo(plane, p);
				ai_planes.put(p.basic_info.plane_id, plane); // We move it from killed plane to ai_planes
				// Then we update the plane with the information given by the server :
			}
			else // The plane wasn't existing (unknown id) so we add it to the ai_planes list
			{
				Plane plane = new Plane(p.basic_info.plane_id, new Coord.Unique(p.basic_info.posit.x, p.basic_info.posit.y), Plane.Type.get(p.basic_info.planeTypeId));
				new UpdateFullInfo(plane, p);
				ai_planes.put(plane.id, plane);
			}
		}

		for (genbridge.PlaneBasicData p : d.not_owned_planes)
		{
			if (p.ai_id == player_id)
				log.warn("An owned plane is in the not owned ones: may generate errors");
			
			if (ennemy_planes.containsKey(p.plane_id)) // the unit already exists, we just update it
			{
				Plane plane = ennemy_planes.get(p.plane_id); // Get the old plane and update it
				new UpdateBasicInfo(plane,p);
			}
			else // First time that the plane appears
			{
				Plane plane = new Plane(p.plane_id, new Coord.Unique(p.posit.x,p.posit.y), Plane.Type.get(p.planeTypeId));
				new UpdateBasicInfo(plane,p);
				ennemy_planes.put(plane.id, plane);
			}
		}
	}

	/**
	 * Private function to update the axis
	 * @param d Data retrieved from the server
	 */
	private void updateAxis(Data d) {
		
		for (ProgressAxisData a : d.progressAxis)
		{
			if (map_axis.containsKey(a.id))
			{
				ProgressAxis progAxis = map_axis.get(a.id);
				
				progAxis.ratio1 = a.progressBase1;
				progAxis.ratio2 = a.progressBase2;
			}
		}
	}
	
	/**
	 * Update the proxy model of the game
	 * @param d Data retrieved from the server
	 */
	void updateProxyData(Data d)
	{
		//log.debug("Updating data...");
		
		// Update numFrame
		numFrame = d.numFrame;

		updateBases(d);
		updatePlanes(d);
		updateAxis(d);
		
		// TODO Update the country (update production line)
		
		cm.newFrame(); // notify the command sender that we have a new frame
	}

	/**
	 * Get the current frame number
	 */
	public int getNumFrame() 
	{
		return numFrame;
	}

	/**
	 * Get the width of the map
	 */
	public double getMapWidth() {
		return mapWidth;
	}

	/**
	 * Get the height of the map
	 */
	public double getMapHeight() {
		return mapHeight;
	}

	/**
	 * Get the planes that no longer exists in the game. 
	 * If a plane is in this list, it means that he has been destroyed.
	 * 
	 * Warning : You should call this method only once per frame
	 */
	public MapView<Integer, Plane.FullView> getKilledPlanes()
	{
		return Util.view(killed_planes);
	}

	/**
	 * Get the planes that your AI own
	 * 
	 * Warning : You should call this method only once per frame
	 */
	public MapView<Integer, Plane.FullView> getMyPlanes()
	{
		return Util.view(ai_planes);
	}
	
	/**
	 * Get all the visible ennemy planes
	 * An ennemy plane is visible only if at least one of your entity sees it
	 * 
	 * Warning : You should call this method only once per frame
	 */
	public MapView<Integer, Plane.BasicView> getEnnemyPlanes()
	{
		return new MapView.Transform<>(ennemy_planes,new Util.Converter<Plane,Plane.BasicView>(){
			@Override
			public BasicView convert(Plane src) {
				return src.restrictedView();
			}
		});
	}
	
	/**
	 * Get all the bases of the game
	 * 
	 * You don't need to call this method more than once per game. Because no bases will be created or destroyed during the game :)
	 */
	public MapView<Integer,Base.BasicView> getAllBases()
	{
		return new MapView.Transform<>(all_bases, new Util.Converter<Base, Base.BasicView>() {
			@Override
			public Base.BasicView convert(Base src) {
			    if (other_notvisible_bases.containsKey(src.id))
			    	return src.restrictedView();
			    else
			    	return src.view();
			}
		});
	}

	/**
	 * Get the bases which your AI own
	 * 
	 * Warning : You should call this method only once per frame
	 */
	public MapView<Integer,Base.FullView> getMyBases()
	{
		return Util.view(ai_bases);
	}
	
	/**
	 * Get the bases which your AI see but does not own
	 * 
	 * Warning : You should call this method only once per frame
	 */
	public MapView<Integer,Base.FullView> getNotOwnedAndVisibleBases()
	{
		return Util.view(other_visible_bases);
	}
	
	/**
	 * Get the bases which your AI neither see nor own
	 * 
	 * Warning : You should call this method only once per frame
	 */
	public MapView<Integer,Base.BasicView> getNotOwnedAndNotVisibleBases()
	{
		return new MapView.Transform<>(other_notvisible_bases,new Util.Converter<Base,Base.BasicView>(){
			@Override
			public Base.BasicView convert(Base src) {
				return src.restrictedView();
			}
		});
	}
	
	
	/**
	 * Check if you are out of time.
	 * If this returns true, it means that your AI is probably too long to send commands
	 */
	public boolean isTimeOut()
	{
		return cm.isTimeOut();
	}

	/**
	 * This function allows you to get the next frame of the game. 
	 * Calling this function will block you until the next game frame is available on the server
	 */
	public void updateSimFrame()
	{
		idm.updateData();
	}
	
	/**
	 * Send a command to the server.
	 * Calling this function will not block you.
	 * @param c The command to be sent
	 */
	public void sendCommand(Command c)
	{
		cm.sendCommand(c);
	}

	/**
	 * Quit the proxy
	 * @param code The code that will be sen
	 */
	public void quit(int code)
	{
		if (idm != null)
			idm.terminate();
		if (client_ai != null)
			client_ai.end();
		if (cm != null)
		{
			cm.terminate();
			try {
				cm.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		log.debug("Gracefully terminated the client");
		System.exit(code);
	}
	
	static class StateConverter {
		public static Plane.State make(PlaneStateData sd)
		{
			Plane.State s = null;
			switch (sd)
			{
			case AT_AIRPORT:
				s = Plane.State.AT_AIRPORT;
				break;
			case ATTACKING:
				s = Plane.State.ATTACKING;
				break;
			case IDLE:
				s = Plane.State.IDLE;
				break;
			case GOING_TO:
				s = Plane.State.GOING_TO;
				break;
			case DEAD:
				s = Plane.State.DEAD;
				break;
			case FOLLOWING:
				s = Plane.State.FOLLOWING;
				break;
			case LANDING:
				s = Plane.State.LANDING;
				break;
			default:
				throw new Error("Unhandled plane state");
			}
			return s;
		}
	}
	
}








