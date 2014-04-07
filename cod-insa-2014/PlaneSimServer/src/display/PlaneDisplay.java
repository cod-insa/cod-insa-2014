package display;

import game.GamePlane;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.Coord;
import model.Plane;
import model.Plane.State;

import common.Couple;


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
		super.draw(g2d, vtrans);
		
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
		
		Plane m = entity.model();
		
		g2d.draw(shape.toPolygon(m.position(), m.rotation(), vtrans));
		
//		Pixel left = vtrans.getViewPos(m.position().shifted(-m.).view());
//		Pixel right = vtrans.getViewPos(m.position().shifted(size/2).view());
		
		//Pixel left = vtrans.getViewPos(m.position().shifted(-entity.radius()).view());
		
		double r = entity.radius()*1.5;
		
		Coord left = m.position().shifted(-r, r); //entity.radius());
		Coord right = left.shifted(r*2, 0);
		
		//double height = vtrans._scale.y*.003;
		
		Pixel leftp = vtrans.getViewPos(left.view());
		Pixel rightp = vtrans.getViewPos(right.view());
		int height = (int) Math.round(vtrans._scale.y*5);
		
		int y = leftp.y;
		
		class Bar extends Couple<Double, Color> {
			public Bar(Double first, Color second) { super(first, second); }
		}
		for (Bar bar: new Bar[]{
			new Bar(m.health, Color.green),
			new Bar(m.capacityTank/m.capacityTank, Color.blue),
			new Bar(m.militarResourceCarried/m.capacityHold, Color.red),
		}) {
			g2d.setColor(bar.second);
			y += height*2;
			g2d.fillRect(leftp.x, y, (int) Math.round(bar.first*(rightp.x-leftp.x)), height);
		}
		
		
		
			//left.shift(0, height*2)
			//leftp.y += height*2; rightp.y += height*2;
		
	}
	
}















