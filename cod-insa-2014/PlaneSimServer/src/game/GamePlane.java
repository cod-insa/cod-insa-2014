package game;

import model.Coord;
import model.Plane;
import model.Plane.State;

import common.Unique;

import display.EntityDisplay;
import display.PlaneDisplay;

public final class GamePlane extends GameEntity {
	
	public static final double
	
		MAX_SPEED = .01,   // 1E-2,
		MIN_SPEED = .008,  // .3E-2,
		
		MAX_ROT_SPEED = Math.PI*.03,
		
		MAX_ACCELERATION = .001,
		MAX_DECELERATION = MAX_ACCELERATION,
		
		VISION_DIST = .7, VISION_DIST_SQUARED = VISION_DIST*VISION_DIST,
		
		//MAX_FIRING_ANGLE = Math.PI*.2,
		MAX_FIRING_ANGLE = Math.PI*.4,
		
		RADIUS = .03
	;
	
	public final AutoPilot autoPilot = new AutoPilot(sim, this);
	final PlaneDisplay disp = new PlaneDisplay(this);
	
	//model.Plane.View model() { return null; }
	
	public GamePlane (Game sim, Unique<Coord> pos, int ownerId) {
		super(new Plane(makeNextId(), pos, 1, State.IDLE), sim, Altitude.SKY);
		//_pos.set(p);
		//autoPilot.goTo(new Coord(Util.rand.nextDouble(),Util.rand.nextDouble()).view);
		//model.speed = 1E-2;
		//model.speed = MIN_SPEED;
		//model.speed = MAX_SPEED;
		model().speed = 0;
		model.ownerId = ownerId;
		radius = RADIUS;
	}
	
	@Override
	public void updateSpecialized(double period) {
		autoPilot.refresh(period);
	}

	@Override
	public EntityDisplay<GamePlane> getView() {
		return disp;
	}
	
	@Override
	Plane model() { return (Plane) model; }
	
	@Override
	public Plane.View modelView() { return model().view(); }
	
	
	public void fire (double angle) {
		//new Projectile(sim, new Coord.Unique(model.position().copied()), model.ownerId, model.rotation);
		assert Math.abs(angle) <= MAX_FIRING_ANGLE;
		new Projectile(sim, new Coord.Unique(model.position().copied()), model.ownerId, angle);
	}
	
	public State getState()
	{
		return model().state;
	}
	
	public boolean isFlying() {
		return model().isFlying();
	}
	
	public boolean isFriend(GameEntity e) {
		return model.ownerId == e.model.ownerId;
	}
	
	public boolean canSee(GameEntity e) {
		if (e instanceof GamePlane && ((GamePlane)e).model().state == State.AT_AIRPORT)
			return false;
		return model().position.squareDistanceTo(e.model.position()) <= VISION_DIST_SQUARED;
	}
	
	public boolean canAttack(GamePlane e) {
		return isEnemy(e) && e.altitude == altitude && canSee(e);
	}
	
	public boolean knowsPositionOf(GameEntity e) {
		if (e instanceof GameBase)
			return true;
		return isFriend(e) || canSee(e);
	}
	
}
















