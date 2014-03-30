package command;


public class LoadResourcesCommand extends Command {

	//public final Plane plane;
	public final int planeSrcId;
	
	public final double militarQuantity;
	public final double fuelQuantity;
	
	public LoadResourcesCommand(int psid, double mq, double fq)
	{
		planeSrcId = psid;
		militarQuantity = mq;
		fuelQuantity = fq;
	}
	
	@Override
	public String toString() {
		return "loadResource "+planeSrcId+" : militar => "+militarQuantity+"; fuel => "+fuelQuantity;
	}

	@Override
	public void match() throws LoadResourcesCommand {
		throw this;
	}
	
}
