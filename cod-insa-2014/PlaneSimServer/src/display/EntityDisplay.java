package display;

import game.GameEntity;

import java.awt.Color;
import java.awt.Graphics2D;


public abstract class EntityDisplay<T extends GameEntity> {
	
	final T entity;
	
	public EntityDisplay(T entity) {
		this.entity = entity;
	}
	
	public Color getPlayerColor() {
		switch(entity.modelView().ownerId()) {
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
	
	public abstract void draw (Graphics2D g2d, ViewTransform vtrans);

}
