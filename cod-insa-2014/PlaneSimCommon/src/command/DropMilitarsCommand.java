package command;


public class DropMilitarsCommand extends Command {

	//public final Plane plane;
	public final int planeSrcId;
	
	public final int baseTargetId;
	
	public final double quantity;

	public DropMilitarsCommand(int psid, int btid, double ndrop)
	{
		planeSrcId = psid;
		baseTargetId = btid;
		quantity = ndrop;
	}
	
	@Override
	public String toString() {
		return "drop "+planeSrcId+" --("+quantity+")--> "+baseTargetId;
	}

	@Override
	public void match() throws DropMilitarsCommand {
		throw this;
	}
	
}
