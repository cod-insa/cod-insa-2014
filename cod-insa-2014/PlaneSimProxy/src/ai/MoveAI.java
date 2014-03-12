package ai;

import java.util.ArrayList;
import java.util.Random;

import model.Base;
import model.Plane;
import model.Plane.State;

import command.MoveCommand;

public class MoveAI extends AbstractAI 
{
	
	public MoveAI(String ip, int port)
	{
		super(ip,port);
	}

	@Override
	public void think() {
		Random r = new Random();
		
		while (true) {
			game.updateSimFrame();
			ArrayList<Base.View> bases = game.getBases();
			
			for (Plane.View p : game.getMyPlanes())
			{
				// Get a random base :
				if(bases.size() > 0) {
					int i = r.nextInt(bases.size());
					//////////////////
					//i=0;
					//////////////////
					
					Base.View b = bases.get(i);
					
					//System.out.println("Moving plane "+p.id()+" to base "+bases.get(i).id()+", at pos "+b.position);
			
					// Make and send a MoveCommand with the plane to the random base
//					MoveCommand mc = new MoveCommand(p.id(), b.position());
//					
//					System.out.println("Sending command "+mc);
//					
//					game.sendCommand(mc);
					if (p.state() == State.IDLE)
						// Make and send a MoveCommand with the plane to the random base
						game.sendCommand(new MoveCommand(p.id(), b.position()));
					
				}
			}
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








