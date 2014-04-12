package command;

import model.AbstractBase;
import model.Plane;

import common.Util;


public class DropMilitarsCommand extends Command {

	//public final Plane plane;
	public final Plane.FullView planeSrc;
	
	public final AbstractBase.View baseTarget;
	
	public final double quantity;

	public DropMilitarsCommand(Plane.FullView p, AbstractBase.View b, double ndrop)
	{
		Util.checkNull(p, b);
		planeSrc = p;
		baseTarget = b;
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
