package model;

import java.io.Serializable;
import java.util.Set;

import common.Unique;
import common.Viewable;

public class Plane extends MovingEntityModel implements Serializable, Viewable<Plane.View> {
	
	private static final long serialVersionUID = 1L;
	private static final double DEFAULT_RANGE = 10;

	public double health; // = 1;
	public State state; // don't try to modify this: it is controlled by the autoPilot
	public double radarRange;

	public class View extends MovingEntityModel.View {
		public double health() { return health; }
		public State state() { return state; }
		public double radarRange() { return radarRange; }
		
		public Plane copied(Set<Object> context) {
//			if (context.contains(PlaneModel.this)) return PlaneModel.this;
//			return new BaseModel(BaseModel.this);
			return copy(context);
		}
	}
	
	public enum State {
		IDLE,
		GOING_TO,
		FOLLOWING,
		ATTACKING,
		LANDING,
		AT_AIRPORT,
		DEAD, // FIXME use it
	}
	
	@Override public View view() {
		return new View();
	}
	
//	public final View view = new View();
	
	
	public Plane (int id, Unique<Coord> pos, double health, State state) {
		//super(id,pos);
		super(id, pos);
		this.health = health;
		this.state = state;
		this.radarRange = DEFAULT_RANGE;
	}
	
	public Plane (Plane.View p) {
		super(p);
		health = p.health();
		state = p.state();
	}
	
	@Override
	public Plane copy (Set<Object> context) {
		if (context.contains(this)) return this;
		Plane ret = new Plane(view());
		return ret;
	}

	public boolean isFlying() {
		return state != State.AT_AIRPORT && state != State.DEAD;
	}
	
}











