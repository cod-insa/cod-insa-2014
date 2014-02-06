package proxy;
import java.util.ArrayList;

import network.CommandSender;
import network.IncomingData;
import model.PlaneModel;
import commands.Command;
import datas.Base;
import datas.Plane;


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
	}
	public ArrayList<Plane> getMyPlanes()
	{
		return ai_planes;
	}
	public ArrayList<Plane> getKilledPlanes()
	{
		return killed_planes;
	}
	public ArrayList<Base> getBases()
	{
		return bases;
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
