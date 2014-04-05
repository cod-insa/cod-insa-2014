package command;

import model.Plane;


public class FollowCommand extends Command {

	//public final Plane plane;
	public final Plane.FullView planeSrc;
	public final Plane.BasicView planeTarget;

	public FollowCommand(Plane.FullView psid, Plane.BasicView ptid)
	{
		planeSrc = psid;
		planeTarget = ptid;
	}
	
	@Override
	public String toString() {
		return "follow "+planeSrc.id()+" -> "+planeTarget.id();
	}

	@Override
	public void match() throws FollowCommand {
		throw this;
	}
	
}
