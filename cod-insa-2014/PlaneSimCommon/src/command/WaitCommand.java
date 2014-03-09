package command;


public class WaitCommand extends Command {

	//public final Plane plane;
	public final int planeId;
	
	//public MoveCommand(Plane p, Coord d)
	public WaitCommand(int pid)
	{
		//plane = p;
		planeId = pid;
	}

	@Override
	public void match() throws WaitCommand {
		throw this;
	}
	
}
