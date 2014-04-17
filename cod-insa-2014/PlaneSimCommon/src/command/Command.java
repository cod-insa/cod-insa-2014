package command;


public abstract class Command extends Throwable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int
		SUCCESS = 0,
		WARNING_COMMAND = -1,
		ERROR_COMMAND = -2,
		ERROR_TIME_OUT = -3,
		ERROR_FORBIDDEN = -4,
		ERROR_UNKNOWN = -5
	;
	
	public abstract void match() throws
		MoveCommand,
		LandCommand,
		WaitCommand, 
		AttackCommand,
		FollowCommand, 
		DropMilitarsCommand, 
		FillFuelTankCommand, 
		ExchangeResourcesCommand, 
		BuildPlaneCommand
	;
	
}

