package model;

import java.awt.geom.Point2D;


public final class Coord {

	public double x, y;
	
	public final class View {
		
		public double x() { return x; }
		public double y() { return y; }

		public Coord add (View cv)
		{ Coord r = new Coord(this); r.add(cv); return r; }
		
		public Coord rotate (Coord center, double angle)
		{ Coord r = new Coord(this); r.rotate(center, angle); return r; }
		
		/*public Point2D.Double toPoint2D() {
			// TODO Auto-generated method stub
			return null;
		}*/
		
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
		/*this.x = c.x;
		this.y = c.y;*/
		//this(c.x, c.y);
		this(c.view);
	}
	public Coord(Point2D.Double p) {
		this(p.x, p.y);
	}
	
	public void set(View cv) {
		x = cv.x();
		y = cv.y();
	}
	/*public void set (Coord c) {
		set(c.view);
	}*/
	
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


/*
public class Coord {
	
	double x, y;
	
	public double getX() { return x; }
	public double getY() { return y; }
	
	public Coord(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Coord(Coord c) {
		this.x = c.x;
		this.y = c.y;
	}
	
	void add (Coord c) {
		x += c.x;
		y += c.y;
	}
	public Coord addedTo(Coord c)
	{ Coord r = new Coord(this); r.add(c); return r; }
	
	void rotate (Coord center, double angle) {
		// TODO
		throw new UnsupportedOperationException("Not implemented yet");
	}
	public Coord rotatedTo(Coord center, double angle)
	{ Coord r = new Coord(this); r.rotate(center, angle); return r; }
	
}
*/

/*

Usage example where the original object u is not modified

	Coord v = new Coord(u); u.add(Coord(1,2))
	// or
	Coord v = u.addedTo(Coord(1,2))



classes:

	Coord
		x,y
	
	CoordObs
		x(),y()
		CoordObs add()

*/



