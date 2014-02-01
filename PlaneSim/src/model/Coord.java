package model;

import java.awt.geom.Point2D;

/*

When you own a Coord, set it "final".
If you want to expose the coordinate for free modification, set it public.
If you cant to expose it only for reading, set it private/package-private
and expose another final field holding a view into this Coord object.
Example:
	private final Coord _position = new Coord(0,0);
	public  final Coord.View position = _position.view;

When you ask for a Coord in parameter and you don't intend to modify it,
ask for a Coord.View instead.
When you return a Coord and you don't want the caller to modify it, return
the object's view instead:
	return some_internal_value.view;

Methods on Coord will modify the current Coord object.
Methods on Coord.View will all leave the objects intact.

*/

public final class Coord {

	public double x, y;
	
	public final class View {
		
		public double x() { return x; }
		public double y() { return y; }

		public Coord add (View cv)
		{ Coord r = new Coord(this); r.add(cv); return r; }
		
		public Coord rotate (Coord center, double angle)
		{ Coord r = new Coord(this); r.rotate(center, angle); return r; }
		
	}
	
	public final View view = new View();
	
	public Coord (double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Coord (View v) {
		this(v.x(), v.y());
	}
	public Coord (Coord c) {
		this(c.view);
	}
	public Coord(Point2D.Double p) {
		this(p.x, p.y);
	}
	
	public void set(View cv) {
		x = cv.x();
		y = cv.y();
	}
	
	public void add (View cv) {
		x += cv.x();
		y += cv.y();
	}
	public void sub (View cv) {
		x -= cv.x();
		y -= cv.y();
	}
	
	public void mult (double q) {
		x *= q;
		y *= q;
	}
	
	public void rotate (Coord center, double angle) {
		// TODO
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
}



