package display;

import java.awt.Graphics2D;

import model.Entity;

public abstract class EntityDisplay<T extends Entity> {
	
	T entity;
	
	public EntityDisplay(T entity) {
		this.entity = entity;
	}
	
	public abstract void draw (Graphics2D g2d, ViewTransform vtrans);

}
