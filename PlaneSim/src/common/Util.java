package common;

import java.util.Random;

import model.Coord;

public class Util {

	public static final Random rand = new Random();
	
	private static final Coord _unit = new Coord(1,1);
	public static final Coord.View unit = _unit.view;
	
}
