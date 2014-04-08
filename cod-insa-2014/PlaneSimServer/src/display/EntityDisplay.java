package display;

import game.GameEntity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;


public abstract class EntityDisplay<T extends GameEntity> {
	
	final T entity;
	
	public EntityDisplay(T entity) {
		this.entity = entity;
	}
	
	public static Color getPlayerColor(int ownerId) {
		switch(ownerId) {
		case 0:
			return Color.gray;
		case 1:
			return Color.blue;
		case 2:
			return Color.red;
		case 3:
			return Color.green;
		case 4:
			return Color.magenta;
		case 5:
			return Color.cyan;
		case 6:
			return Color.orange;
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
	
	public void draw (Graphics2D g2d, ViewTransform vtrans) {
		init(g2d, vtrans);
	}
	
	//public abstract new BasicStroke(4*(float)vtrans._scale.x, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
	
	public Stroke getStroke(ViewTransform vtrans) {
		return new BasicStroke(4*(float)vtrans._scale.x, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
	}
	
	public void init(Graphics2D g2d, ViewTransform vtrans) {
		g2d.setStroke(getStroke(vtrans));
	}

}
