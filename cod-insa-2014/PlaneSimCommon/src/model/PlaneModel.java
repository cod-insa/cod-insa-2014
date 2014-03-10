package model;

import java.io.Serializable;
import java.util.Set;

import common.Unique;
import common.Viewable;

public class PlaneModel extends MovingEntityModel implements Serializable, Viewable<PlaneModel.View> {
	
	private static final long serialVersionUID = 1L;
	

	public double health; // = 1;
	public State state; // = State.IDLE;
	

	public class View extends MovingEntityModel.View {
		public double health() { return health; }
		public State state() { return state; }
		
		public PlaneModel copied(Set<Object> context) {
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
		AT_AIRPORT,
		DEAD // FIXME use it
	}
	
	@Override public View view() {
		return new View();
	}
	
//	public final View view = new View();
	
	
	public PlaneModel (int id, Unique<Coord> pos, double health, State state) {
		//super(id,pos);
		super(id, pos);
		this.health = health;
		this.state = state;
	}
	
	public PlaneModel (PlaneModel.View p) {
		super(p);
		health = p.health();
		state = p.state();
	}
	
	@Override
	public PlaneModel copy (Set<Object> context) {
		if (context.contains(this)) return this;
		PlaneModel ret = new PlaneModel(view());
		return ret;
	}

	public boolean isFlying() {
		return state != State.AT_AIRPORT && state != State.DEAD;
	}
	
}











