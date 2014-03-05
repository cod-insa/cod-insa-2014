package ai;

import java.util.ArrayList;
import java.util.Scanner;

import model.BaseModel;
import model.PlaneModel;
import proxy.Proxy;

import command.MoveCommand;

public class ConsoleAI extends AbstractAI 
{
	
	public ConsoleAI(String ip, int port)
	{
		super(new Proxy(ip,port));
	}

	@Override
	public void think() {
		Scanner in = new Scanner(System.in);
		
		while (true) {
			proxy.updateSimFrame();
			ArrayList<BaseModel.View> bases = proxy.getBases();
			int i;

			System.out.print("Next base: ");
			i = in.nextInt();
			
			if (i == -1) {
				in.close();
				/* I want to */ break /* free! */;
			}
			
			BaseModel.View b = bases.get(i);
			
			for (PlaneModel.View p : proxy.getMyPlanes())
			{
				MoveCommand mc = new MoveCommand(p.id(), b.position);
				
				System.out.println("Sending command "+mc);
				
				proxy.sendCommand(mc);
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
		
		AbstractAI ai = new ConsoleAI(ip,port);
		ai.think();
	}
}








