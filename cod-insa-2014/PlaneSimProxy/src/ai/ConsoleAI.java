package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Base;
import model.Base.View;
import model.Plane;
import model.Plane.BasicView;
import model.Plane.FullView;
import command.AttackCommand;
import command.Command;
import command.LandCommand;
import command.MoveCommand;
import common.MapView;

public class ConsoleAI extends AbstractAI 
{
	
	public ConsoleAI(String ip, int port)
	{
		super(ip,port);
	}

	@Override
	public void think() {
		think_blocking();
	}
	
	public void think_blocking() {
		Scanner in = new Scanner(System.in);
		
		main_loop:
		while (true) {

			System.out.print("Next action ([move|land|attk] id; exit to quit): ");
			
			String[] cmd = in.nextLine().split(" ");

			System.out.print("Sending command... ");
			System.out.flush();
			
			game.updateSimFrame();
			

			MapView<Integer, View> bases;
			MapView<Integer, FullView> planes;
			MapView<Integer, BasicView> ennemy_planes;

			bases = game.getBases();
			planes = game.getMyPlanes();
			ennemy_planes = game.getEnnemyPlanes();
			
			List<Command> coms = new ArrayList<>();
			
			try {
				switch(cmd[0]) {
					case "exit":
						break main_loop;
					case "move": {
						Base.View b = bases.get(Integer.parseInt(cmd[1]));
						for (Plane.FullView p: planes.valuesView())
							coms.add(new MoveCommand(p, b.position()));
						break;
					}
					case "land": {
						Base.View b = bases.get(Integer.parseInt(cmd[1]));
						for (Plane.FullView p : planes.valuesView())
							coms.add(new LandCommand(p, b));
						break;
					}
					case "attk":
						for (Plane.FullView p : planes.valuesView())
							coms.add(new AttackCommand(p, ennemy_planes.get(Integer.parseInt(cmd[1]))));
						break;
					default:
						System.err.println("Unrecognized command!");
				}
				System.out.println("Sent");
			}
			catch(IndexOutOfBoundsException iob) {
				System.err.println("Command failed: "+iob);
			}
			
			for (Command c: coms)
				game.sendCommand(c);
			
		}
		exit:
		in.close();
	}
	
	public void think_async() {
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

			MapView<Integer, View> bases;
			MapView<Integer, FullView> planes;
			MapView<Integer, BasicView> ennemy_planes;

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
					for (Plane.FullView p: planes.valuesView())
						coms.add(new MoveCommand(p, b.position()));
					break;
				}
				case "land": {
					Base.View b = bases.get(Integer.parseInt(cmd[1]));
					for (Plane.FullView p : planes.valuesView())
						coms.add(new LandCommand(p, b));
					break;
				}
				case "attk":
					for (Plane.FullView p : planes.valuesView())
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








