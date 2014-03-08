package ai;

import java.util.ArrayList;
import java.util.Random;

import model.BaseModel;
import model.PlaneModel;

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
		//r.setSeed(System.currentTimeMillis()); // not needed?
		
		while (true) {
			serveur.updateSimFrame();
			ArrayList<BaseModel.View> bases = serveur.getBases();
			
			for (PlaneModel.View p : serveur.getMyPlanes())
			{
				// Get a random base :
				if(bases.size() > 0) {
					int i = r.nextInt(bases.size());
					//////////////////
					//i=0;
					//////////////////
					
					BaseModel.View b = bases.get(i);
					
					//System.out.println("Moving plane "+p.id()+" to base "+bases.get(i).id()+", at pos "+b.position);
					
					// Make and send a MoveCommand with the plane to the random base
					MoveCommand mc = new MoveCommand(p.id(), b.position());
					
					System.out.println("Sending command "+mc);
					
					serveur.sendCommand(mc);
				}
			}
			try {
				// le client ne doit pas s'arr�ter pas (et ne ferme pas la socket)
				// wait marche pas, faudra changer �a lorsqu'il sera temps.
				//Thread.sleep(Integer.MAX_VALUE);
				
				Thread.sleep(5);
				
				
			} catch (InterruptedException e) {
				//e.printStackTrace();
				throw new Error(e);
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








