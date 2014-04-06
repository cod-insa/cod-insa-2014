package command;

import model.Plane;

import common.Util;


public class FollowCommand extends Command {

	//public final Plane plane;
	public final Plane.FullView planeSrc;
	public final Plane.BasicView planeTarget;

	public FollowCommand(Plane.FullView p, Plane.BasicView t)
	{
		Util.checkNull(p, t);
		planeSrc = p;
		planeTarget = t;
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
