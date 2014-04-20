package ai;

import java.util.Random;

import model.Base;
import model.Plane;
import command.BuildPlaneCommand;
import command.MoveCommand;
import common.MapView;

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
			game.sendCommand(new BuildPlaneCommand(r.nextInt() % 2 == 0 ? Plane.Type.COMMERCIAL : Plane.Type.MILITARY));
					
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








