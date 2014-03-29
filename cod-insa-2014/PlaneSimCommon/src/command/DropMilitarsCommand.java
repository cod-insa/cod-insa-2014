package command;


public class DropMilitarsCommand extends Command {

	//public final Plane plane;
	public final int planeSrcId;
	
	public final int baseTargetId;
	
	public final double militarResourceDroped;

	public DropMilitarsCommand(int psid, int btid, double ndrop)
	{
		planeSrcId = psid;
		baseTargetId = btid;
		militarResourceDroped = ndrop;
	}
	
	@Override
	public String toString() {
		return "drop "+planeSrcId+" --("+militarResourceDroped+")--> "+baseTargetId;
	}

	@Override
	public void match() throws DropMilitarsCommand {
		throw this;
	}
	
}
