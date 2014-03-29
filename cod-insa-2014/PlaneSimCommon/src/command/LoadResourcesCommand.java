package command;


public class LoadResourcesCommand extends Command {

	//public final Plane plane;
	public final int planeSrcId;
	
	public final double militarResourceToLoad;
	public final double fuelResourceToLoad;
	
	public LoadResourcesCommand(int psid, double mrload, double frload)
	{
		planeSrcId = psid;
		militarResourceToLoad = mrload;
		fuelResourceToLoad = frload;
	}
	
	@Override
	public String toString() {
		return "loadResource "+planeSrcId+" : militar => "+militarResourceToLoad+"; fuel => "+fuelResourceToLoad;
	}

	@Override
	public void match() throws LoadResourcesCommand {
		throw this;
	}
	
}
