package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.Plane;

public class PlaneView extends EntityView<Plane> {
	
	PolygonShape shape;

	public PlaneView (Plane p) {
		super(p);
		
		ArrayList<Point2D.Double> points = new ArrayList<>();
		/*
		points.add(new Point2D.Double(0, -5));
		points.add(new Point2D.Double(5, 10));
		points.add(new Point2D.Double(-5, 10));
		*/
		points.add(new Point2D.Double(5, 0));
		points.add(new Point2D.Double(-10, -5));
		points.add(new Point2D.Double(-10, 5));
		
		shape = new PolygonShape(points, 3E-3);
	}

	@Override
	public void draw(Graphics2D g2d, ViewTransform vtrans) {
		
		//int s = 20;
		
		//System.out.println(vtrans.scale.x());
		
		/*
		int s = (int) (vtrans.vScale.x()*20);
		Pixel corner = vtrans.getViewPos(entity.position);
		g2d.drawRect (
				corner.x,
				corner.y,
				s,
				s
			);
		*/
		
		//shape.translate(entity.position.x(), entity.position.y());
		//shape.translate(entity.position);
		
		g2d.draw(shape.toPolygon(entity.position, entity.rotation(), vtrans));
		
		
		
	}
	
}



