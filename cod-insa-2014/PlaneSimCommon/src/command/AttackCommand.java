package command;


public class AttackCommand extends Command {

	//public final Plane plane;
	public final int planeSrcId;
	
	public final int planeTargetId;

	//public MoveCommand(Plane p, Coord d)
	public AttackCommand(int psid, int ptid)
	{
		//plane = p;
		planeSrcId = psid;
		planeTargetId = ptid;
	}
	
	@Override
	public String toString() {
		return "attack "+planeSrcId+" -> "+planeTargetId;
	}

	@Override
	public void match() throws AttackCommand {
		throw this;
	}
	
}
