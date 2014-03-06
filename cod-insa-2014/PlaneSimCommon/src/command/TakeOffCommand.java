package command;


public class TakeOffCommand implements Command {

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
	
}
