package proxy;

import genbridge.Bridge;
import genbridge.Data;

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
		try 
		{
			// Initialize Socket of client
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
	}
	
	
	public int getIdConnection() {
		return id;
	}
	
	public void retrieveInitialDatas()
	{
		System.out.println("Trying to connect to the server...");
		try {
			id = client.connect("Banane2");
			System.out.println("Connected!");
		} catch (TException e) {
			System.err.println("Error while connecting to the server");
			e.printStackTrace();
		}
	}
	
	public void updateData()
	{
		System.out.println("Trying to update data...");
		try {
			Data d = client.retrieveData(id); // Will be blocked during call
			proxy.updateProxyData(d);
			System.out.println("Data updated");
		} catch (TException e) {
			e.printStackTrace();
		}
	}


}
