package proxy;
import java.util.ArrayList;

import model.Base;
import model.Plane;
import network.CommandSender;
import network.IncomingData;

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
	
	public Proxy()
	{
		idm = new IncomingData();
		cm = new CommandSender();
		ai_planes = new ArrayList<Plane>();
		killed_planes = new ArrayList<Plane>();
		bases = new ArrayList<Base>();
	}
	public ArrayList<Plane.View> getMyPlanes()
	{
		//return ai_planes;
		return null; // TODO
	}
	public ArrayList<Plane.View> getKilledPlanes()
	{
		//return killed_planes;
		return null; // TODO
	}
	public ArrayList<Base.View> getBases()
	{
		//return bases;
		return null; // TODO
	}
	public void updateSimFrame()
	{
		//TODO Thread have to sleep while there is not any new datas
		
		//TODO Update ai_planes
	}
	public void sendCommand(Command c)
	{
		cm.sendCommand(c);
	}
	
}
