package ai;

import java.util.Random;

import model.Base;
import model.Plane;
import model.Plane.BasicView;

import command.AttackCommand;
import common.MapView;

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
			MapView<Integer, Base.BasicView> bases = game.getAllBases();
			MapView<Integer, BasicView> ennemy_planes = game.getEnnemyPlanes();
			
			for (Plane.FullView p : game.getMyPlanes().valuesView())
			{
				// Get a random base :
				if(bases.size() > 0) {
					int i = r.nextInt(bases.size());
					//////////////////
					//i=0;
					//////////////////
					
					Base.BasicView b = bases.get(i);
					
					//System.out.println("Moving plane "+p.id()+" to base "+bases.get(i).id()+", at pos "+b.position);
			
					// Make and send a MoveCommand with the plane to the random base
//					MoveCommand mc = new MoveCommand(p.id(), b.position());
//					
//					System.out.println("Sending command "+mc);
//					
//					game.sendCommand(mc);
//					if (p.state() == State.IDLE && ennemy_planes.size() != 0)
						// Make and send a MoveCommand with the plane to the random base
//						game.sendCommand(new AttackCommand(p, ennemy_planes.get(0)));
					
					if (ennemy_planes.size() != 0)
						game.sendCommand(new AttackCommand(p, ennemy_planes.valuesView().iterator().next()));
					
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








