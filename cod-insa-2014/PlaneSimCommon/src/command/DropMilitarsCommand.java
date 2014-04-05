package command;

import model.Base;
import model.Plane;


public class DropMilitarsCommand extends Command {

	//public final Plane plane;
	public final Plane.FullView planeSrc;
	
	public final Base.View baseTarget;
	
	public final double quantity;

	public DropMilitarsCommand(Plane.FullView psid, Base.View btid, double ndrop)
	{
		planeSrc = psid;
		baseTarget = btid;
		quantity = ndrop;
	}
	
	@Override
	public String toString() {
		return "drop "+planeSrc.id()+" --("+quantity+")--> "+baseTarget.id();
	}

	@Override
	public void match() throws DropMilitarsCommand {
		throw this;
	}
	
}
