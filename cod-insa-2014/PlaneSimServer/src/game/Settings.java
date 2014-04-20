package game;

import main.Main;

public abstract class Settings {
	
	public static final long GAME_FRAMES_PER_SECOND = 30;

	public static final boolean
		DEBUG_NO_WAIT_FOR_PLAYERS = Main.DEBUG_MODE,// false //true
		DEBUG_GOD_MODE = false//Main.DEBUG_MODE
	;
	
	public static final double WORLD_ZOOM = .5;

	public static final boolean WORLD_WRAP = false;
	
	
}
