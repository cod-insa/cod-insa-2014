package ai;

import java.io.IOException;

import genbridge.Bridge;

import org.apache.thrift.TException;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import proxy.Proxy;

public class MoveAI extends AbstractAI 
{
	
	public MoveAI(String ip, int port)
	{
		super(new Proxy(ip,port));
		/*
		super();
		prox = new Proxy();

		prox.updateSimFrame();
		
		ArrayList<Base> bases = prox.bases;
		for (Plane p : prox.ai_planes)
		{
			// Get a random base :
			Base b = bases.get(new Random().nextInt(bases.size()));
			
			// Make and send a MoveCommand with the plane to the random base
			MoveCommand mc = new MoveCommand(p.id,b._pos);
			prox.sendCommand(mc);
		}
		
		*/
		
	}

	@Override
	public void think() {
		// TODO Let's write the code here
		
		
		
	}
	
	public static void main(String[] args) 
	{
	   /* if (args.length != 1) { 
	      System.out.println("Please enter 'simple' or 'secure' as attribute");
	      System.exit(0);
	    } if (args[0].contains("simple")) {
        transport = new TSocket("localhost",9090);
        transport.open();
	      }
	      else {
	    	  throw new NotImplementedException();    	  
	      }*/

		if (args.length != 2)
		{
			System.out.println("Usage : java MoveAI ip port");
			System.exit(1);
		}
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		
		AbstractAI ai = new MoveAI(ip,port);
		ai.think();
	}
}
