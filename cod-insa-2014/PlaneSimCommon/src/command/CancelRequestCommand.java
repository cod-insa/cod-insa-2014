package command;

import model.Country;
import model.Plane;

import common.Util;

public class CancelRequestCommand extends Command {

	public final Country.Request.View request;
	
	public CancelRequestCommand(Country.Request.View r)
	{
		Util.checkNull(r);
		this.request = r;
	}
	
	@Override
	public String toString() {
		return "cancel request " + request.rqId();
	}
	
	public void match() throws CancelRequestCommand {
		throw this;
	}

}
