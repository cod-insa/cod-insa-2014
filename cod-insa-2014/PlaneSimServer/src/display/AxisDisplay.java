package display;

import game.GameAxis;
import model.Coord;

import java.awt.*;

/**
 * Created by LP on 28/03/2014.
 */
public class AxisDisplay extends EntityDisplay<GameAxis> {

	public AxisDisplay (GameAxis b) {
		super(b);
	}

	@Override
	public Stroke getStroke(ViewTransform vtrans) {
		return new BasicStroke(25*(float)vtrans._scale.x, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
		// entity.modelView().base1().radius // nope
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
		
//		Coord bary = c2.copied();
//		bary.sub(c1);
		Coord bary = c2.addedTo(c1, -1);
		bary.mult(entity.modelView().ratio());
		bary.add(c1);
		
		Pixel b1 = vtrans.getViewPos(c1);
		Pixel b2 = vtrans.getViewPos(c2);
		Pixel m = vtrans.getViewPos(bary.view());
		
		g2d.setColor(getPlayerColor(entity.modelView().base1().ownerId()));
		g2d.drawLine(
				b1.x,
				b1.y,
				m.x,
				m.y
		);
		g2d.setColor(getPlayerColor(entity.modelView().base2().ownerId()));
		g2d.drawLine(
				m.x,
				m.y,
				b2.x,
				b2.y
		);
		
	}
	
}









