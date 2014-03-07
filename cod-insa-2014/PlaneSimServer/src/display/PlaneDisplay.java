package display;

import game.Plane;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class PlaneDisplay extends EntityDisplay<Plane> {
	
	PolygonShape shape;

	public PlaneDisplay (Plane p) {
		super(p);
		
		ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
		
		points.add(new Point2D.Double(5, 0));
		points.add(new Point2D.Double(-10, -5));
		points.add(new Point2D.Double(-10, 5));
		
		shape = new PolygonShape(points, 3E-3);
	}

	@Override
	public void draw(Graphics2D g2d, ViewTransform vtrans) {
		
		g2d.setColor(getPlayerColor());
		
		g2d.draw(shape.toPolygon(entity.modelView.position(), entity.modelView.rotation(), vtrans));
		
	}
	
}



