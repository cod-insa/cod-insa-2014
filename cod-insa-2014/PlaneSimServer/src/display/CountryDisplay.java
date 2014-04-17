package display;

import game.GameCountry;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class CountryDisplay extends EntityDisplay<GameCountry> {

	public static Font font = BaseDisplay.font;
	
	public CountryDisplay(GameCountry entity) {
		super(entity);
	}
	
	@Override
	public void draw(Graphics2D g2d, ViewTransform vtrans) {
		super.draw(g2d, vtrans);

//		g2d.setColor(getPlayerColor());
		g2d.setColor(lightenColor(getPlayerColor()));

		double size = entity.radius()*2;
		Pixel topLeft = vtrans.getViewPos(entity.modelView().position().shifted(-size/2).view());
		Pixel bottomRight = vtrans.getViewPos(entity.modelView().position().shifted(size/2).view());
				
		g2d.fillOval (
				topLeft.x,
				topLeft.y,
				bottomRight.x-topLeft.x,
				bottomRight.y-topLeft.y
			);
	}
	
	@Override
	public void drawOverlay(Graphics2D g2d, ViewTransform vtrans) {

		double size = entity.radius()*2;
		Pixel topLeft = vtrans.getViewPos(entity.modelView().position().shifted(-size/2).view());
		Pixel bottomRight = vtrans.getViewPos(entity.modelView().position().shifted(size/2).view());
		
		if (vtrans._scale.x > .3) {

			int margin = 2;
			g2d.setColor(Color.black);
			g2d.drawString(entity.countryname, topLeft.x, bottomRight.y + font.getSize() + margin);

		}
		
	}
}
