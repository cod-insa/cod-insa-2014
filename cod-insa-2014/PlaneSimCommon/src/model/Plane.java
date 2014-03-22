package model;

import java.io.Serializable;
import java.util.Set;

import common.Unique;
import common.Viewable;

public class Plane extends MovingEntity implements Serializable, Viewable<Plane.FullView> {
	
	private static final long serialVersionUID = 1L;
	//private static final double DEFAULT_RANGE = 0.7;

	public double health; // = 1;
	public State state; // don't try to modify this: it is controlled by the autoPilot
	public Base curBase; // no sense if state != State.AT_AIRPORT
	private static final double DEFAULT_PLANE_RADAR_RANGE = 0.7;

	// Basically, FullView is a BasicView plus some additional things that are visible
	public class FullView extends BasicView {
		public State state() { return state; }
		public Base.View curBase() { return curBase == null ? null : curBase.view(); }
		@Override
		public boolean isWithinRadar(Coord.View pos) {
			return position.squareDistanceTo(pos) <= radarRange*radarRange;
		}

		public Plane copied(Set<Object> context) {
//			if (context.contains(PlaneModel.this)) return PlaneModel.this;
//			return new BaseModel(BaseModel.this);
			return copy(context);
		}

		public boolean knowsPositionOf(Entity.View e) {
			if (e instanceof Base.View)
				return true;
			return isFriend(e) || canSee(e);
		}

		public boolean canAttack(Plane.FullView e) {
			return isEnemy(e) && canSee(e);
		}
	}
	
	// This is what an AI will see for an ennemy plane
	public class BasicView extends MovingEntity.View {
		public double health() { return health; }
		
		@Override
		public boolean canSee(Entity.View e) {
			if (e instanceof Plane.FullView && ((Plane.FullView)e).state() == State.AT_AIRPORT)
				return false;
			return isWithinRadar(e.position);
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
	
	@Override public FullView view() {
		return new FullView();
	}
	
	public BasicView restrictedView() {
		return new BasicView();
	}
	
//	public final View view = new View();
	
	
	public Plane (int id, Unique<Coord> pos, double health, State state) {
		//super(id,pos);
		super(id, pos, new Coord.Unique(0,0));
		this.health = health;
		this.state = state;
		this.radarRange = DEFAULT_PLANE_RADAR_RANGE;
	}
	
	public Plane (Plane.FullView p, Set<Object> context) {
		super(p);
		context.add(this);
		health = p.health();
		state = p.state();
		if (p.curBase() != null)
			curBase = p.curBase().copied(context);
	}
	
	@Override
	public Plane copy (Set<Object> context) {
		if (context.contains(this)) 
			return this;
		Plane ret = new Plane(view(),context);
		return ret;
	}

	public boolean isFlying() {
		return state != State.AT_AIRPORT && state != State.DEAD;
	}
	
	public void assignTo(Base b)
	{
		this.curBase = b;
		b.planes.add(this);
	}
	
	public void unassign()
	{
		if (curBase != null)
		{
			curBase.planes.remove(this);
			this.curBase = null;
		}
	}
	
}











