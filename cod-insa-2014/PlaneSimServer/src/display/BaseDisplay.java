package display;

import game.GameBase;

import java.awt.Graphics2D;


public class BaseDisplay extends EntityDisplay<GameBase> {

	public BaseDisplay (GameBase b) {
		super(b);
	}

	@Override
	public void draw(Graphics2D g2d, ViewTransform vtrans) {
		super.draw(g2d, vtrans);
		
		/*
		Pixel corner = vtrans.getViewPos(entity.position);
		int s = (int) (vtrans.scale.x());
		g2d.drawOval (
				corner.x-s,
				corner.y-s,
				s*2,
				s*2
			);
		*/
		
		g2d.setColor(getPlayerColor());
		
		//double size = 5E-2;
		double size = entity.radius()*2;
		Pixel left = vtrans.getViewPos(entity.modelView().position().shifted(-size/2).view());
		Pixel right = vtrans.getViewPos(entity.modelView().position().shifted(size/2).view());
		
		g2d.fillOval (
				left.x,
				left.y,
				right.x-left.x,
				right.y-left.y
			);
		
	}
	
}

