package game;

import model.Coord;
import model.Plane;
import model.Plane.State;

import common.Unique;

import display.EntityDisplay;
import display.PlaneDisplay;

public final class GamePlane extends MaterialGameEntity {
	
	public static final double
	
		MAX_SPEED = .01,   // 1E-2,
		MIN_SPEED = .008,  // .3E-2,
		
		MAX_ROT_SPEED = Math.PI*.03,
		
		MAX_ACCELERATION = .001,
		MAX_DECELERATION = MAX_ACCELERATION,
		
		VISION_DIST = .7, VISION_DIST_SQUARED = VISION_DIST*VISION_DIST,
		
		//MAX_FIRING_ANGLE = Math.PI*.2,
		MAX_FIRING_ANGLE = Math.PI*.4,
		
		RADIUS = .03,
		
		FUEL_BY_FRAME_MIL = .1,
		FUEL_BY_FRAME_COM = FUEL_BY_FRAME_MIL*1.6
		
	;
	
	public final AutoPilot autoPilot = new AutoPilot(sim, this);
	final PlaneDisplay disp = new PlaneDisplay(this);
	
	//model.Plane.View model() { return null; }
	
	public GamePlane (Game sim, Unique<Coord> pos, int ownerId, boolean isMilitar) {
		super(new Plane(makeNextId(), pos, 1, isMilitar), sim, Altitude.SKY);
		model().state = State.IDLE;
		//_pos.set(p);
		//autoPilot.goTo(new Coord(Util.rand.nextDouble(),Util.rand.nextDouble()).view);
		//model.speed = 1E-2;
		//model.speed = MIN_SPEED;
		//model.speed = MAX_SPEED;
		model().speed = 0;
		model.ownerId(ownerId);
		radius = RADIUS;
	}
	
	@Override
	public void updateSpecialized(double period) {
		super.updateSpecialized(period);
		autoPilot.refresh(period);
		//System.out.println(model().fuelInTank/model().tankCapacity);
		if (model().state != State.AT_AIRPORT)
			model().fuelInTank -= FUEL_BY_FRAME_MIL;
		if (model().fuelInTank < 0) {
			model().fuelInTank = 0;
			die();
		}
	}

	@Override
	public EntityDisplay<GamePlane> getDisplay() {
		return disp;
	}
	
	@Override
	public Plane model() { return (Plane) model; }
	
	@Override
	public Plane.FullView modelView() { return model().view(); }
	
	
	public void fire (double angle) {
		//new Projectile(sim, new Coord.Unique(model.position().copied()), model.ownerId, model.rotation);
//		assert Math.abs(angle) <= MAX_FIRING_ANGLE; // must take into account plane rotation
		new Projectile(sim, new Coord.Unique(model().position()), model().speedVector(), model.ownerId(), angle);
	}
	
	public State getState()
	{
		return model().state;
	}
	
	public boolean isFlying() {
		return model().isFlying();
	}
	
}
















