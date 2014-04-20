package Runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.AbstractBase;
import model.Base;
import model.Coord;
import model.Plane;
import model.Plane.BasicView;
import model.Plane.Type;
import ai.AbstractAI;

import command.AttackCommand;
import command.BuildPlaneCommand;
import command.Command;
import command.ExchangeResourcesCommand;
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

			//// ATTENTION : 
			// LAND 0 pour aller au pays
			// MOVE ID (quelconque) -> va à une base connue ou inconnue
			// EXCH NB_M NB_F -> si on est sur une source, prend dans la soute NB_militar et NB_fuel
			// 				  -> si on est sur une destination (et négatif), décharge NB_militar et NB_fuel sur la dest
			System.out.print("Next action ([move|land|attk] id; [exch] m f; [build] M|C; list; exit): ");
			
			String[] cmd = in.nextLine().split(" ");

			System.out.print("Processing command... ");
			System.out.flush();
			
			game.updateSimFrame();
			

			MapView<Integer, Base.BasicView> bases;
			MapView<Integer, Plane.FullView> planes;
			MapView<Integer, BasicView> ennemy_planes;	

			bases = game.getAllBases();
			planes = game.getMyPlanes();
			ennemy_planes = game.getEnnemyPlanes();
			
			List<Command> coms = new ArrayList<>();
			
			try {
				boolean recognized = true;
				switch(cmd[0]) {
					case "exit":
						break main_loop;
					case "move": {
						if (cmd.length == 2)
						{
							int id = Integer.parseInt(cmd[1]);
							Base.BasicView b = bases.get(id);
							Coord.View c = null;
							
							if (b == null)
								if (game.getCountry().id() != id)
								{
									System.err.println("This id isn't corresponding neither to a base nor your country");
									break;
								}
								else
									c = game.getCountry().position();
							else
								c = b.position();
							
							for (Plane.FullView p: planes.valuesView())
								coms.add(new MoveCommand(p, c));
						}
						break;
					}
					case "land": {
						int id = Integer.parseInt(cmd[1]);
						Base.BasicView b = bases.get(id);
						AbstractBase.View c = null;
						
						if (b == null)
							if (game.getCountry().id() != id)
							{
								System.err.println("You can't see this base, move around it before you land");
								break;
							}
							else
								c = game.getCountry();
						else
							c = b;
						
						for (Plane.FullView p: planes.valuesView())
							coms.add(new LandCommand(p, c));
						
						break;
					}
					case "attk":
						Plane.BasicView ep = ennemy_planes.get(Integer.parseInt(cmd[1]));
						if (ep == null)
						{
							System.err.println("Bad id, this plane does not exists");
							break;
						}
						for (Plane.FullView p : planes.valuesView())
							coms.add(new AttackCommand(p, ep));
						break;
					case "exch":
						if (cmd.length == 3) {
							int militar = Integer.parseInt(cmd[1]);
							int fuel = Integer.parseInt(cmd[2]);
							for (Plane.FullView p : planes.valuesView()) {
								coms.add(new ExchangeResourcesCommand(p, militar, fuel, false));
							}
						}
						break;
					case "build":
						if (cmd.length == 2) {
							if (cmd[1].equals("M"))
								coms.add(new BuildPlaneCommand(Type.MILITARY));
							else if (cmd[1].equals("C"))
								coms.add(new BuildPlaneCommand(Type.COMMERCIAL));
							else
								System.err.println("Type inconnu");
						}
						break;
					case "list":
						System.out.println();
						System.out.println(">> My planes:");
						for (Plane.FullView p : planes.valuesView())
							System.out.println(p+ " capacity_m:" +p.type.holdCapacity+
									" capacity_f:" +p.type.tankCapacity+
									" consumption:" +p.type.fuelConsumptionPerDistanceUnit+
									" timeRespawn:" +p.type.timeToBuild);
						System.out.println(">> Ennemy planes:");
						for (Plane.BasicView p : ennemy_planes.valuesView())
							System.out.println(p);
						System.out.println(">> Visible bases :");
						for (Base.FullView b : game.getVisibleBase().valuesView())
							System.out.println(b);
						System.out.println(">> All bases : ");
						for (Base.BasicView b : game.getAllBases().valuesView())
							System.out.println(b.id());
						System.out.println(">> Your Country");
						System.out.println(game.getCountry());
						break;
					default:
						recognized = false;
						System.err.println("Unrecognized command!");
				}
				if (recognized)
					System.out.println("Processed");
			}
			catch(IllegalArgumentException e) {
				System.out.println("Command failed: "+e);
				System.err.println("Command failed: "+e);
			}
			
			for (Command c: coms)
				game.sendCommand(c);
			
		}
		exit:
		in.close();
		game.quit(0);
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








