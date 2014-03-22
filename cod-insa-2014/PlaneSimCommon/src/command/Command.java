package command;


public abstract class Command extends Throwable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int
		SUCCESS = 0,
		ERROR_COMMAND = -1,
		ERROR_TIME_OUT = -2,
		ERROR_FORBIDDEN = -3,
		ERROR_UNKNOWN = -4
	;
	
	public abstract void match() throws
		MoveCommand,
		LandCommand,
		WaitCommand, 
		AttackCommand,
		FollowCommand
	;
	
}


