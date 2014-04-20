package Global;
import model.Coord;

public class Common {
	public static double distance(Coord a, Coord b)
	{
		return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
	
	public static double distanceSquare(Coord a, Coord b)
	{
		return Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);
	}
	
	static int myId = 1;
}
