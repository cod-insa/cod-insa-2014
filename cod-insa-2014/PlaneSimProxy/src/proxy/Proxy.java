package proxy;
import genbridge.Bridge;
import genbridge.Bridge.AsyncClient;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import model.Base;
import model.Plane;
import command.Command;


public class Proxy 
{
	// Incoming and Outcoming data manager.
	private IncomingData idm; 
	private CommandSender cm; 
	
	// Datas // TODO accessors
	public ArrayList<Base> bases;
	public ArrayList<Plane> ai_planes;
	public ArrayList<Plane> killed_planes;
	
	
	
	public Proxy(String ip, int port)
	{
		idm = new IncomingData(ip,port,this);
		cm = new CommandSender(ip,port,this);
		ai_planes = new ArrayList<Plane>();
		killed_planes = new ArrayList<Plane>();
		bases = new ArrayList<Base>();
	}
	public ArrayList<Plane.View> getMyPlanes()
	{
		//return ai_planes;
		return null; // TODO ???
	}
	public ArrayList<Plane.View> getKilledPlanes()
	{
		//return killed_planes;
		return null; // TODO ???
	}
	public ArrayList<Base.View> getBases()
	{
		//return bases;
		return null; // TODO ???
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
