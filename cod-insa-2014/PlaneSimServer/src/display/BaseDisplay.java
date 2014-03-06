package display;

import game.Base;

import java.awt.Color;
import java.awt.Graphics2D;


public class BaseDisplay extends EntityDisplay<Base> {

	public BaseDisplay (Base b) {
		super(b);
	}

	@Override
	public void draw(Graphics2D g2d, ViewTransform vtrans) {
		
		
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
		
		g2d.setColor(Color.red);
		
		double size = 5E-2;
		Pixel left = vtrans.getViewPos(entity.vModel.position.shifted(-size/2).view());
		Pixel right = vtrans.getViewPos(entity.vModel.position.shifted(size/2).view());
		
		g2d.fillOval (
				left.x,
				left.y,
				right.x-left.x,
				right.y-left.y
			);
		
	}
	
}

