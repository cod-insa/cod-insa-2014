package command;

import model.Plane;

import common.Util;


public class ExchangeResourcesCommand extends Command {

	//public final Plane plane;
	public final Plane.FullView planeSrc;
	
	public final double militarQuantity;
	public final double fuelQuantity;
	public final boolean deleteResources;
	
	public ExchangeResourcesCommand(Plane.FullView p, double mq, double fq, boolean delete)
	{
		Util.checkNull(p);
		planeSrc = p;
		militarQuantity = mq;
		fuelQuantity = fq;
		deleteResources = delete;
	}
	
	@Override
	public String toString() {
		return "loadResource "+planeSrc.id()+" : militar => "+militarQuantity+"; fuel => "+fuelQuantity;
	}

	@Override
	public void match() throws ExchangeResourcesCommand {
		throw this;
	}
	
}
