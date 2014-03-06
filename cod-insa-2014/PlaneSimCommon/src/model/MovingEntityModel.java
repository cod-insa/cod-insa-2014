package model;


public abstract class MovingEntityModel extends EntityModel {
	
	
	public final Coord position;
	
	public double speed = 0; // TODO add to ctor
	
	
	public class View extends EntityModel.View {
		public double speed() { return speed; }
	}
	
	public MovingEntityModel(int id, Coord.Unique pos) {
		super(id);
		position = pos.take();
	}
	
	public MovingEntityModel(MovingEntityModel.View src) {
		super(src);
		rotation = src.rotation();
		position = src.position().copied();
		speed = src.speed();
	}

	@Override
	public model.Coord.View position() {
		return position.view();
	}
	
}
