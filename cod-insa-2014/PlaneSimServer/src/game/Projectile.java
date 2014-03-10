package game;

import model.Coord;
import model.MovingEntityModel;

import common.Unique;
import common.Util;

import display.EntityDisplay;
import display.ProjectileDisplay;

public class Projectile extends Entity<MovingEntityModel> {
	
	public static double
		SPEED = .1,
		INITIAL_ANGLUAR_IMPRECISION = Math.PI*.01,
		DISTANCE_RANDOMNESS = .5,
		INEFFECTIVE_RANGE = .4
	;
	
	ProjectileDisplay disp = new ProjectileDisplay(this);
	
	private double distToCover; // = Plane.VISION_RADIUS;
	public double distToCover() { return distToCover; };
	
	//public Projectile(MovingEntityModel model, Sim sim, Unique<Coord> pos, Altitude alt, double direction) {
		//super(model, sim, pos, alt);
	public Projectile (Sim sim, Unique<Coord> pos, int ownerId, double direction) {
		super(new MovingEntityModel(makeNextId(), pos), sim, Altitude.SKY);
		model.rotation = direction -INITIAL_ANGLUAR_IMPRECISION/2 + Util.rand.nextDouble()*INITIAL_ANGLUAR_IMPRECISION;
		model.speed = SPEED;
		model.ownerId = ownerId;
		distToCover = Plane.VISION_RADIUS - DISTANCE_RANDOMNESS/2 + Util.rand.nextDouble()*DISTANCE_RANDOMNESS + INEFFECTIVE_RANGE;
	}
	
	@Override
	public final void updateSpecialized (double period) {
		distToCover -= model.position().distanceTo(lastPosition); // FIXME useless calculations...
//		distToCover -= SPEED; // FIXME WorldWrap hack
		
		if (distToCover < 0) {
			die();
			return;
		}
		if (distToCover < INEFFECTIVE_RANGE) {
			//distToCover / INEFFECTIVE_RANGE
			return;
		}
//		for (Entity<?> e: sim.entities.get()) {
//			if (e.altitude == altitude && model.position().distanceTo(e.model.position()) < radius + e.radius) {
//				// TODO
//			}
//		}
		for (Plane p : sim.planes) {
			if (p.isFlying() && model.position().distanceTo(p.model.position()) < radius + p.radius) {
				
			}
		}
	}
	
	@Override
	public EntityDisplay<Projectile> getView() {
		return disp;
	}
	
}





















