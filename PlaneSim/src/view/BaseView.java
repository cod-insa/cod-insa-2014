package view;

import java.awt.Graphics2D;

import model.Base;

public class BaseView extends EntityView<Base> {

	public BaseView (Base b) {
		super(b);
	}

	@Override
	public void draw(Graphics2D g2d, ViewTransform vtrans) {
		
		Pixel corner = vtrans.getViewPos(entity.position);
		
		g2d.drawOval (
				corner.x,
				corner.y,
				10,
				10
			);
		
	}
	
}

