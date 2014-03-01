package proxy;
import genbridge.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Base;
import model.Coord;
import model.Plane;

import command.Command;


public class Proxy 
{
	// Incoming and Outcoming data manager.
	private IncomingData idm; 
	private CommandSender cm; 
	
	// Datas
	private Map<Integer,Base> bases;
	private Map<Integer,Plane> ai_planes;
	private Map<Integer,Plane> killed_planes;
	
	private int numFrame;
	
	public Proxy(String ip, int port)
	{
		idm = new IncomingData(ip,port,this);
		ai_planes = new HashMap<Integer,Plane>();
		killed_planes = new HashMap<Integer,Plane>();
		bases = new HashMap<Integer,Base>();
		idm.retrieveInitialDatas();
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
				model.Base base = bases.get(b.base_id); // base in the list
				// For now, just updating coords (doing nothing lol)
				base._pos.x = b.posit.longit;
				base._pos.y = b.posit.latid;
			}
			else
			{
				model.Base base = new Base(b.base_id, new Coord.Unique(b.posit.latid,b.posit.longit));
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
				Plane plane = killed_planes.remove(p.plane_id);
				ai_planes.put(p.plane_id, plane); // We move it from killed plane to ai_planes
				
				// Then we update the plane with the information given by the server :

				plane._pos.x = p.posit.longit;
				plane._pos.y = p.posit.latid;
				// plane._rot = p.rotation; // Ajouter au thrift plus tard
				// plane.health = p.energy; // Not necessary for now
			}
			else // The plane wasn't existing (unknown id) so we add it to the ai_planes list
			{
				Plane plane = new Plane(p.plane_id, new Coord.Unique(p.posit.latid,p.posit.longit));
				ai_planes.put(plane.id, plane);
			}
		}
		cm.newFrame(); // notify the command sender that we have a new frame
	}

	public int getNumFrame() 
	{
		return numFrame;
	}
	
	public ArrayList<Plane.View> getKilledPlanes()
	{
		ArrayList<Plane.View> planesToReturn = new ArrayList<Plane.View>();
		for (model.Plane p : killed_planes.values())
			planesToReturn.add(p.view);
		return planesToReturn;
	}

	public ArrayList<Plane.View> getMyPlanes()
	{
		ArrayList<Plane.View> planesToReturn = new ArrayList<Plane.View>();
		for (model.Plane p : ai_planes.values())
			planesToReturn.add(p.view);
		return planesToReturn;
	}
	
	public ArrayList<Base.View> getBases()
	{
		ArrayList<Base.View> basesToReturn = new ArrayList<Base.View>();
		for (model.Base b : bases.values())
			basesToReturn.add(b.view);
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
