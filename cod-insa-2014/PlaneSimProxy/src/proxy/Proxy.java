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
	
	// Datas
	public ArrayList<Base> bases;
	public ArrayList<Plane> ai_planes;
	public ArrayList<Plane> killed_planes;
	
	
	private Bridge.Client initSynchroneClient(String ip, int port)
	{
		Bridge.Client client = null;
		try 
		{
	        /*
	         * Similar to the server, you can use the parameters to setup client parameters or
	         * use the default settings. On the client side, you will need a TrustStore which
	         * contains the trusted certificate along with the public key. 
	         * For this example it's a self-signed cert. 
	         */
	      /*  TSSLTransportParameters params = new TSSLTransportParameters();
	        params.setTrustStore("../../lib/java/test/.truststore", "thrift", "SunX509", "JKS");
	        *//*
	         * Get a client transport instead of a server transport. The connection is opened on
	         * invocation of the factory method, no need to specifically call open()
	         */
	        /*transport = TSSLTransportFactory.getClientSocket("localhost", 9091, 0, params);
	      }*/
	    	TTransport transport;
	    	transport = new TSocket(ip, port);
	    	transport.open();
	    	
	    	TProtocol protocol = new  TBinaryProtocol(transport);
	    	client = new Bridge.Client(protocol);
	      
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } 
		return client;
	}
	
	private Bridge.AsyncClient initAsynchroneClient(String ip, int port)
	{
		Bridge.AsyncClient client = null;
		try {
			client = new Bridge.AsyncClient(
					new TBinaryProtocol.Factory(),
					new TAsyncClientManager(),
			        new TNonblockingSocket(ip, port));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return client;
	}
	
	public Proxy(String ip, int port)
	{
		
		idm = new IncomingData(initSynchroneClient(ip,port), this);
		cm = new CommandSender(initAsynchroneClient(ip,port));
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
