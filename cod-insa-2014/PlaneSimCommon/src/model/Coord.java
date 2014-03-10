package model;

import java.awt.geom.Point2D;

import model.Coord.View;

import common.Copyable;
import common.CopyableAs;
import common.Util;
import common.Util.Dummy;
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
public final class Coord extends InternalView implements Viewable<Coord.View>, Copyable {
	

	/*
	 * Immutable common Coord objects:
	 */
	public static final Coord.View
		origin = new Coord(0,0).view(),
		unit   = new Coord(1,1).view()
	;
	
	
//	public final class View extends InternalView implements Viewable.View { //implements Copyable<Coord> {
//
//		public double x() { return x; }
//		public double y() { return y; }
//		
//		public View (Coord model) {
//			super(model);
//		}
//		
//	}
	public final class View extends InternalView implements Viewable.View, CopyableAs<Coord> { //implements Copyable<Coord> {

		public double x() { return x; }
		public double y() { return y; }
		
		public View () {
			//super(Coord.this);
			model = Coord.this;
		}
		
		@Override
		public Coord copyAs(Dummy<Coord> type) {
			return new Coord(this);
		}
		
	}
	
	/*
	 * By implementing Unique<Coord>, Coord.Unique tells us it contains a Coord that no
	 * one can have modifiable references to until it is taken.
	 * 
	 */
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
		
		// The follwoing is possible because Coord is a Viewable<Coord.View> type
		public View view() {
			return Util.view(this);
		}
	}
	
	public double x, y;
	
	//public final View view = new View();
	private final View view = new View();
	
	@Override
	public View view() {
		return view;
		//return new View();
	}
	@Override
	public Coord copy() {
		return new Coord(this);
	}
	
	public Coord (double x, double y) {
		//super(this);
		model = this;
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
//	private Coord(InternalView v) {
//		this(v.x(), v.y());
//	}
	
	public void set (View cv) {
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
	public void shift (double shift) {
		x += shift;
		y += shift;
	}
	public void shift (double dx, double dy) {
		x += dx;
		y += dy;
	}
	
	public void mult (double q) {
		x *= q;
		y *= q;
	}
	
	public void rotate (View center, double angle) {
		// TODO
		throw new UnsupportedOperationException("Not implemented yet");
	}

//	@Override
//	public String toString() {
//		return "Coord("+x+", "+y+")";
//	}
	
}


class InternalView {
	
	protected Coord model;
	
//	public InternalView (Coord model) {
//		this.model = model;
//	}
	
//	private Coord copy() {
//		return new Coord(model);
//	}
	
	public Coord opposite ()
	{ Coord r = new Coord(-model.x, -model.y); return r; }
	
	public Coord addedTo (View cv)
	{ Coord r = new Coord(model); r.add(cv); return r; }
	
	public final Coord addedTo (View cv, double coeff)
	{ Coord r = new Coord(model); r.shift(cv.x()*coeff, cv.y()*coeff); return r; }
	
	public Coord shifted (double shift)
	{ Coord r = new Coord(model); r.shift(shift); return r; }

	public Coord rotated (View center, double angle)
	{ Coord r = new Coord(model); r.rotate(center, angle); return r; }

	public double distanceTo (View cv)
	{ return Math.sqrt((cv.x()-model.x)*(cv.x()-model.x) + (cv.y()-model.y)*(cv.y()-model.y)); }
	
	public double squareDistanceTo (View cv)
	{ return (cv.x()-model.x)*(cv.x()-model.x) + (cv.y()-model.y)*(cv.y()-model.y); }
	
	//@Override
	public Coord copied() { return model.copy(); }

	@Override
	public boolean equals (Object e) {
		if (e == null)
			return false;
		if (e instanceof Coord)
			return model.x == ((Coord)e).x && model.y == ((Coord)e).y;
		if (e instanceof Coord.View)
			return model.x == ((Coord.View)e).x() && model.y == ((Coord.View)e).y();
		return false;
	}
	
	@Override
	public int hashCode () {
		//return Doubles.hashCode(x) * 31 + Doubles.hashCode(y);
		return Double.valueOf(model.x).hashCode() * 31 + Double.valueOf(model.y).hashCode();
	}
	
	@Override
	public String toString() {
		//return model.toString();
		return "Coord("+model.x+", "+model.y+")";
	}
	
}













