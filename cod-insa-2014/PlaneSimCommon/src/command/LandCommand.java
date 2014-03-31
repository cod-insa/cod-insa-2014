package command;


import model.Base;

public class LandCommand extends Command {

	//public final Plane plane;
	public final int planeId;
	
	public final int baseId;

	//public MoveCommand(Plane p, Coord d)
	public LandCommand(int pid, int bid)
	{
		planeId = pid;
		baseId = bid;
	}
	// TODO: use this
	//public LandCommand(Plane.FullView p, Base.View b)
	
	@Override
	public String toString() {
		return "land "+planeId+" -> "+baseId;
	}

	@Override
	public void match() throws LandCommand {
		throw this;
	}
	
}
