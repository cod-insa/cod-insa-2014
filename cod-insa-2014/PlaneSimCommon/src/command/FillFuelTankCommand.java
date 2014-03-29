package command;


public class FillFuelTankCommand extends Command {

	//public final Plane plane;
	public final int planeSrcId;
	public final double fuelQuantity;

	public FillFuelTankCommand(int psid, double qfuel)
	{
		planeSrcId = psid;
		fuelQuantity = qfuel;
	}
	
	@Override
	public String toString() {
		return "store "+planeSrcId+" :> "+fuelQuantity;
	}

	@Override
	public void match() throws FillFuelTankCommand {
		throw this;
	}
	
}
