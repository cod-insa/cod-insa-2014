package proxy;
import genbridge.Bridge;
import genbridge.Bridge.AsyncClient;
import genbridge.Data;

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
	
	// Datas
	public ArrayList<Base.View> bases;
	public ArrayList<Plane.View> ai_planes;
	public ArrayList<Plane.View> killed_planes;
	
	public Proxy(String ip, int port)
	{
		idm = new IncomingData(ip,port,this);
		cm = new CommandSender(ip,port,idm.getIdConnection());
		ai_planes = new ArrayList<Plane.View>();
		killed_planes = new ArrayList<Plane.View>();
		bases = new ArrayList<Base.View>();
	}
	public ArrayList<Plane.View> getMyPlanes()
	{
		return ai_planes;
		
	}
	
	public void updateProxyData(Data d)
	{
		for (genbridge.Base b : d.bases)
		{
			// TODO
		}
	}
	
	public ArrayList<Plane.View> getKilledPlanes()
	{
		return killed_planes;
	}
	public ArrayList<Base.View> getBases()
	{
		return bases;
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
