package game;

import model.Coord;
import model.MovingEntity;

import common.Unique;
import common.Util;

import display.EntityDisplay;
import display.ProjectileDisplay;

public class Projectile extends MaterialGameEntity {
	
	public static double
		SPEED = .1,
		INITIAL_ANGLUAR_IMPRECISION = Math.PI*.01, // = Math.PI*.01,
		DISTANCE_RANDOMNESS = .5,
		INEFFECTIVE_RANGE = .4
	;
	
	ProjectileDisplay disp = new ProjectileDisplay(this);
	
	private double distToCover; // = Plane.VISION_RADIUS;
	public double distToCover() { return distToCover; };
	
	//public Projectile(MovingEntity model, Sim sim, Unique<Coord> pos, Altitude alt, double direction) {
		//super(model, sim, pos, alt);
	public Projectile (Game sim, Unique<Coord> pos, Unique<Coord> ine, int ownerId, double direction) {
		super(new MovingEntity(makeNextId(), pos, ine), sim, Altitude.SKY);
//		model.rotation = direction -INITIAL_ANGLUAR_IMPRECISION/2 + Util.rand.nextDouble()*INITIAL_ANGLUAR_IMPRECISION;
		model().rotation(direction -INITIAL_ANGLUAR_IMPRECISION/2 + Util.rand.nextDouble()*INITIAL_ANGLUAR_IMPRECISION);
		model().speed = SPEED;
		model().ownerId(ownerId);
		distToCover = GamePlane.VISION_DIST - DISTANCE_RANDOMNESS/2 + Util.rand.nextDouble()*DISTANCE_RANDOMNESS + INEFFECTIVE_RANGE;
	}
	
	@Override
	public final void updateSpecialized (double period) {
		super.updateSpecialized(period);
		distToCover -= model().position().distanceTo(lastPosition); // FIXME useless calculations...
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
		for (GamePlane p : sim.planes) {
			if (p.isFlying() && model().position().distanceTo(p.model().position()) < radius + p.radius) {
				
			}
		}
	}
	
	@Override
	public EntityDisplay<Projectile> getDisplay() {
		return disp;
	}
	
	@Override
	MovingEntity model() { return (MovingEntity) model; }
	
	@Override
	public MovingEntity.View modelView() { return model().view(); }
	
	
}





















