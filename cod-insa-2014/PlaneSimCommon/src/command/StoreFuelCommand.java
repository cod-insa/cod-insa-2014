package command;


public class StoreFuelCommand extends Command {

	//public final Plane plane;
	public final int planeSrcId;
	public final double fuelResourceToStore;

	public StoreFuelCommand(int psid, double fstore)
	{
		planeSrcId = psid;
		fuelResourceToStore = fstore;
	}
	
	@Override
	public String toString() {
		return "store "+planeSrcId+" :> "+fuelResourceToStore;
	}

	@Override
	public void match() throws StoreFuelCommand {
		throw this;
	}
	
}
