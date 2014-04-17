package display;

import game.GameEntity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;


public abstract class EntityDisplay<T extends GameEntity> {
	
	final T entity;
	
	public static Stroke fineStroke, largeStroke, axisStroke;
	
	public EntityDisplay(T entity) {
		this.entity = entity;
	}
	
	public static Color getPlayerColor(int ownerId) {
		switch(ownerId) {
		case 0:
//			return Color.gray;
//			return Color.lightGray;
			return setHSB(Color.black, null, null, .7f);
		case 1:
			return Color.blue;
		case 2:
			return Color.red;
		case 4:
			return Color.orange;
		case 3:
			return Color.magenta;
		case 5:
			return Color.green;
		case 6:
			return Color.cyan;	
		default:
			return Color.black;
		}
	}
	public Color getPlayerColor() {
		return getPlayerColor(entity.modelView().ownerId());
	}

	public static Color fadeColor(Color c, float alpha) {
		return new Color((float)c.getRed()/255, (float)c.getGreen()/255, (float)c.getBlue()/255, alpha);
	}

//	/**
//	 * FIXEDME: out of range colors
//	 * TODO: better algo using coeff
//	 */
//	public static Color lightenColor(Color c) { //, float coeff) {
////		return new Color(c.getRed()*coeff, c.getGreen()*coeff, (float)c.getBlue()*coeff);/
//		
//		float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
//
////		hsb[2] *= coeff;
////		hsb[2] = .5f*(1f+coeff);
////		hsb[2] = 1;
//		if (hsb[2] > 1) hsb[2] = 1;
//		else if (hsb[2] < 0) hsb[2] = 0;
//
//		hsb[1] = .5f;
//		
//		
//		return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
//		
//	}
public static Color lightenColor(Color c, float coeff) {
	return setHSB(c, null, coeff, null);
}
	public static Color lightenColor(Color c) {
		return setHSB(c, null, .5f, null);
	}
	
	public static Color setHSB(Color c, Float hue, Float saturation, Float brightness) {
		float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		
		if (hue != null)
			hsb[0] = hue;
		if (saturation != null)
			hsb[1] = saturation;
		if (brightness != null)
			hsb[2] = brightness;

		return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));

	}
	
//	public static Color ChangeColorBrightness(Color color, float correctionFactor)
//	{
//		float red = (float)color.getRed();
//		float green = (float)color.getGreen();
//		float blue = (float)color.getBlue();
//
//		if (correctionFactor < 0)
//		{
//			correctionFactor = 1 + correctionFactor;
//			red *= correctionFactor;
//			green *= correctionFactor;
//			blue *= correctionFactor;
//		}
//		else
//		{
//			red = (255 - red) * correctionFactor + red;
//			green = (255 - green) * correctionFactor + green;
//			blue = (255 - blue) * correctionFactor + blue;
//		}
//
//		return Color.FromArgb(color.getAlpha(), (int)red, (int)green, (int)blue);
//	}
	
	
	
	
	public void draw (Graphics2D g2d, ViewTransform vtrans) {
		init(g2d, vtrans);
	}
	public void drawOverlay (Graphics2D g2d, ViewTransform vtrans) {
		
	}
	
	//public abstract new BasicStroke(4*(float)vtrans._scale.x, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
	
	public Stroke getStroke(ViewTransform vtrans) {
//		return new BasicStroke(4*(float)vtrans._scale.x, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
		return largeStroke;
	}
	
	public void init(Graphics2D g2d, ViewTransform vtrans) {
		g2d.setStroke(getStroke(vtrans));
	}

}
