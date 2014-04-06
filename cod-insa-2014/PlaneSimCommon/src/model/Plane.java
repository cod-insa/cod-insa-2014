package model;

import java.io.Serializable;

import common.Unique;
import common.Viewable;

public class Plane extends MovingEntity implements Serializable, Viewable<Plane.FullView> {
	
	private static final long serialVersionUID = 1L;
	//private static final double DEFAULT_RANGE = 0.7;

	public double health; // = 1;
	public State state; // don't try to modify this: it is controlled by the autoPilot
	public Base curBase; // no sense if state != State.AT_AIRPORT
	
	// Hold
	public double militarResourceCarried;
	public double fuelResourceCarried;
	public double capacityHold;
	
	// Tank
	public double remainingGaz;
	public double capacityTank;
	
	public double fireRange;
	
	private static final double DEFAULT_CAPACITY_HOLD = 10;
	private static final double DEFAULT_CAPACITY_TANK = 10;
	private static final double DEFAULT_PLANE_RADAR_RANGE = 0.7;
	private static final double DEFAULT_FIRE_RANGE_MILITAR = 0.7;
	private static final double DEFAULT_FIRE_RANGE_COMMERCIAL = 0;
	
	private static final double DEFAULT_INIT_GAZ = 100;

	// Basically, FullView is a BasicView plus some additional visible things 
	public class FullView extends BasicView
	{
		public State state() { return state; }
		public Base.View curBase() { return curBase == null ? null : curBase.view(); }
		public double militarResourceCarried() { return militarResourceCarried; }
		public double fuelResourceCarried() { return fuelResourceCarried; }
		public double remainingGaz() { return remainingGaz; }
		public double capacityHold() { return capacityHold; }
		public double capacityTank() { return capacityTank; }
		@Override
		public boolean isWithinRadar(Coord.View pos) {
			return position.squareDistanceTo(pos) <= radarRange*radarRange;
		}
		
		public Plane copied(Context context) {
//			if (context.contains(PlaneModel.this)) return PlaneModel.this;
//			return new BaseModel(BaseModel.this);
			return copy(context);
		}

		public boolean knowsPositionOf(MaterialEntity.View e) {
			if (e instanceof Base.View)
				return true;
			return isFriend(e) || canSee(e);
		}
		
		@Override
//		public boolean canSee(Entity.View e) {
		public boolean canSee(MaterialEntity.View e) {
			if (e instanceof Plane.FullView && ((Plane.FullView)e).state() == State.AT_AIRPORT)
				return false;
			return isWithinRadar(e.position);
		}
		
		public boolean canAttack(Plane.FullView e) {
			return isEnemy(e) && canSee(e);
		}
		
		@Override
		public String toString() { return super.toString()+" state:"+state()+" fuel:"+fuelResourceCarried()+" mil:"+militarResourceCarried(); }
		
		
//		private Plane model() { return Plane.this; }
	}
	
	// This is what an AI will see for an ennemy plane
	public class BasicView extends MovingEntity.View {
		public double health() { return health; }
		public boolean canAttack() {
			return fireRange > 0;
		}
		public double fireRange() { return fireRange; }
		public double radarRange() { return radarRange;	}
		@Override
		public String toString() { return (exists()?"":"[dead] ")+"Plane "+id()+" owner:"+ownerId()+" health:"+health(); }
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
	
	
	public Plane (int id, Unique<Coord> pos, double health, boolean isMilitar) {
		//super(id,pos);
		super(id, pos, new Coord.Unique(0,0));
		this.health = health;
//		this.state = state;
		this.fireRange = isMilitar ? DEFAULT_FIRE_RANGE_MILITAR : DEFAULT_FIRE_RANGE_COMMERCIAL;
		this.radarRange = DEFAULT_PLANE_RADAR_RANGE;
		this.capacityHold = DEFAULT_CAPACITY_HOLD;
		this.capacityTank = DEFAULT_CAPACITY_TANK;
		this.remainingGaz = DEFAULT_INIT_GAZ;
		fuelResourceCarried = militarResourceCarried = 0;
	}
	
	public Plane (Plane.FullView src, Context context) {
		super(src);
		context.putSafe(src.model(), this);
		health = src.health();
		state = src.state();
		radarRange = src.radarRange();
		fireRange = src.fireRange();
		capacityHold = src.capacityHold();
		capacityTank = src.capacityTank();
		remainingGaz = src.remainingGaz();
		fuelResourceCarried = src.fuelResourceCarried();
		militarResourceCarried = src.militarResourceCarried();
		if (src.curBase() != null)
			curBase = src.curBase().copied(context);
	}
	
	@Override
	public Plane copy (Context context) {
		if (context.containsKey(this))
//			return (Plane) context.get(this);
			return (Plane) context.getSafe(this);
		Plane ret = new Plane(view(),context);
		return ret;
	}

	public boolean isFlying() {
		return state != State.AT_AIRPORT && state != State.DEAD;
	}
	
	public void assignTo(Base b)
	{
		unAssign();
		this.curBase = b;
		b.planes.add(this);
	}
	
	public void unAssign()
	{
		if (curBase != null)
		{
			curBase.planes.remove(this);
			this.curBase = null;
		}
	}
	
}











