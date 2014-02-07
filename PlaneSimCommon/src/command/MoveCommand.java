package command;

import model.Coord;
import model.Plane;

public class MoveCommand implements Command {
	
	private Plane plane;
	private Coord destination;
	
	public MoveCommand(Plane pm, Coord cm)
	{
		plane = pm;
		destination = cm;
	}
	
	public Plane getPlane() {
		return plane;
	}

	public Coord getDestination() {
		return destination;
	}

	
}
