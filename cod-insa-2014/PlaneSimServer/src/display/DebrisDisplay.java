package display;

import game.Debris;
import model.Coord;

import java.awt.Graphics2D;


public class DebrisDisplay extends EntityDisplay<Debris> {
	
	public DebrisDisplay (Debris d) {
		super(d);
		
	}
	
	@Override
	public void draw(Graphics2D g2d, ViewTransform vtrans) {
		super.draw(g2d, vtrans);
		
		float alpha = entity.timeToLive() < Debris.FADING_RANGE?
				(float)(entity.timeToLive() / Debris.FADING_RANGE) : 1;
		
		if (alpha < 0)
			alpha = 0;

		g2d.setColor(fadeColor(getPlayerColor(), alpha));
		
		double r = entity.modelView().rotation();
		Coord.View vector = new Coord(
				Math.cos(r)*entity.radius()*2,
				Math.sin(r)*entity.radius()*2
			).view();
		Coord c = entity.modelView().position.addedTo(vector, -.5);
		Pixel first = vtrans.getViewPos(c.view());
		c.add(vector);
		Pixel second = vtrans.getViewPos(c.view());
		
		g2d.drawLine(first.x, first.y, second.x, second.y);
		
	}
	
}



