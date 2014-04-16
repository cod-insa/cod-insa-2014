package game;

public interface Settings {

	public static final boolean
		DEBUG_NO_WAIT_FOR_PLAYERS = true,// false //true
		DEBUG_GOD_MODE = true
	;
	
	public static final double WORLD_ZOOM = 0.3;
	
	public static final boolean WORLD_WRAP = false;

	public static double
		DYING_RATE = .01,
		MILITARY_TRANSFER_RATE = .015,
		// ^ should be slightly greater than DYING_RATE to be able to save a base
		MINIMUM_BASE_GARRISON = .5
	;
	
}
