package network;

import game.Sim;

import command.Command;

public class ActionChecker {

	Sim ps;
	
	public ActionChecker(Sim planeSim)
	{
		this.ps = planeSim;
	}
	
	public boolean isValid(Command a)
	{
		//FIXME This is maybe not a good idea, we should certainly do a "isValid" function for each different command (because the treatment is different)
		
		//return true;
		return true; // Let's say yes.
	}
	
	
}
