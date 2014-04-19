package model;

public class GameSettings {
	
	public static final int TIME_UNITS_PER_FRAME = 50;
	
	public static final double
		TIME_UNIT = 1,
		TIME_PER_FRAME = TIME_UNIT * TIME_UNITS_PER_FRAME
	;
	
	public static final boolean FRIENDLY_FIRE = false;

	public static final int MAX_PLANES_PER_BASE = 3;
	public static final int MAX_PLANES_PER_COUNTRY = MAX_PLANES_PER_BASE*3;

	public static double
		
//		DYING_RATE = .01,
//		MILITARY_TRANSFER_RATE = .015,
//		// ^ should be slightly greater than DYING_RATE to be able to save a base

//		MILITARY_TRANSFER_RATE = .025,
//		DYING_RATE_PERCENTAGE = .01,
			
		MILITARY_TRANSFER_RATE = .05,
		DYING_RATE_PERCENTAGE = .003,
	
	
		MINIMUM_BASE_GARRISON = .3,
		MINIMUM_CAPTURE_GARRISON = MINIMUM_BASE_GARRISON*2
	;
	
	
}