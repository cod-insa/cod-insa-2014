package command;


public class FollowCommand extends Command {

	//public final Plane plane;
	public final int planeSrcId;
	
	public final int planeTargetId;

	//public MoveCommand(Plane p, Coord d)
	public FollowCommand(int psid, int ptid)
	{
		//plane = p;
		planeSrcId = psid;
		planeTargetId = ptid;
	}
	
	@Override
	public String toString() {
		return "follow "+planeSrcId+" -> "+planeTargetId;
	}

	@Override
	public void match() throws FollowCommand {
		throw this;
	}
	
}
