package command;


public interface Command {
	
	public static final int
		SUCCESS = 0,
		ERROR_COMMAND = -1,
		ERROR_TIME_OUT = -2,
		ERROR_FORBIDDEN = -3,
		ERROR_UNKNOWN = -4
	;
	
	
}
