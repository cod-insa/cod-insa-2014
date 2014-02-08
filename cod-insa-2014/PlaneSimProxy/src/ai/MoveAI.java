package ai;

import java.util.ArrayList;
import java.util.Random;

import model.Base;
import model.Plane;
import proxy.Proxy;

import command.MoveCommand;

public class MoveAI {
	
	private Proxy prox;
	
	public MoveAI()
	{
		prox = new Proxy();

		ArrayList<Base.View> bases = prox.getBases();
		for (Plane.View p : prox.getMyPlanes())
		{
			// Get a random base :
			Base.View b = bases.get(new Random().nextInt(bases.size()));
			
			// Make and send a MoveCommand with the plane to the random base
			MoveCommand mc = new MoveCommand(p.id(),b.position);
			prox.sendCommand(mc);
		}
		
		// Then stop
		
		
	}
	
}
