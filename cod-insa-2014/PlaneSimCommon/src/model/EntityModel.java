package model;

import common.Copyable;
import common.Viewable;


public abstract class EntityModel implements Copyable { //, Viewable<EntityModel.View> {
	
	public final int id;
	
	public double rotation = 0;
	
	public boolean exists = true;
	
	/**
	 * 0 if this entity belongs to no one (player ids start at 1)
	 */
	public int ownerId;
	
	public class View implements Viewable.View {
		public Coord.View position() { return EntityModel.this.position(); }
		public int id() { return id; }
		public double rotation() { return rotation; }
		public boolean exists() { return exists; }
		public int ownerId() { return ownerId; }
	}
	
	public abstract Coord.View position();
	
	public void rotate (double angle) {
		rotation += angle;
		rotation %= Math.PI*2;
	}

	//public Entity(int id, Coord.Unique pos) {
//	public EntityModel(int id, Unique<Coord> pos) {
	public EntityModel(int id) {
		this.id = id;
		//_pos = pos.take();
		//position = _pos.view;
		//view = new View();
		//this.view = view;
	}
	public EntityModel(EntityModel.View src) {
		this(src.id());
		rotation = src.rotation();
	}
	
//	public EntityModel (EntityModel src) {
//		//this(src.id, Unique.Copy.make(src.position)));
//		this(src.id, Unique.Copy.make(src._pos));
//	}
//	
//	@Override public Object copy() {
//		return new EntityModel(this);
//	}
	
	public View view() {
		return new View();
	}
	
}







