package proxy;
import genbridge.Data;

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
		cm = new CommandSender(ip,port,idm.getIdConnection(), this);
	}
	
	public void updateProxyData(Data d)
	{
		System.out.println("Updating data...");
		
		// Update numFrame
		numFrame = d.numFrame;
		
		// Update bases 
		for (genbridge.BaseData b : d.bases)
		{
			if (bases.containsKey(b.base_id)) // update informations
			{
				model.BaseModel base = bases.get(b.base_id); // base in the list
				// For now, just updating coords (doing nothing lol)
				base._pos.x = b.posit.longit;
				base._pos.y = b.posit.latid;
			}
			else
			{
				model.BaseModel base = new BaseModel(b.base_id, new Coord.Unique(b.posit.latid,b.posit.longit));
				bases.put(base.id, base);
			}
		}
		
		// Update avions
		
		System.out.println("Looking for "+d.planes.size()+" planes");
		
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
				
				plane._pos.x = p.posit.longit;
				plane._pos.y = p.posit.latid;
				// plane._rot = p.rotation; // Ajouter au thrift plus tard
				// plane.health = p.energy; // Not necessary for now
			}
			else // The plane wasn't existing (unknown id) so we add it to the ai_planes list
			{
				PlaneModel plane = new PlaneModel(p.plane_id, new Coord.Unique(p.posit.latid,p.posit.longit));
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








