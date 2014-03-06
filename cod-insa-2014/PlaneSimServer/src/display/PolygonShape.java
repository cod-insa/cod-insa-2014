package display;


import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.Coord;


public class PolygonShape {
	
	ArrayList<Point2D.Double> points;
	
	double current_rotation = 0;
	
	public PolygonShape(ArrayList<Point2D.Double> points, double scale) {
		this.points = points;
		for (Point2D.Double p: points) {
			p.x *= scale;
			p.y *= scale;
		}
	}
	
	public void rotate (double angle, Coord.View center) {
		current_rotation += angle;
		
		Point2D.Double[] pts = points.toArray(new Point2D.Double[points.size()]);
		
		AffineTransform.getRotateInstance(angle, center.x(), center.y()) //center.x(), center.y())
                .transform(pts,0,pts,0,pts.length);
	}
	
	public Polygon toPolygon(Coord.View center, double rotation, ViewTransform vtrans) {
		rotate(rotation-current_rotation, new Coord(0,0).view());
		
		Polygon ret = new Polygon();
		for (Point2D.Double p : points) {
			Coord c = new Coord(p);
			c.add(center);
			Pixel pix = vtrans.getViewPos(c.view());
			ret.addPoint(pix.x, pix.y);
		}
		
		return ret;
	}
	
}








