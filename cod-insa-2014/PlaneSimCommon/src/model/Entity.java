package model;

import model.Entity.View;
import common.Copyable;
import common.Viewable;


public abstract class Entity implements Copyable { //, Viewable<EntityModel.View> {
	
	public final int id;

	//public double rotation = 0;
	private double rotation = 0;
	
	public boolean exists = true;
	
	/**
	 * 0 if this entity belongs to no one (player ids start at 1)
	 */
	public int ownerId;
	
	
	private static final double DEFAULT_ENTITY_RADAR_RANGE = 0;
	public double radarRange;
	
	public class View implements Viewable.View {
//		public Coord.View position() { return EntityModel.this.position(); }
		public final Coord.View position = Entity.this.position();
		public Coord.View position() { return position; }
		
		public int id() { return id; }
		public double rotation() { return rotation; }
		public boolean exists() { return exists; }
		public int ownerId() { return ownerId; }
		public double radarRange() { return radarRange; }
		public boolean canSee(Entity.View e) {
			return false;
		}
		public boolean isWithinRadar(Coord.View pos) {
			return false;
		}
		public boolean isFriend(Entity.View e) {
			return ownerId == e.ownerId();
		}
		public final boolean isEnemy(Entity.View e) {
			return ownerId > 0 && ownerId != e.ownerId();
		}
	}
	
	public abstract Coord.View position();

	public double rotation () {
		return rotation;
	}
	public void rotation (double angle) {
		rotation = angle;
		rotation %= Math.PI*2;
		if (rotation > Math.PI)
			rotation -= Math.PI*2;
	}
	public void rotate (double angle_delta) {
//		rotation += angle;
//		rotation %= Math.PI*2;
//		if (rotation > Math.PI)
//			rotation -= Math.PI*2;
		rotation(rotation + angle_delta);
	}

	//public Entity(int id, Coord.Unique pos) {
//	public EntityModel(int id, Unique<Coord> pos) {
	public Entity(int id) {
		this.id = id;
		//_pos = pos.take();
		//position = _pos.view;
		//view = new View();
		//this.view = view;
		radarRange = DEFAULT_ENTITY_RADAR_RANGE;
	}
	public Entity(Entity.View src) {
		this(src.id());
		ownerId = src.ownerId();
		rotation = src.rotation();
		radarRange = src.radarRange();
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







