package command;


import model.AbstractBase;
import model.Plane;

import common.Util;

public class LandCommand extends Command {

	public Plane.FullView plane;
	public AbstractBase.View base;

	public LandCommand(Plane.FullView p, AbstractBase.View b)
	{
		Util.checkNull(p, b);
		plane = p;
		base = b;
	}
	
	@Override
	public String toString() {
		return "land "+plane.id()+" -> "+base.id();
	}

	@Override
	public void match() throws LandCommand {
		throw this;
	}
	
}
