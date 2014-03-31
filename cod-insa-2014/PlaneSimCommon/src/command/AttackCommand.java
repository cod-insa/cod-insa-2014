package command;


import model.Plane;

public class AttackCommand extends Command {

	//public final Plane plane;
	public final int planeSrcId;
	
	public final int planeTargetId;

	//public MoveCommand(Plane p, Coord d)
//	public AttackCommand(int psid, int ptid)
	public AttackCommand(Plane.FullView self, Plane.BasicView target)
	{
		//plane = p;
		planeSrcId = self.id();
		planeTargetId = target.id();
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
