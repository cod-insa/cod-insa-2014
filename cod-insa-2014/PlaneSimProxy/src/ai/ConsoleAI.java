package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Base;
import model.Plane;

import command.AttackCommand;
import command.Command;
import command.LandCommand;
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
		//int lastNumFrame = -1;

		class Updater extends Thread {
			public volatile boolean active = true;
			
			@Override
			public void run() {
				while (active) {
					synchronized (this) {
						game.updateSimFrame();
//						System.out.println("updated");
						
					}
				}
			}
		};
		final Updater updater = new Updater();
		updater.start();
		
		main_loop:
		while (true) {
//			game.updateSimFrame();
//			ArrayList<Base.View> bases = game.getBases();

//			if (lastNumFrame != game.getNumFrame()) {
//				game.updateSimFrame();
//				lastNumFrame = game.getNumFrame();
//			}
			//int i;

			System.out.print("Next action ([move|land|attk] id; exit to quit): ");
			//i = in.nextInt();
			
			String[] cmd = in.nextLine().split(" ");

			ArrayList<Base.View> bases;
			ArrayList<Plane.FullView> planes;
			ArrayList<Plane.BasicView> ennemy_planes;

//			System.out.println("waiting");
			
			// TODO FIXME! there should be synchronization but it doesn't work!
//			synchronized (updater) {
				bases = game.getBases();
				planes = game.getMyPlanes();
				ennemy_planes = game.getEnnemyPlanes();
//			}

//			System.out.println("sync");
			
			List<Command> coms = new ArrayList<>();
			
			switch(cmd[0]) {
				case "exit":
					break main_loop;
				case "move": {
					Base.View b = bases.get(Integer.parseInt(cmd[1]));
					for (Plane.FullView p: planes)
						coms.add(new MoveCommand(p, b.position()));
					break;
				}
				case "land": {
					Base.View b = bases.get(Integer.parseInt(cmd[1]));
					for (Plane.FullView p : planes)
						coms.add(new LandCommand(p, b));
					break;
				}
				case "attk":
					for (Plane.FullView p : planes)
						coms.add(new AttackCommand(p, ennemy_planes.get(Integer.parseInt(cmd[1]))));
					break;
				default:
					System.err.println("Unrecognized command!");
			}
			
			for (Command c: coms)
				game.sendCommand(c);

			


//			if (i < 0) {
//				//in.close();
//				/* I want to */ break /* free! */;
//			}
//			
//			Base.View b = bases.get(i);
//			
//			for (Plane.FullView p : game.getMyPlanes())
//			{
//				MoveCommand mc = new MoveCommand(p.id(), b.position());
//				
//				System.out.println("Sending command "+mc);
//				
//				game.sendCommand(mc);
//			}
		}
		exit:
		in.close();
		updater.active = false;
		try {
			updater.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
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








