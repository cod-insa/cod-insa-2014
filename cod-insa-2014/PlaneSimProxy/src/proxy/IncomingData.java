package proxy;

import genbridge.Bridge;
import genbridge.ConnectionData;
import genbridge.Data;
import genbridge.InitData;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class IncomingData {

	private Proxy proxy;
	private int connection_id;
	private int player_id;


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
	    	System.exit(-1);
	    } 
		
		proxy = p;
	}
	
	
	public int getIdConnection() {
		return connection_id;
	}
	public int getPlayerId() {
		return player_id;
	}
	
	public void retrieveInitialData()
	{
		System.out.println("Trying to connect to the server...");
		try {
			ConnectionData cd = client.connect("Banane2");
			player_id = cd.player_id;
			connection_id = cd.con_id;
			
			System.out.println("Connected with id: " + connection_id + ". You are player n°" + player_id);
			if (connection_id < 0)
			{
				System.err.println("Error while connecting to the server: invalid id.");
				System.exit(1);
			}
			
			System.out.println("Retrieving ");
			InitData d = client.retrieveInitData(connection_id);
			
			proxy.setInitData(d);
			
		} catch (TException e) {
			System.err.println("Error while connecting to the server");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void updateData()
	{
		//System.out.println("Trying to update data...");
		try {
			Data d = client.retrieveData(connection_id); // Will be blocked during call
			if (d.numFrame < 0)
			{
				if (d.numFrame == -1)
					 System.out.println("Received an end-of-game frame id (-1), stopping.");
				else System.err.println("Error: frame sent by the server is not valid ("+d.numFrame+").");
				System.exit(1); // TODO FIXME clean exit
			}
			proxy.updateProxyData(d);
			//System.out.println("Data updated");
		} catch (TException e) {
			System.err.println("Error while retrieving data from server");
			e.printStackTrace();
			System.exit(1);
		}
	}
}








