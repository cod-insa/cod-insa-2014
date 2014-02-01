package view;

import java.awt.Graphics2D;

import model.Entity;

public abstract class EntityView<T extends Entity> {
	
	T entity;
	
	public EntityView(T entity) {
		this.entity = entity;
	}
	
	public abstract void draw (Graphics2D g2d, ViewTransform vtrans);

}
