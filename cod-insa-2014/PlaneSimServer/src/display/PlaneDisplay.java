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
	
	public final PolygonShape shape;

	public PlaneDisplay (GamePlane p) {
		super(p);

		ArrayList<Point2D.Double> half = new ArrayList<>();

		double siz;
		
		if (p.model().type == Plane.Type.MILITARY) {
			
//			half.add(new Point2D.Double(5, 0));
//			half.add(new Point2D.Double(-10, -5));
//			half.add(new Point2D.Double(-10, 5));

//			half.add(new Point2D.Double(5, 0));
//			half.add(new Point2D.Double(-10, 5));

			
//			half.add(new Point2D.Double(  10,   2));
//			half.add(new Point2D.Double(   4, 2.2));
//			half.add(new Point2D.Double( 4.5,  12));
//			half.add(new Point2D.Double( 5.5,  12));
//			half.add(new Point2D.Double( 6.5,  2.2));
//			half.add(new Point2D.Double(-8,  1.5));
//			half.add(new Point2D.Double(-12,  5));

			
			
//			half.add(new Point2D.Double(  12,   1));
//			half.add(new Point2D.Double(  10,   2));
			
//			half.add(new Point2D.Double(  10,   1));
//			half.add(new Point2D.Double(   7,   2));
//			half.add(new Point2D.Double(   3, 2.2));

			half.add(new Point2D.Double(  9,   1));
			half.add(new Point2D.Double(   8,   2));
			half.add(new Point2D.Double(   2, 2.2));
			half.add(new Point2D.Double(   1,  12));
			half.add(new Point2D.Double(   0,  13));
			half.add(new Point2D.Double(  -2,  12));
			half.add(new Point2D.Double(  -3, 2.2));
			half.add(new Point2D.Double(-10,  1.5));
			half.add(new Point2D.Double(-14,  5));
			

			siz = 3E-3;
			
		} else if (p.model().type == Plane.Type.COMMERCIAL) {

//			half.add(new Point2D.Double( 12,  0));
//			half.add(new Point2D.Double( 10,  2));
//			half.add(new Point2D.Double(  4,  3));
//			half.add(new Point2D.Double(  2,  12));
////			half.add(new Point2D.Double(  -1,  13));
//			half.add(new Point2D.Double(  -1.5,  13));
//			half.add(new Point2D.Double(  0,  3));
//			half.add(new Point2D.Double(-8,  1.5));
//			half.add(new Point2D.Double(-12,  5));
			
			half.add(new Point2D.Double( 13,  0));
			half.add(new Point2D.Double( 11,  2));
			half.add(new Point2D.Double(  5,  3));
			half.add(new Point2D.Double(  3,  12));
//			half.add(new Point2D.Double(  -1,  13));
			half.add(new Point2D.Double(  -1.5,  13));
			half.add(new Point2D.Double(  0,  3));
			half.add(new Point2D.Double(-8,  1.5));
			half.add(new Point2D.Double(-12,  5));


//			siz = 5E-3;
			siz = 4.5E-3;

		} else throw new AssertionError(); //assert false;
		
		ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();

		points.addAll(half);
		
//		for (Point2D.Double p: half) {
		for (int i = half.size()-1; i >= 0; i--) {
			Point2D.Double p2d = half.get(i);
//			points.add(p2d);
			if (p2d.getY() != 0)
				points.add(new Point2D.Double(p2d.getX(), -p2d.getY()));
		}
		
		// TODO center the shape...
		
//		shape = new PolygonShape(points, 3E-3);
		shape = new PolygonShape(points, siz);

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
//			c = new Color((float)c.getRed()/255, (float)c.getGreen()/255, (float)c.getBlue()/255, PLANE_ALPHA_AT_BASE);
			c = fadeColor(c, PLANE_ALPHA_AT_BASE);
		
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
			new Bar(m.health/m.type.fullHealth, Color.green),
			new Bar(m.fuelInTank/m.type.tankCapacity, Color.blue),
			new Bar(m.militaryInHold/m.type.holdCapacity, Color.red),
			new Bar(m.fuelInHold/m.type.holdCapacity, Color.blue),
		}) {
			g2d.setColor(bar.second);
			y += height*2;
//			System.out.println(bar.first);
//			System.out.println(((double)bar.first)*((double)rightp.x-leftp.x));
//			g2d.fillRect(leftp.x, y, (int) Math.round(((double)bar.first)*(rightp.x-leftp.x)), height);
			g2d.fillRect(leftp.x, y, (int) Math.round(bar.first*(rightp.x-leftp.x)), height);
		}
		
		
		
		/////////////////////////////////////////////////
//		// DEBUG_MODE
//		
//		g2d.setColor(getPlayerColor(0));
//		
//		double size = entity.radius()*2;
//		Pixel left2 = vtrans.getViewPos(entity.modelView().position().shifted(-size/2).view());
//		Pixel right2 = vtrans.getViewPos(entity.modelView().position().shifted(size/2).view());
//		
//		g2d.drawOval (
//				left2.x,
//				left2.y,
//				right2.x-left2.x,
//				right2.y-left2.y
//			);
//		
		/////////////////////////////////////////////////
		
		
			//left.shift(0, height*2)
			//leftp.y += height*2; rightp.y += height*2;
		
	}
	
}















