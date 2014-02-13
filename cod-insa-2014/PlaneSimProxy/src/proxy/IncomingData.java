package proxy;

import genbridge.Base;
import genbridge.Bridge;
import genbridge.Data;

import java.util.ArrayList;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class IncomingData {

	private Proxy proxy;
	private int id;
	private Bridge.Client client;
	
	public IncomingData(String ip, int port, Proxy p)
	{
		// Initialize client
		Bridge.Client client = null;
		try 
		{
	    	TTransport transport;
	    	transport = new TSocket(ip, port);
	    	transport.open();
	    	
	    	TProtocol protocol = new  TBinaryProtocol(transport);
	    	client = new Bridge.Client(protocol);
	      
	    } catch (Exception e) {
	    	System.err.println("Error while initializing data retriever : ");
	    	e.printStackTrace();
	    } 
		
		proxy = p;
		retrieveInitialDatas();
	}
	
	public int getIdConnection() {
		return id;
	}
	
	public void retrieveInitialDatas()
	{

		try {
			id = client.connect("Banane");
		} catch (TException e) {
			System.err.println("Error while connecting to the server");
			e.printStackTrace();
		}
	}
	
	public void updateData()
	{
		try {
			Data d = client.retrieveData(id); // Bloquant
			proxy.updateProxyData(d);
		} catch (TException e) {
			e.printStackTrace();
		}
	}


}
