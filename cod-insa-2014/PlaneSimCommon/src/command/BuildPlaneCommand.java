package command;

import common.Util;
import model.Plane;

public class BuildPlaneCommand extends Command {

	public final Plane.Type requestedType;
	
	public BuildPlaneCommand(Plane.Type requestedType)
	{
		Util.checkNull(requestedType);
		this.requestedType = requestedType;
	}
	
	@Override
	public String toString() {
		return "build " + (requestedType == Plane.Type.MILITARY ? "military" : "commercial");
	}
	
	public void match() throws BuildPlaneCommand {
		throw this;
	}

}
