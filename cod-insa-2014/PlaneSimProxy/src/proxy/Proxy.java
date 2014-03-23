package proxy;
import genbridge.Data;
import genbridge.InitData;
import genbridge.PlaneStateData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Base;
import model.Coord;
import model.Plane;
import model.Plane.State;
import ai.AbstractAI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import command.Command;


public class Proxy 
{
	// Incoming and Outcoming data manager.
	private IncomingData idm;
	private CommandSender cm;
	private AbstractAI client_ai;
	
	// Datas
	private Map<Integer,Base> bases;
	private Map<Integer,Plane> ai_planes;
	private Map<Integer,Plane> killed_planes;
	private Map<Integer,Plane> ennemy_planes;
	private double mapWidth, mapHeight;
	
	private int player_id;
	private int numFrame;
	
	public static final Logger log = LoggerFactory.getLogger(Proxy.class);
	
	
	public Proxy(String ip, int port, AbstractAI ai)
	{
		client_ai = ai;
		idm = new IncomingData(ip,port,this);
		ai_planes = new HashMap<Integer,Plane>();
		killed_planes = new HashMap<Integer,Plane>();
		ennemy_planes = new HashMap<Integer,Plane>();
		bases = new HashMap<Integer,Base>();
		idm.retrieveInitialData();

		player_id = idm.getPlayerId();
		
		cm = new CommandSender(ip, port+1, idm.getIdConnection(), this);
		cm.start();
	}
	

	void setInitData(InitData d) {
		
		for (genbridge.BaseInitData b : d.bases)
		{
			model.Base base = new Base(b.base_id, new Coord.Unique(b.posit.x,b.posit.y));
			bases.put(base.id, base);
			//log.debug("Created base "+base.id);
			//log.debug(base.id+": "+base._pos.x+" -> "+b.posit.x);
		}
		mapWidth = d.mapWidth;
		mapHeight = d.mapHeight;
		
	}
	
	void updateProxyData(Data d)
	{
		//log.debug("Updating data...");
		
		// Update numFrame
		numFrame = d.numFrame;
		
		//log.debug("<< "+d.bases.get(0).posit.latid);
		
		// Update bases 
		for (genbridge.BaseData b : d.bases)
		{
			if (bases.containsKey(b.base_id))
			{
				model.Base base = bases.get(b.base_id); // get the base in the map

			}
			else // There is a base which is not in the bases created at the beginning
			{
				log.warn("The base id received is unknown. Ignoring the base. The model may be incoherent.");
			}
		}
		
		// Update avions
		
		//log.debug("Looking for "+d.planes.size()+" planes");
		
		killed_planes.putAll(ai_planes); // We put all the planes in killed_planes as if all planes were destroyed
		ai_planes.clear();
		
		
		// The goal is to do : killed_planes += ai_planes - planeFromData and ai_planes = planesFromData
		for (genbridge.PlaneData p : d.planes)
		{
			if (p.ai_id == player_id) // unit belongs to the ai
			{
				if (killed_planes.containsKey(p.plane_id)) // So this plane is alive
				{
					Plane plane = killed_planes.remove(p.plane_id);
					ai_planes.put(p.plane_id, plane); // We move it from killed plane to ai_planes
					
					// Then we update the plane with the information given by the server :
					
					plane.position.x = p.posit.x;
					plane.position.y = p.posit.y;
					plane.health = p.health;
					plane.ownerId = p.ai_id;
					

					plane.state = StateConverter.make(p.state);
					if (plane.state == State.AT_AIRPORT) // Update the plane 
						plane.assignTo(bases.get(p.base_id));
					else
						plane.unassign();
						
					// plane._rot = p.rotation; // Ajouter au thrift plus tard
				}
				else // The plane wasn't existing (unknown id) so we add it to the ai_planes list
				{
					Plane plane = new Plane(p.plane_id, new Coord.Unique(p.posit.x,p.posit.y), p.health, StateConverter.make(p.state));
					ai_planes.put(plane.id, plane);
				}
			}
			else // this is an ennemy unit
			{
				if (ennemy_planes.containsKey(p.plane_id)) // the unit already exists, we just update it
				{
					Plane plane = ennemy_planes.get(p.plane_id); // We update the plane
					
					plane.position.x = p.posit.x;
					plane.position.y = p.posit.y;
					plane.health = p.health;
					
					plane.state = StateConverter.make(p.state);
				}
				else // unit just appeared
				{
					Plane plane = new Plane(p.plane_id, new Coord.Unique(p.posit.x,p.posit.y), p.health, StateConverter.make(p.state));
					ennemy_planes.put(plane.id, plane);
				}
				// For ennemy_planes which were not in the data, they are not visible. We can use 
				
			}
		}
		
		cm.newFrame(); // notify the command sender that we have a new frame
	}
	
	public int getNumFrame() 
	{
		return numFrame;
	}
	
	public double getMapWidth() {
		return mapWidth;
	}

	public double getMapHeight() {
		return mapHeight;
	}
	
	public ArrayList<Plane.FullView> getKilledPlanes()
	{
		ArrayList<Plane.FullView> planesToReturn = new ArrayList<Plane.FullView>();
		for (model.Plane p : killed_planes.values())
			planesToReturn.add(p.view());
		return planesToReturn;
	}

	public ArrayList<Plane.FullView> getMyPlanes()
	{
		ArrayList<Plane.FullView> planesToReturn = new ArrayList<Plane.FullView>();
		for (model.Plane p : ai_planes.values())
			planesToReturn.add(p.view());
		return planesToReturn;
	}
	
	public ArrayList<Plane.BasicView> getEnnemyPlanes()
	{
		ArrayList<Plane.BasicView> planesToReturn = new ArrayList<Plane.BasicView>();
		for (model.Plane p : ennemy_planes.values())
			planesToReturn.add(p.restrictedView());
		return planesToReturn;
	}
	
	public ArrayList<Base.View> getBases()
	{
		ArrayList<Base.View> basesToReturn = new ArrayList<Base.View>();
		for (model.Base b : bases.values())
			basesToReturn.add(b.view());
		return basesToReturn;
	}

	public boolean isTimeOut()
	{
		return cm.isTimeOut();
	}
	
	public void updateSimFrame()
	{
		idm.updateData();
	}
	public void sendCommand(Command c)
	{
		cm.sendCommand(c);
	}

	void quit(int code)
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








