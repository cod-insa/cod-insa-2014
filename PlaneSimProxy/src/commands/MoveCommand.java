package commands;

import model.Coord;
import model.PlaneModel;

public class MoveCommand implements Command {
	
	private PlaneModel plane;
	private Coord destination;
	
	public MoveCommand(PlaneModel pm, Coord cm)
	{
		plane = pm;
		destination = cm;
	}
	
	public PlaneModel getPlane() {
		return plane;
	}

	public Coord getDestination() {
		return destination;
	}

	
}
