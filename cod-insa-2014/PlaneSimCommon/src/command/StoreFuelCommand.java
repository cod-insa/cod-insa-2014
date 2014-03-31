package command;


public class StoreFuelCommand extends Command {

	//public final Plane plane;
	public final int planeSrcId;
	public final double quantity;

	public StoreFuelCommand(int psid, double fstore)
	{
		planeSrcId = psid;
		quantity = fstore;
	}
	
	@Override
	public String toString() {
		return "store "+planeSrcId+" :> "+quantity;
	}

	@Override
	public void match() throws StoreFuelCommand {
		throw this;
	}
	
}
