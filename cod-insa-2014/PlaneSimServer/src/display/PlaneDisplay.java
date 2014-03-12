package display;

import game.GamePlane;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.Plane.State;


public class PlaneDisplay extends EntityDisplay<GamePlane> {
	
	public static final float PLANE_ALPHA_AT_BASE = .3f;
	
	PolygonShape shape;

	public PlaneDisplay (GamePlane p) {
		super(p);
		
		ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
		
		points.add(new Point2D.Double(5, 0));
		points.add(new Point2D.Double(-10, -5));
		points.add(new Point2D.Double(-10, 5));
		
		shape = new PolygonShape(points, 3E-3);
	}

	@Override
	public void draw(Graphics2D g2d, ViewTransform vtrans) {
		
//		if (entity.getState() != State.AT_AIRPORT)
//		{
//			g2d.setColor(getPlayerColor());
//			
//			g2d.draw(shape.toPolygon(entity.modelView.position(), entity.modelView.rotation(), vtrans));
//		}
		
		Color c = getPlayerColor();
		//System.out.println((float)c.getRed()/255f);
		if (entity.getState() == State.AT_AIRPORT)
			c = new Color((float)c.getRed()/255, (float)c.getGreen()/255, (float)c.getBlue()/255, PLANE_ALPHA_AT_BASE);
		
		g2d.setColor(c);
		
		g2d.draw(shape.toPolygon(entity.modelView().position(), entity.modelView().rotation(), vtrans));
		
		
	}
	
}



