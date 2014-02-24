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
		//TODO is an action valid?
		//return true;
		return true; // Let's say yes.
	}
	
	
}
