package proxy;
import genbridge.Data;

import java.util.ArrayList;

import model.Base;
import model.Plane;

import command.Command;


public class Proxy 
{
	// Incoming and Outcoming data manager.
	private IncomingData idm; 
	private CommandSender cm; 
	
	// Datas
	public ArrayList<Base> bases;
	public ArrayList<Plane> ai_planes;
	public ArrayList<Plane> killed_planes;
	
	public Proxy(String ip, int port)
	{
		idm = new IncomingData(ip,port,this);
		cm = new CommandSender(ip,port,idm.getIdConnection());
		ai_planes = new ArrayList<Plane>();
		killed_planes = new ArrayList<Plane>();
		bases = new ArrayList<Base>();
	}
	public ArrayList<Plane.View> getMyPlanes()
	{
		//return ai_planes;
		return null;
	}
	
	public void updateProxyData(Data d)
	{
		for (model.Base b : bases)
		{
			// TODO
		}
	}
	
	public ArrayList<Plane.View> getKilledPlanes()
	{
		//return killed_planes;
		return null;
	}
	public ArrayList<Base.View> getBases()
	{
		return null;
		//return bases;
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
