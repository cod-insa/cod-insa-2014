package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import common.Unique;
import common.Viewable;

public class Plane extends MovingEntity implements Serializable, Viewable<Plane.FullView> {
	
	private static final long serialVersionUID = 1L;
	
	public final Type type;
	
	//private static final double DEFAULT_RANGE = 0.7;
	
	public State state; // don't try to modify this: it is controlled by the autoPilot
	public Base curBase; // no signification if state != State.AT_AIRPORT
	
	public double health; // = 1;
	
	// Hold (soute)
	public double militaryInHold;
	public double fuelInHold;
//	public double holdCapacity;
	
	// Tank (reservoir)
	public double fuelInTank;
//	public double tankCapacity;
	
//	public final double fireRange;
	
//	private static final double DEFAULT_HOLD_CAPACITY = 10;
//	private static final double DEFAULT_TANK_CAPACITY = 10;
//	private static final double DEFAULT_PLANE_RADAR_RANGE = 0.7;
//	private static final double DEFAULT_FIRE_RANGE_MILITARY = 0.7;
//	private static final double DEFAULT_FIRE_RANGE_COMMERCIAL = 0;
//	
//	private static final double DEFAULT_INIT_GAZ = 10;

	// Basically, FullView is a BasicView plus some additional visible things 
	public class FullView extends BasicView
	{
		public State state() { return state; }
		public Base.FullView curBase() { return curBase == null ? null : curBase.view(); }
		
		public double militaryInHold() { return militaryInHold; }
		public double fuelInHold() { return fuelInHold; }
		public double fuelInTank() { return fuelInTank; }
//		public double holdCapacity() { return holdCapacity; }
//		public double tankCapacity() { return tankCapacity; }
		
		public double health() { return health; }
		
		public final Type type = Plane.this.type;
		
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
			if (e instanceof Base.FullView)
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
		public String toString() { return super.toString()+" state:"+state()+" fuel:"+fuelInHold()+" mil:"+militaryInHold(); }
		
		
//		private Plane model() { return Plane.this; }
	}
	
	// This is what an AI will see for an ennemy plane
	public class BasicView extends MovingEntity.View {
		public double health() { return health; }
		public boolean canAttack() {
			return type.firingRange > 0;
		}
//		public double fireRange() { return fireRange; }
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
	
	
	public Plane (int id, Unique<Coord> pos, Type type) {
		//super(id,pos);
		super(id, pos, new Coord.Unique(0,0));
//		this.health = health;
//		this.state = state;
//		this.fireRange = isMilitar ? DEFAULT_FIRE_RANGE_MILITARY : DEFAULT_FIRE_RANGE_COMMERCIAL;
//		this.radarRange = DEFAULT_PLANE_RADAR_RANGE;
//		this.holdCapacity = DEFAULT_HOLD_CAPACITY;
//		this.tankCapacity = DEFAULT_TANK_CAPACITY;
//		this.fuelInTank = DEFAULT_INIT_GAZ;
		
		this.type = type;
		
		health = type.fullHealth;
		radarRange = type.radarRange;
		fuelInTank = type.tankCapacity;
		fuelInHold = 0;
		militaryInHold = 0;
		
	}
	
	public Plane (Plane.FullView src, Context context) {
		super(src);
		context.putSafe(src.model(), this);
		health = src.health();
		state = src.state();
		radarRange = src.radarRange();
//		fireRange = src.fireRange();
//		holdCapacity = src.holdCapacity();
//		tankCapacity = src.tankCapacity();
		fuelInTank = src.fuelInTank();
		fuelInHold = src.fuelInHold();
		militaryInHold = src.militaryInHold();
		type = src.type;
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
	
	
	public static class Type {
		
		private static final List<Type> instances = new ArrayList<>();
		
		public static Type get (int id) {
			return instances.get(id);
		}
		
		public final int id;
		
		public final double
			
			firingRange,
			radarRange,
			
			fullHealth,
			
			holdCapacity,
			tankCapacity,
			
			fuelConsumptionPerDistanceUnit,
			
			radius
			
		;
		
		private Type(
				double firingRange,
				double radarRange,
				double fullHealth,
				double holdCapacity,
				double tankCapacity,
				double fuelConsumptionPerDistanceUnit,
				double radius
		) {
			
			id = instances.size();
			instances.add(this);
			
			this.firingRange = firingRange;
			this.radarRange = radarRange;
			
			this.fullHealth = fullHealth;
			
			this.holdCapacity = holdCapacity;
			this.tankCapacity = tankCapacity;
			
			this.fuelConsumptionPerDistanceUnit = fuelConsumptionPerDistanceUnit;
			
			this.radius = radius;
			
		}

		public static final Type MILITARY = new Type(
				// firingRange
				0.7,
				// radarRange
				0.7,
				// fullHealth
				100,
				// holdCapacity
				10,
				// tankCapacity
				10,
				// fuelConsumptionPerDistanceUnit
				1,
				// radius
				.03
			);
		
		public static final Type COMMERCIAL = new Type(
				// firingRange
				0,
				// radarRange
				MILITARY.radarRange,
				// fullHealth
				MILITARY.fullHealth*2,
				// holdCapacity
				MILITARY.holdCapacity*5,
				// tankCapacity
				MILITARY.tankCapacity*4,
				// fuelConsumptionPerDistanceUnit
				MILITARY.fuelConsumptionPerDistanceUnit*3,
				// radius
				MILITARY.radius*2 // TODO adjust
			);
		
	}
	
}











