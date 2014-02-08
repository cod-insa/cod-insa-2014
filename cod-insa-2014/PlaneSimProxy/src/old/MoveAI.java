package old;

import java.util.ArrayList;
import java.util.Random;

import model.Base;
import model.Plane;
import command.MoveCommand;

public class MoveAI {
	
	private Proxy prox;
	
	public MoveAI()
	{
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
		
		// Then stop
		
		
	}
	
}
