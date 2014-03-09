package proxy;
import genbridge.Data;
import genbridge.InitData;
import genbridge.PlaneStateData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.BaseModel;
import model.Coord;
import model.PlaneModel;
import ai.AbstractAI;

import command.Command;


public class Proxy 
{
	// Incoming and Outcoming data manager.
	private IncomingData idm;
	private CommandSender cm;
	private AbstractAI client_ai;
	
	// Datas
	private Map<Integer,BaseModel> bases;
	private Map<Integer,PlaneModel> ai_planes;
	private Map<Integer,PlaneModel> killed_planes;
	private Map<Integer,PlaneModel> ennemy_planes;
	
	
	private int player_id;
	private int numFrame;
	
	public Proxy(String ip, int port, AbstractAI ai)
	{
		client_ai = ai;
		idm = new IncomingData(ip,port,this);
		ai_planes = new HashMap<Integer,PlaneModel>();
		killed_planes = new HashMap<Integer,PlaneModel>();
		ennemy_planes = new HashMap<Integer,PlaneModel>();
		bases = new HashMap<Integer,BaseModel>();
		idm.retrieveInitialData();

		player_id = idm.getPlayerId();
		
		cm = new CommandSender(ip, port+1, idm.getIdConnection(), this);
		cm.start();
	}
	

	public void setInitData(InitData d) {
		
		for (genbridge.BaseInitData b : d.bases)
		{
			model.BaseModel base = new BaseModel(b.base_id, new Coord.Unique(b.posit.x,b.posit.y));
			bases.put(base.id, base);
			//System.out.println("Created base "+base.id);
			//System.out.println(base.id+": "+base._pos.x+" -> "+b.posit.x);
		}
		
	}
	
	public void updateProxyData(Data d)
	{
		//System.out.println("Updating data...");
		
		// Update numFrame
		numFrame = d.numFrame;
		
		//System.out.println("<< "+d.bases.get(0).posit.latid);
		
		// Update bases 
		for (genbridge.BaseData b : d.bases)
		{
			if (bases.containsKey(b.base_id)) // update informations
			{
				model.BaseModel base = bases.get(b.base_id); // base in the list
				// For now, just updating coords (doing nothing lol)
				
				//TODO If there is things that have to be updated for bases, it's here
				
			}
			else // There is a base which is not in the bases created at the beginning
			{
				System.err.println("Base "+b.base_id+" does not exist. You should suicide.");
			}
		}
		
		// Update avions
		
		System.out.println("Looking for "+d.planes.size()+" planes");
		
		killed_planes.putAll(ai_planes); // We put all the planes in killed_planes as if all planes were destroyed
		ai_planes.clear();
		Map<Integer,PlaneModel> nextFrameEnnemyUnits = new HashMap<Integer,PlaneModel>();
		ennemy_planes.clear();
		
		
		// The goal is to do : killed_planes += ai_planes - planeFromData and ai_planes = planesFromData
		for (genbridge.PlaneData p : d.planes)
		{
			if (p.ai_id == player_id) // unit belong to the ai
			{
				if (killed_planes.containsKey(p.plane_id)) // So this plane is still alive finally
				{
					PlaneModel plane = killed_planes.remove(p.plane_id);
					ai_planes.put(p.plane_id, plane); // We move it from killed plane to ai_planes
					
					// Then we update the plane with the information given by the server :
					
					plane.position.x = p.posit.x;
					plane.position.y = p.posit.y;
					plane.health = p.health;
					
					plane.state = StateConverter.make(p.state);
					// plane._rot = p.rotation; // Ajouter au thrift plus tard
					// plane.health = p.energy; // Not necessary for now
				}
				else // The plane wasn't existing (unknown id) so we add it to the ai_planes list
				{
					PlaneModel plane = new PlaneModel(p.plane_id, new Coord.Unique(p.posit.x,p.posit.y), p.health, StateConverter.make(p.state));
					ai_planes.put(plane.id, plane);
				}
			}
			else // this is an ennemy unit
			{
				if (ennemy_planes.containsKey(p.plane_id)) // the unit already exists, we just update it
				{
					PlaneModel plane = ennemy_planes.get(p.plane_id);
					nextFrameEnnemyUnits.put(p.plane_id, plane);
					
					plane.position.x = p.posit.x;
					plane.position.y = p.posit.y;
					plane.health = p.health;
					
					plane.state = StateConverter.make(p.state);
				}
				else // unit just appeared
				{
					PlaneModel plane = new PlaneModel(p.plane_id, new Coord.Unique(p.posit.x,p.posit.y), p.health, StateConverter.make(p.state));
					ennemy_planes.put(plane.id, plane);
				}
			}
		}
		// erase the ennemy_planes, now, all the ennemy planes are in the nextFrameEnnemyUnits
		
		ennemy_planes = nextFrameEnnemyUnits;
		
		cm.newFrame(); // notify the command sender that we have a new frame
	}
	
	public int getNumFrame() 
	{
		return numFrame;
	}
	
	public ArrayList<PlaneModel.View> getKilledPlanes()
	{
		ArrayList<PlaneModel.View> planesToReturn = new ArrayList<PlaneModel.View>();
		for (model.PlaneModel p : killed_planes.values())
			planesToReturn.add(p.view());
		return planesToReturn;
	}

	public ArrayList<PlaneModel.View> getMyPlanes()
	{
		ArrayList<PlaneModel.View> planesToReturn = new ArrayList<PlaneModel.View>();
		for (model.PlaneModel p : ai_planes.values())
			planesToReturn.add(p.view());
		return planesToReturn;
	}
	
	public ArrayList<BaseModel.View> getBases()
	{
		ArrayList<BaseModel.View> basesToReturn = new ArrayList<BaseModel.View>();
		for (model.BaseModel b : bases.values())
			basesToReturn.add(b.view());
		return basesToReturn;
	}
	public void updateSimFrame()
	{
		idm.updateData();
	}
	public void sendCommand(Command c)
	{
		cm.sendCommand(c);
	}

	public void quit(int errorCode)
	{
		if (idm != null)
			idm.terminate();
		if (cm != null)
			cm.terminate();
		if (client_ai != null) // Safelly exit the client
			client_ai.end();
		
		System.out.println("Gracefully terminated the client");
		System.exit(errorCode);
	}
	
	public static class StateConverter {
		public static PlaneModel.State make(PlaneStateData sd)
		{
			PlaneModel.State s = null;
			switch (sd)
			{
			case AT_AIRPORT:
				s = PlaneModel.State.AT_AIRPORT;
				break;
			case ATTACKING:
				s = PlaneModel.State.ATTACKING;
				break;
			case IDLE:
				s = PlaneModel.State.IDLE;
				break;
			case GOING_TO:
				s = PlaneModel.State.MOVING;
				break;
			case DEAD:
				s = PlaneModel.State.DEAD;
				break;
			case FOLLOWING:
				s = PlaneModel.State.FOLLOWING;
				break;
			}
			return s;
		}
	}
	
}








