package ai;

import java.util.ArrayList;
import java.util.Random;

import model.Base;
import model.Plane;
import proxy.Proxy;

import command.MoveCommand;

public class MoveAI extends AbstractAI 
{
	
	public MoveAI(String ip, int port)
	{
		super(new Proxy(ip,port));
	}

	@Override
	public void think() {
		proxy.updateSimFrame();
		
		ArrayList<Base.View> bases = proxy.getBases();
		for (Plane.View p : proxy.getMyPlanes())
		{
			// Get a random base :
			Base.View b = bases.get(new Random().nextInt(bases.size()));
			
			// Make and send a MoveCommand with the plane to the random base
			MoveCommand mc = new MoveCommand(p.id(),b.position);
			proxy.sendCommand(mc);
		}
	}
	
	public static void main(String[] args) 
	{
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
