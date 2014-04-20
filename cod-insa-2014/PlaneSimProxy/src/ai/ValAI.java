package ai;

import command.*;
import common.MapView;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by LP on 19/04/2014.
 */
public class ValAI extends AbstractAI
{
	public ValAI(String ip, int port)
	{
		super(ip, port);
	}

	@Override
	public void think() {
		think_blocking();
	}

	public void think_blocking() {
//		while (true) {
//
//			this.game.updateSimFrame();
//
//			MapView<Integer, Base.BasicView> bases = this.game.getAllBases();
//			MapView<Integer, Plane.FullView> planes = this.game.getMyPlanes();
//
//			List<Command> comm =  new ArrayList<Command>();
//			comm.add(new BuildPlaneCommand(Plane.Type.COMMERCIAL));
//			comm.add(new BuildPlaneCommand(Plane.Type.MILITARY));
//
//			Base.BasicView b =  bases.get(12);
//
//			for (Plane.FullView p : planes.valuesView())
//			{
//				System.out.println(p.position);
////				comm.add(new MoveCommand(p, b.position));
//			}
//
//			for(Command c : comm)
//				this.game.sendCommand(c);
//
//		}

		while (true) {

			this.game.updateSimFrame();

			MapView<Integer, Base.BasicView> bases = this.game.getAllBases();
			MapView<Integer, Plane.FullView> planes = this.game.getMyPlanes();

			List<Command> comm =  new ArrayList<Command>();
			comm.add(new BuildPlaneCommand(Plane.Type.COMMERCIAL));
			comm.add(new BuildPlaneCommand(Plane.Type.MILITARY));

			AbstractBase.View c = this.game.getAllBases().get(12);

			Base.BasicView b =  bases.get(12);


			for (Plane.FullView p : planes.valuesView())
			{
//				System.out.println(p.position);
				//comm.add(new MoveCommand(p, b.position));
				comm.add(new LandCommand(p, c));
			}


			for(Command commande : comm)
				this.game.sendCommand(commande);

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

		AbstractAI ai = new ValAI(ip,port);
		ai.think();
	}

}
