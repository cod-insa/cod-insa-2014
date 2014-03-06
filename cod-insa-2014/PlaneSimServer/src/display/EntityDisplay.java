package display;

import game.Entity;

import java.awt.Graphics2D;


public abstract class EntityDisplay<T extends Entity<?>> {
	
	final T entity;
	
	public EntityDisplay(T entity) {
		this.entity = entity;
	}
	
	public abstract void draw (Graphics2D g2d, ViewTransform vtrans);

}
