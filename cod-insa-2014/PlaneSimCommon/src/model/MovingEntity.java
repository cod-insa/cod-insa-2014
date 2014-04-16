package model;

import java.util.Set;

import common.Unique;


public class MovingEntity extends MaterialEntity {
	
	public final Coord position, inertia;
	
	public double speed = 0; // TODO add to ctor
	
	/**
	 * This class represents a Moving Entity
	 */
	public class View extends MaterialEntity.View {
		public double speed() { return speed; }
		Coord.View inertia() { return inertia.view(); }
	}
	
	public MovingEntity(int id, Unique<Coord> pos, Unique<Coord> ine) {
		super(id);
		position = pos.take();
		inertia = ine.take();
	}
	
	public MovingEntity(MovingEntity.View src) {
		super(src);
//		rotation = src.rotation();
		rotation(src.rotation());
		position = src.position().copied();
		speed = src.speed();
		inertia = src.inertia().copied();
	}

	@Override
	public model.Coord.View position() {
		return position.view();
	}
	
	@Override public View view() {
		return new View();
	}

	@Override
	public MovingEntity copy (Context context) {
//		if (context.containsKey(this)) return (MovingEntity) context.get(this);
		if (context.containsKey(this)) return context.getSafe(this);
		MovingEntity ret = new MovingEntity(new View());
		context.putSafe(this, ret);
		return ret;
	}
	
//	public Coord speedVector() {
//		return new Coord(Math.cos(rotation())*speed, Math.sin(rotation())*speed);
//	}
	public Unique<Coord> speedVector() {
		return new Coord.Unique(Math.cos(rotation())*speed, Math.sin(rotation())*speed);
	}
	
}












