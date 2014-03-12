package model;

import java.util.Set;

import common.Unique;


public class MovingEntityModel extends EntityModel {
	
	public final Coord position;
	
	public double speed = 0; // TODO add to ctor
	
	
	public class View extends EntityModel.View {
		public double speed() { return speed; }
	}
	
	public MovingEntityModel(int id, Unique<Coord> pos) {
		super(id);
		position = pos.take();
	}
	
	public MovingEntityModel(MovingEntityModel.View src) {
		super(src);
//		rotation = src.rotation();
		rotation(src.rotation());
		position = src.position().copied();
		speed = src.speed();
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
	
}


