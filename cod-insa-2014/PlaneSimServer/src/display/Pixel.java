package display;

public class Pixel {
	final int x, y;
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public String toString() { return "["+x+" ; "+y+"]"; }
}

