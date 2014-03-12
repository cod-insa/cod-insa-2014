package model;

import java.util.Set;

import common.Unique;


public class MovingEntityModel extends Entity {
	
	public final Coord position, inertia;
	
	public double speed = 0; // TODO add to ctor
	
	
	public class View extends Entity.View {
		public double speed() { return speed; }
		public Coord.View inertia() { return inertia.view(); }
	}
	
	public MovingEntityModel(int id, Unique<Coord> pos, Unique<Coord> ine) {
		super(id);
		position = pos.take();
		inertia = ine.take();
	}
	
	public MovingEntityModel(MovingEntityModel.View src) {
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
	public MovingEntityModel copy (Set<Object> context) {
		if (context.contains(this)) return this;
		MovingEntityModel ret = new MovingEntityModel(new View());
		context.add(ret);
		return ret;
	}
	
//	public Coord speedVector() {
//		return new Coord(Math.cos(rotation())*speed, Math.sin(rotation())*speed);
//	}
	public Unique<Coord> speedVector() {
		return new Coord.Unique(Math.cos(rotation())*speed, Math.sin(rotation())*speed);
	}
	
}












