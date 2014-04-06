package command;

import model.Coord;
import model.Plane;

import common.Immutable;
import common.Util;

public class MoveCommand extends Command {

	//public final Plane plane;
	public final Plane.FullView plane;
	
	//final Coord _dest;
//	public final Coord.View destination;
	public final Immutable<Coord.View> destination;
	
	//public MoveCommand(Plane p, Coord d)
//	public MoveCommand(int pid, Coord.View d)
	public MoveCommand(Plane.FullView p, Coord.View d)
	{
		Util.checkNull(p, d);
		plane = p;
		destination = new Immutable<>(new Coord.Unique(d)); // FIXEDME request Immutable to avoid copies
		// Note: requiring immutable<Coord> would probably just annoy the clients
	}
	
	@Override
	public String toString() {
		return "mv "+plane.id()+" -> "+destination;
	}
	
	@Override
	public void match() throws MoveCommand {
		throw this;
	}
	
}


