package command;

import model.Plane;

import common.Util;


public class WaitCommand extends Command {

	public final Plane.FullView plane;
	
	public WaitCommand(Plane.FullView p) 
	{
		Util.checkNull(p);
		plane = p;
	}

	@Override
	public String toString() {
		return "wait "+plane.id();
	}
	
	@Override
	public void match() throws WaitCommand {
		throw this;
	}
	
}
