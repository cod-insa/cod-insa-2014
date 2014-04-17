package display;

import game.GameAxis;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

import model.Coord;

/**
 * Created by LP on 28/03/2014.
 */
public class AxisDisplay extends EntityDisplay<GameAxis> {

	public AxisDisplay (GameAxis b) {
		super(b);
	}

	@Override
	public Stroke getStroke(ViewTransform vtrans) {
//		return new BasicStroke(25*(float)vtrans._scale.x, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
		// entity.modelView().base1().radius // nope
		return axisStroke;
	}
	
	@Override
	public void draw(Graphics2D g2d, ViewTransform vtrans) {
		super.draw(g2d, vtrans);
		
		Coord.View
			c1 = entity.modelView().base1().position,
			c2 = entity.modelView().base2().position
		;
		
		// Creates many useless temporaries:
		// Coord bary = c2.addedTo(c1,-1).multipliedBy(entity.modelView().ratio()).addedTo(c1);
		
////		Coord bary = c2.copied();
////		bary.sub(c1);
//		Coord bary = c2.addedTo(c1, -1);
//		bary.mult(entity.modelView().ratio());
//		bary.add(c1);
//		
//		Pixel b1 = vtrans.getViewPos(c1);
//		Pixel b2 = vtrans.getViewPos(c2);
//		Pixel m = vtrans.getViewPos(bary.view());
//		
//		g2d.setColor(getPlayerColor(entity.modelView().base1().ownerId()));
//		g2d.drawLine(
//				b1.x,
//				b1.y,
//				m.x,
//				m.y
//		);
//		g2d.setColor(getPlayerColor(entity.modelView().base2().ownerId()));
//		g2d.drawLine(
//				m.x,
//				m.y,
//				b2.x,
//				b2.y
//		);
		
		assert entity.modelView().ratio1() + entity.modelView().ratio2() <= 1;

		Coord bary1 = c2.addedTo(c1, -1);
		bary1.mult(entity.modelView().ratio1());
		bary1.add(c1);
		
		Coord bary2 = c2.addedTo(c1, -1);
		bary2.mult(1-entity.modelView().ratio2());
		bary2.add(c1);
		
		Pixel b1 = vtrans.getViewPos(c1);
		Pixel b2 = vtrans.getViewPos(c2);
		Pixel m1 = vtrans.getViewPos(bary1.view());
		Pixel m2 = vtrans.getViewPos(bary2.view());

		float a = .3f;
//		float a = 1.5f;
		
//		g2d.setColor(fadeColor(getPlayerColor(entity.modelView().base1().ownerId()), a));
		g2d.setColor(lightenColor(getPlayerColor(entity.modelView().base1().ownerId())));
		g2d.drawLine(
				b1.x,
				b1.y,
				m1.x,
				m1.y
		);
		g2d.setColor(fadeColor(getPlayerColor(0), a));
		g2d.drawLine(
				m1.x,
				m1.y,
				m2.x,
				m2.y
		);
//		g2d.setColor(fadeColor(getPlayerColor(entity.modelView().base2().ownerId()), a));
		g2d.setColor(lightenColor(getPlayerColor(entity.modelView().base2().ownerId())));
		g2d.drawLine(
				m2.x,
				m2.y,
				b2.x,
				b2.y
		);
		
		
		
		
	}
	
}









