package model;

import common.Copyable;
import common.Viewable;


public abstract class Entity implements Copyable { //, Viewable<EntityModel.View> {
	
	public final int id;
	
	public boolean exists = true;
	
	/**
	 * Nil (0) if this entity belongs to no one (player ids start at 1)
	 */
	//public int ownerId = 0;
	private int ownerId = 0;

	/**
	 * This class represents an entity
	 */
	public class View implements Viewable.View {
		
		/**
		 * Return the id of the entity
		 */
		public int id() { return id; }
		/**
		 * This function will return true if the entity is still in the game
		 */
		public boolean exists() { return exists; }
		/**
		 * Return the ownerId of the entity
		 */
		public int ownerId() { return ownerId; }
		/**
		 * Returns false by default (for an Entity)
		 */
		public boolean isWithinRadar(Coord.View pos) {
			return false;
		}
		/**
		 * Return if the unit e is of the same AI
		 * @param e The potential friend
		 */
		public boolean isFriend(Entity.View e) {
			return ownerId == e.ownerId();
		}
		
		/**
		 * Return if the unit is an ennemy
		 * @param e The potential ennemy
		 */
		public final boolean isEnemy(Entity.View e) {
			return ownerId > 0 && ownerId != e.ownerId();
		}
		
		protected Entity model() { return Entity.this; }
	}
	
	public boolean owned() {
		return ownerId != 0;
	}
	
	//public Entity(int id, Coord.Unique pos) {
//	public EntityModel(int id, Unique<Coord> pos) {
	public Entity(int id) {
		this.id = id;
		//_pos = pos.take();
		//position = _pos.view;
		//view = new View();
		//this.view = view;
//		radarRange = DEFAULT_ENTITY_RADAR_RANGE;
	}
	public Entity(Entity.View src) {
		this(src.id());
		ownerId = src.ownerId();
//		rotation = src.rotation();
//		radarRange = src.radarRange();
	}

	public int ownerId() { return ownerId; }
	public void ownerId(int id) { ownerId = id; } // TODO: check >= 0
	
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







