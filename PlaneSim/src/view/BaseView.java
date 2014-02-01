package view;

import java.awt.Graphics2D;

import model.Base;

public class BaseView extends EntityView<Base> {

	public BaseView (Base b) {
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
		
		double size = 5E-2;
		Pixel left = vtrans.getViewPos(entity.position.add(-size/2).view);
		Pixel right = vtrans.getViewPos(entity.position.add(size/2).view);
		
		g2d.drawOval (
				left.x,
				left.y,
				right.x-left.x,
				right.y-left.y
			);
		
	}
	
}

