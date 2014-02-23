package proxy;
import genbridge.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Base;
import model.Coord;
import model.Plane;

import command.Command;
import common.NotSupportedException;


public class Proxy 
{
	// Incoming and Outcoming data manager.
	private IncomingData idm; 
	private CommandSender cm; 
	
	// Datas
	private Map<Integer,Base> bases;
	private Map<Integer,Plane> ai_planes;
	//private Map<Integer,Plane> killed_planes; // There is nothing in there
	
	private int numFrame;
	
	public Proxy(String ip, int port)
	{
		idm = new IncomingData(ip,port,this);
		ai_planes = new HashMap<Integer,Plane>();
		//killed_planes = new HashMap<Integer,Plane>();
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
		System.out.println("Looking for "+d.bases.size()+" bases");
		for (genbridge.Base b : d.bases)
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
				//FIXME Check if it's good
			}
		}
		
		// Update avions
		System.out.println("Looking for "+d.planes.size()+" planes");
		for (genbridge.Plane p : d.planes)
		{
			System.out.print(p.plane_id + " ");
		}
		System.out.println();
		for (genbridge.Plane p : d.planes)
		{
			
			if (ai_planes.containsKey(p.plane_id))
			{
				model.Plane plane = ai_planes.get(p.plane_id);
				// For now, we update the coords and set every properties a plane have
				plane._pos.x = p.posit.longit;
				plane._pos.y = p.posit.latid;
				// plane._rot = p.rotation; // Ajouter au thrift plus tard
				// plane.health = p.energy; // Not necessary for now
			}
			else
			{
				model.Plane plane = new Plane(p.plane_id, new Coord.Unique(p.posit.latid,p.posit.longit));
				ai_planes.put(plane.id, plane);
				//FIXME Check if it's good
			}
		}
		cm.newFrame(); // notify the command sender that we have a new frame
	}
	
	public ArrayList<Plane.View> getKilledPlanes()
	{
		//return killed_planes; // Not used pour le moment
		throw new NotSupportedException();
		//return null;
	}
	public int getNumFrame() 
	{
		return numFrame;
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
