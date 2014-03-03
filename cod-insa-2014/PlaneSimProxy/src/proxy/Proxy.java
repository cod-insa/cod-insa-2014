package proxy;
import genbridge.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.BaseModel;
import model.Coord;
import model.PlaneModel;

import command.Command;
import common.NotSupportedException;


public class Proxy 
{
	// Incoming and Outcoming data manager.
	private IncomingData idm; 
	private CommandSender cm; 
	
	// Datas
	private Map<Integer,BaseModel> bases;
	private Map<Integer,PlaneModel> ai_planes;
	//private Map<Integer,Plane> killed_planes; // There is nothing in there
	
	private int numFrame;
	
	public Proxy(String ip, int port)
	{
		idm = new IncomingData(ip,port,this);
		ai_planes = new HashMap<Integer,PlaneModel>();
		//killed_planes = new HashMap<Integer,Plane>();
		bases = new HashMap<Integer,BaseModel>();
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
				//FIXME Check if it's good
			}
		}
		
		// Update avions
		System.out.println("Looking for "+d.planes.size()+" planes");
		for (genbridge.PlaneData p : d.planes)
		{
			System.out.print(p.plane_id + " ");
		}
		System.out.println();
		for (genbridge.PlaneData p : d.planes)
		{
			
			if (ai_planes.containsKey(p.plane_id))
			{
				model.PlaneModel plane = ai_planes.get(p.plane_id);
				// For now, we update the coords and set every properties a plane have
				plane._pos.x = p.posit.longit;
				plane._pos.y = p.posit.latid;
				// plane._rot = p.rotation; // Ajouter au thrift plus tard
				// plane.health = p.energy; // Not necessary for now
			}
			else
			{
				model.PlaneModel plane = new PlaneModel(p.plane_id, new Coord.Unique(p.posit.latid,p.posit.longit));
				ai_planes.put(plane.id, plane);
				//FIXME Check if it's good
			}
		}
		cm.newFrame(); // notify the command sender that we have a new frame
	}
	
	public ArrayList<PlaneModel.View> getKilledPlanes()
	{
		//return killed_planes; // Not used pour le moment
		throw new NotSupportedException();
		//return null;
	}
	public int getNumFrame() 
	{
		return numFrame;
	}

	public ArrayList<PlaneModel.View> getMyPlanes()
	{
		ArrayList<PlaneModel.View> planesToReturn = new ArrayList<PlaneModel.View>();
		for (model.PlaneModel p : ai_planes.values())
			planesToReturn.add(p.view);
		return planesToReturn;
	}
	
	public ArrayList<BaseModel.View> getBases()
	{
		ArrayList<BaseModel.View> basesToReturn = new ArrayList<BaseModel.View>();
		for (model.BaseModel b : bases.values())
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
