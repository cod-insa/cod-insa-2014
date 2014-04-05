package ai;

import java.util.ArrayList;
import java.util.Random;

import proxy.Proxy;
import model.Base;
import model.Entity.View;
import model.Plane;
import model.Plane.BasicView;
import model.Plane.State;
import command.AttackCommand;
import command.MoveCommand;

public class AttackAI extends AbstractAI 
{
	
	public AttackAI(String ip, int port)
	{
		super(ip,port);
	}

	@Override
	public void think() {
		Random r = new Random();
		
		while (true) {
			game.updateSimFrame();
			ArrayList<Base.View> bases = game.getBases();
			ArrayList<BasicView> ennemy_planes = game.getEnnemyPlanes();
			
			for (Plane.FullView p : game.getMyPlanes())
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
//					if (p.state() == State.IDLE && ennemy_planes.size() != 0)
						// Make and send a MoveCommand with the plane to the random base
						game.sendCommand(new AttackCommand(p, ennemy_planes.get(0)));
					
				}
			}
		}
	}
	
	public static void main(String[] args) 
	{
		if (args.length != 2)
		{
			System.out.println("Usage : java AttackAI ip port");
			System.exit(1);
		}
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		
		AbstractAI ai = new AttackAI(ip,port);
		ai.think();
	}
}








