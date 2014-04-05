package command;


import model.Plane;

public class AttackCommand extends Command {

	//public final Plane plane;
	 
	public final Plane.FullView planeSrc;
	
	public final Plane.BasicView planeTarget;

	//public MoveCommand(Plane p, Coord d)
//	public AttackCommand(int psid, int ptid)
	public AttackCommand(Plane.FullView self, Plane.BasicView target)
	{
		//plane = p;
		planeSrc = self;
		planeTarget = target;
	}
	
	@Override
	public String toString() {
		return "attack "+planeSrc.id()+" -> "+planeTarget.id();
	}

	@Override
	public void match() throws AttackCommand {
		throw this;
	}
	
}
