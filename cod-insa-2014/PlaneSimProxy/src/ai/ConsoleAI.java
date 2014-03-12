package ai;

import java.util.ArrayList;
import java.util.Scanner;

import model.Base;
import model.Plane;

import command.MoveCommand;

public class ConsoleAI extends AbstractAI 
{
	
	public ConsoleAI(String ip, int port)
	{
		super(ip,port);
	}

	@Override
	public void think() {
		Scanner in = new Scanner(System.in);
		
		while (true) {
			game.updateSimFrame();
			ArrayList<Base.View> bases = game.getBases();
			int i;

			System.out.print("Next base: ");
			i = in.nextInt();
			
			if (i < 0) {
				in.close();
				/* I want to */ break /* free! */;
			}
			
			Base.View b = bases.get(i);
			
			for (Plane.View p : game.getMyPlanes())
			{
				MoveCommand mc = new MoveCommand(p.id(), b.position());
				
				System.out.println("Sending command "+mc);
				
				game.sendCommand(mc);
			}
			
		}
		
		System.exit(0);
	}

	@Override
	public void end() {
		
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
		
		AbstractAI ai = new ConsoleAI(ip,port);
		ai.think();
	}

}








