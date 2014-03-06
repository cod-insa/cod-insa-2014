package model;

import common.Copyable;
import common.Viewable;


public abstract class EntityModel implements Copyable { //, Viewable<EntityModel.View> {
	
	public final int id;
	
	//public final View view;
	
	//public final Coord _pos;// = new Coord(0,0);
	//public final EntityView<?> view;
	
	public double rotation = 0;
	//double _alt = 1;
	public boolean exists = true;
	
	/**
	 * 0 if this entity belongs to no one (player ids start at 1)
	 */
	public int ownerId;
	
	public class View implements Viewable.View {
		//public final Coord.View position = _pos.view;
		public Coord.View position() { return EntityModel.this.position(); }
		public int id() { return id; }
		public double rotation() { return rotation; }
		public boolean exists() { return exists; }
		public int ownerId() { return ownerId; }
	}
	
	//public final Coord.View position;// = _pos.view;
	public abstract Coord.View position();
	
//	// FIXME: are these functions actually useful?
//	public double rotation() { return rotation; }
//	public double speed() { return speed; }
//	//public double altitude() { return _alt; }
//	public boolean exists() { return exists; }
	
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







