package command;


public class FillFuelTankCommand extends Command {

	//public final Plane plane;
	public final int planeSrcId;
	public final double quantity;

	public FillFuelTankCommand(int psid, double qf)
	{
		planeSrcId = psid;
		quantity = qf;
	}
	
	@Override
	public String toString() {
		return "store "+planeSrcId+" :> "+quantity;
	}

	@Override
	public void match() throws FillFuelTankCommand {
		throw this;
	}
	
}
