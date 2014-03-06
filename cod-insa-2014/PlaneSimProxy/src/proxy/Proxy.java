package proxy;
import genbridge.Data;
import genbridge.InitData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.BaseModel;
import model.Coord;
import model.PlaneModel;
import command.Command;


public class Proxy 
{
	// Incoming and Outcoming data manager.
	private IncomingData idm; 
	private CommandSender cm; 
	
	// Datas
	private Map<Integer,BaseModel> bases;
	private Map<Integer,PlaneModel> ai_planes;
	private Map<Integer,PlaneModel> killed_planes;
	
	private int numFrame;
	
	public Proxy(String ip, int port)
	{
		idm = new IncomingData(ip,port,this);
		ai_planes = new HashMap<Integer,PlaneModel>();
		killed_planes = new HashMap<Integer,PlaneModel>();
		bases = new HashMap<Integer,BaseModel>();
		
		idm.retrieveInitialData();
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
		
		System.out.println("Looking for "+d.planes.size()+" planes and the list : " + d.planes);
		
		killed_planes.putAll(ai_planes); // We put all the planes in killed_planes as if all planes were destroyed
		ai_planes.clear();
		
		// The goal is to do : killed_planes += ai_planes - planeFromData and ai_planes = planesFromData
		for (genbridge.PlaneData p : d.planes)
		{
			if (killed_planes.containsKey(p.plane_id)) // So this plane is still alive finally
			{
				PlaneModel plane = killed_planes.remove(p.plane_id);
				ai_planes.put(p.plane_id, plane); // We move it from killed plane to ai_planes
				
				// Then we update the plane with the information given by the server :
				
				plane._pos.x = p.posit.x;
				plane._pos.y = p.posit.y;
				// plane._rot = p.rotation; // Ajouter au thrift plus tard
				// plane.health = p.energy; // Not necessary for now
			}
			else // The plane wasn't existing (unknown id) so we add it to the ai_planes list
			{
				PlaneModel plane = new PlaneModel(p.plane_id, new Coord.Unique(p.posit.x,p.posit.y));
				ai_planes.put(plane.id, plane);
			}
		}
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
			planesToReturn.add(p.getView());
		return planesToReturn;
	}

	public ArrayList<PlaneModel.View> getMyPlanes()
	{
		ArrayList<PlaneModel.View> planesToReturn = new ArrayList<PlaneModel.View>();
		for (model.PlaneModel p : ai_planes.values())
			planesToReturn.add(p.getView());
		return planesToReturn;
	}
	
	public ArrayList<BaseModel.View> getBases()
	{
		ArrayList<BaseModel.View> basesToReturn = new ArrayList<BaseModel.View>();
		for (model.BaseModel b : bases.values())
			basesToReturn.add(b.getView());
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

	
}








