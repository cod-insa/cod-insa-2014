package command;

import model.Plane;


public class StoreFuelCommand extends Command {

	public final Plane.FullView planeSrc;
	public final double quantity;

	public StoreFuelCommand(Plane.FullView p, double fstore)
	{
		planeSrc = p;
		quantity = fstore;
	}
	
	@Override
	public String toString() {
		return "store "+planeSrc.id()+" :> "+quantity;
	}

	@Override
	public void match() throws StoreFuelCommand {
		throw this;
	}
	
}
