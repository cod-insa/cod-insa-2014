package command;

import model.Plane;


public class FillFuelTankCommand extends Command {

	//public final Plane plane;
	public final Plane.FullView planeSrc;
	public final double quantity;

	public FillFuelTankCommand(Plane.FullView p, double qf)
	{
		planeSrc = p;
		quantity = qf;
	}
	
	@Override
	public String toString() {
		return "store "+planeSrc.id()+" :> "+quantity;
	}

	@Override
	public void match() throws FillFuelTankCommand {
		throw this;
	}
	
}
