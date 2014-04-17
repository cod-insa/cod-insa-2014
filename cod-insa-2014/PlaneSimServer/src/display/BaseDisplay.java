package display;

import game.GameBase;

import java.awt.*;


public class BaseDisplay extends EntityDisplay<GameBase> {

//	public static Font font = new Font("Arial", Font.BOLD, 15);
	public static Font font = new Font("Arial", Font.PLAIN, 15);
	
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
		
//		g2d.setColor(getPlayerColor());
		if (entity.model().ownerId() == 0)
		     g2d.setColor(getPlayerColor());
		else g2d.setColor(lightenColor(getPlayerColor()));
		
		//double size = 5E-2;
		double size = entity.radius()*2;
		Pixel topLeft = vtrans.getViewPos(entity.modelView().position().shifted(-size/2).view());
		Pixel bottomRight = vtrans.getViewPos(entity.modelView().position().shifted(size/2).view());
		
		g2d.fillOval (
				topLeft.x,
				topLeft.y,
				bottomRight.x-topLeft.x,
				bottomRight.y-topLeft.y
			);
//		java.awt.Font.
		
	}
	
	@Override
	public void drawOverlay(Graphics2D g2d, ViewTransform vtrans) {

		double size = entity.radius()*2;
		Pixel topLeft = vtrans.getViewPos(entity.modelView().position().shifted(-size/2).view());
		Pixel bottomRight = vtrans.getViewPos(entity.modelView().position().shifted(size/2).view());
		
		if (vtrans._scale.x > .3) {

			int margin = 2;
			g2d.setFont(font);
			//		g2d.setColor(Color.white);
			//		g2d.drawString(entity.cityname, left.x, right.y);
			g2d.setColor(Color.black);
			g2d.drawString(

					/////////////////////////////////////
					// FIXME: testing
					"("+entity.id()+") "+
					/////////////////////////////////////
					
					entity.cityName, topLeft.x, bottomRight.y + font.getSize() + margin);
			g2d.setColor(Color.red);
			g2d.drawString("Mil: " + Math.round(10*entity.modelView().militaryGarrison())/10.0,
					topLeft.x, bottomRight.y + font.getSize() * 2 + margin * 2);
			g2d.setColor(Color.blue);
			g2d.drawString("Fuel: " + Math.round(10*entity.modelView().fuelInStock())/10.0,
					topLeft.x, bottomRight.y + font.getSize() * 3 + margin * 3);

		}
		
	}
	
}

