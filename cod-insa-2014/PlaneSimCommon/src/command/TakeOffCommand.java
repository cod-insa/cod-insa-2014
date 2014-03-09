package command;


public class TakeOffCommand extends Command {

	//public final Plane plane;
	public final int planeId;
	
	//public MoveCommand(Plane p, Coord d)
	public TakeOffCommand(int pid)
	{
		//plane = p;
		planeId = pid;
	}
	
	@Override
	public String toString() {
		return "Take off: "+planeId;
	}
	
	@Override
	public void match() throws TakeOffCommand {
		throw this;
	}
	
}
