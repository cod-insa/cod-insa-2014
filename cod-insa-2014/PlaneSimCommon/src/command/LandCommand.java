package command;


public class LandCommand implements Command {

	//public final Plane plane;
	public final int planeId;
	
	public final int baseId;

	//public MoveCommand(Plane p, Coord d)
	public LandCommand(int pid, int bid)
	{
		//plane = p;
		planeId = pid;
		baseId = bid;
	}
	
	@Override
	public String toString() {
		return "land "+planeId+" -> "+baseId;
	}
	
}
