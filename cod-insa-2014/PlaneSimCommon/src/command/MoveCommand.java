package command;

import model.Coord;

public class MoveCommand implements Command {

	//public final Plane plane;
	public final int planeId;
	
	//final Coord _dest;
	public final Coord.View destination;

	//public MoveCommand(Plane p, Coord d)
	public MoveCommand(int pid, Coord.View d)
	{
		//plane = p;
		planeId = pid;
		destination = d;
	}
	
}