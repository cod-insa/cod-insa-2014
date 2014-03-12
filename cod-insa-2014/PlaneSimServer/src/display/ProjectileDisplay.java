package display;

import game.Projectile;

import java.awt.Color;
import java.awt.Graphics2D;

import model.Coord;


public class ProjectileDisplay extends EntityDisplay<Projectile> {
	
	public ProjectileDisplay (Projectile p) {
		super(p);
		
	}

	@Override
	public void draw(Graphics2D g2d, ViewTransform vtrans) {
		
		float alpha = entity.distToCover() < Projectile.INEFFECTIVE_RANGE?
				(float)(entity.distToCover() / Projectile.INEFFECTIVE_RANGE) : 1;
		
		if (alpha < 0)
			alpha = 0;
		
		//g2d.setColor(Color.yellow);
		//g2d.setColor(new Color(1f, 1f, 0f, .4f));
		g2d.setColor(new Color(1f, 1f, 0f, alpha));
		
//		Pixel first = vtrans.getViewPos(entity.lastPosition);
//		Pixel second = vtrans.getViewPos(entity.model().position());
		
		/** Shortened: */
		Coord.View endPosition = entity.modelView().position();
		//Coord inBetween = end.addedTo(entity.lastPosition, -1);
		Coord inBetween = entity.lastPosition.addedTo(endPosition, -1);
		inBetween.mult(.9);
		inBetween.add(endPosition);
		
		Pixel first = vtrans.getViewPos(inBetween.view());
		Pixel second = vtrans.getViewPos(endPosition);
		//*/
		
		g2d.drawLine(first.x, first.y, second.x, second.y);
		
	}
	
}



