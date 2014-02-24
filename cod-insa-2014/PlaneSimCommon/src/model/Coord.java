package model;

import java.awt.geom.Point2D;

import common.Copyable;
import common.Viewable;

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

A method accepting Coord.Copy means the method wants to own a new independent
object that no one else has direct access on.

*/

//public final class Coord implements Copyable<Coord>, Viewable {
public final class Coord implements Viewable<Coord.View>, Copyable<Coord> {

	public final class View implements Viewable.View { //implements Copyable<Coord> {
		
		public double x() { return x; }
		public double y() { return y; }
		
		public Coord add (View cv)
		{ Coord r = new Coord(this); r.add(cv); return r; }
		
		public Coord add (double shift)
		{ Coord r = new Coord(this); r.add(shift); return r; }
		
		public Coord rotate (Coord center, double angle)
		{ Coord r = new Coord(this); r.rotate(center, angle); return r; }
		
		//@Override
		public Coord copy() { return Coord.this.copy(); }
		
	}
	static public final class Unique extends common.Unique<Coord> {
		/*public Unique(Coord c) {
			super(c);
		}
		// To directly create an object, avoiding an actual copy
		public Unique(double x, double y) {
			//this.object = new Coord(x,y);
			super(new Coord(x,y), false);
		}*/
		public Unique(Coord original) {
			super(original.copy());
		}
		public Unique(double x, double y) {
			super(new Coord(x,y));
		}
	}
	
	public double x, y;
	
	public final View view = new View();
	
	@Override
	public View getView() {
		return view;
	}
	@Override
	public Coord copy() {
		return new Coord(this);
	}
	
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
	public void add (double shift) {
		x += shift;
		y += shift;
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


