package game;

import model.Coord;
import model.PlaneModel.State;

import common.Unique;

import display.EntityDisplay;
import display.PlaneDisplay;

public final class Plane extends Entity<model.PlaneModel> {
	
	public static final double
	
		MAX_SPEED = .01,   // 1E-2,
		MIN_SPEED = .003,  // .3E-2,
		
		MAX_ROT_SPEED = Math.PI*.03,
		
		MAX_ACCELERATION = .001,
		MAX_DECELERATION = MAX_ACCELERATION,
		
		VISION_RADIUS = .7,
		
		MAX_FIRING_ANGLE = Math.PI*.2,
		
		RADIUS = .03
	;
	
	public final AutoPilot autoPilot = new AutoPilot(sim, this);
	final PlaneDisplay disp = new PlaneDisplay(this);
	
	//model.Plane.View model() { return null; }
	
	public Plane (Sim sim, Unique<Coord> pos, int ownerId) {
		super(new model.PlaneModel(makeNextId(), pos, 1, State.IDLE), sim, Altitude.SKY);
		//_pos.set(p);
		//autoPilot.goTo(new Coord(Util.rand.nextDouble(),Util.rand.nextDouble()).view);
		//model.speed = 1E-2;
		//model.speed = MIN_SPEED;
		//model.speed = MAX_SPEED;
		model.speed = 0;
		model.ownerId = ownerId;
		radius = RADIUS;
	}
	
	@Override
	public void updateSpecialized(double period) {
		autoPilot.refresh(period);
	}

	@Override
	public EntityDisplay<Plane> getView() {
		return disp;
	}

	public void fire() {
		new Projectile(sim, new Coord.Unique(model.position().copied()), model.ownerId, model.rotation);
	}
	
}
















