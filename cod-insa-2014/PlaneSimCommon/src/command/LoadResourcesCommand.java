package command;

import model.Plane;

import common.Util;


public class LoadResourcesCommand extends Command {

	//public final Plane plane;
	public final Plane.FullView planeSrc;
	
	public final double militarQuantity;
	public final double fuelQuantity;
	
	public LoadResourcesCommand(Plane.FullView p, double mq, double fq)
	{
		Util.checkNull(p);
		planeSrc = p;
		militarQuantity = mq;
		fuelQuantity = fq;
	}
	
	@Override
	public String toString() {
		return "loadResource "+planeSrc.id()+" : militar => "+militarQuantity+"; fuel => "+fuelQuantity;
	}

	@Override
	public void match() throws LoadResourcesCommand {
		throw this;
	}
	
}
