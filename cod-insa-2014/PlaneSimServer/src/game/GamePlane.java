package game;

import model.Base;
import model.Coord;
import model.Country;
import model.Plane;
import model.Plane.State;

import common.Unique;
import common.Util;

import display.EntityDisplay;
import display.PlaneDisplay;

public final class GamePlane extends MaterialGameEntity {
	
	public static final double
	
		MAX_SPEED = .01,   // 1E-2,
		MIN_SPEED = .008,  // .3E-2,
		
		MAX_ROT_SPEED = Math.PI*.03,
		
		MAX_ACCELERATION = .001,
		MAX_DECELERATION = MAX_ACCELERATION,
		
		VISION_DIST = .7, VISION_DIST_SQUARED = VISION_DIST*VISION_DIST, // FIXME remove
		
		//MAX_FIRING_ANGLE = Math.PI*.2,
		MAX_FIRING_ANGLE = Math.PI*.4
		
//		RADIUS = .03
		
//		FUEL_BY_FRAME_MIL = .1,
//		FUEL_BY_FRAME_COM = FUEL_BY_FRAME_MIL*1.6
		
	;
	
	public final AutoPilot autoPilot = new AutoPilot(sim, this);
	final PlaneDisplay disp = new PlaneDisplay(this);
	
	//model.Plane.View model() { return null; }
	
	public GamePlane (Game sim, Unique<Coord> pos, int ownerId, Plane.Type type) {
		super(new Plane(makeNextId(), pos, type), sim, Altitude.SKY);
		model().state = State.IDLE;
		//_pos.set(p);
		//autoPilot.goTo(new Coord(Util.rand.nextDouble(),Util.rand.nextDouble()).view);
		//model.speed = 1E-2;
		//model.speed = MIN_SPEED;
		//model.speed = MAX_SPEED;
		model().speed = 0;
		model.ownerId(ownerId);
		radius = type.radius;
	}
	
	@Override
	public void updateSpecialized(double period) {
		super.updateSpecialized(period);
		autoPilot.refresh(period);
		//System.out.println(model().fuelInTank/model().tankCapacity);
//		System.out.println(model().type.fuelConsumptionPerDistanceUnit*model().speed*period);
		if (!Settings.DEBUG_GOD_MODE)
		if (model().state != State.AT_AIRPORT)
			model().fuelInTank -= model().type.fuelConsumptionPerDistanceUnit*model().speed*period;
//		if (model().fuelInTank < 0) {
//			model().fuelInTank = 0;
//			die();
////			explode(1, .0007);
//			explode(2, .0007);
//		}
	}

	@Override
	public void afterUpdate(double period) {
		if (model().exists) {
			if (model().health < 0) {
				model().health = 0;
				die();
				explode(2, 3, .003);
			} else if (model().fuelInTank < 0) {
				model().fuelInTank = 0;
				die();
				//			explode(1, .0007);
				explode(1, 2, .0007);
			}
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
	
	public void takeHit (double hitPoints) {
		if (!Settings.DEBUG_GOD_MODE)
			model().health -= hitPoints;
//		System.out.println(model().health);
//		if (model().health < 0) {
//			die();
//			model().health = 0;
//			explode(3, .003);
//		}
	}
	
	public void explode(int min_pieces/* > 0 */, int max_pieces, double blastPower) {
		//sim.addEntity(new Debris(sim, lastPosition, model().position(), model().speedVector(), model.ownerId()));
//		sim.addEntity(new Debris(
//				sim,
// 				model().position(),
//				model().position().shifted(radius, radius).view(),
//				model().speedVector(),
//				model.ownerId()
//		));

//		for (Point2D.Double p: disp.shape.points) {
		int s = disp.shape.points.size();
		for (int i = 0; i < s; i++) {
//			Point2D.Double p1 = disp.shape.points.get(i), p2 = disp.shape.points.get(i%s);
			//final int nbDebs = Math.random() > .5? 1: 2;
			final int nbDebs = Util.rand.nextInt(max_pieces)+min_pieces;

//			Coord.View prevPt = model().position();
//			Coord.View prevPt = new Coord(p1).view();
//			final Coord.View endPt = new Coord(p2).view();
			int nbDebsRemaining = nbDebs;
			Coord.View prevPt = new Coord(disp.shape.points.get(i)).addedTo(model().position()).view();
			final Coord.View endPt = new Coord(disp.shape.points.get((i+1)%s)).addedTo(model().position()).view();
			for (int j = 0; j < nbDebs; j++) {
//				Coord.View bary = Coord.barycenter(prevPt, endPt, ((double)j+1)/(nbDebs)).view();
				Coord.View bary = Coord.barycenter(prevPt, endPt, ((double)1)/(nbDebsRemaining)).view();
				
				//double blastPower = .003; // .005;
				
				Coord blast = prevPt.addedTo(model().position(), -1);
				blast.mult(blastPower/blast.norm());
//				Unique<Coord> inertia = model().speedVector();
				Coord.Unique inertia = new Coord.Unique(model().speedVector());
				inertia.mult(.5); // .5 ??? otherwise it's 2x too fast...
				inertia.add(blast.view());
				sim.addEntity(new Debris(
						sim,
						prevPt,
						bary,
						inertia,
						model.ownerId()
				));
				prevPt = bary;
				nbDebsRemaining--;
			}
			
//			final Coord.View begPt = new Coord(disp.shape.points.get(i)).addedTo(model().position()).view();
//			final Coord.View endPt = new Coord(disp.shape.points.get((i+1)%s)).addedTo(model().position()).view();
//			for (int j = 0; j < nbDebs; j++) {
//				sim.addEntity(new Debris(
//						sim,
//						begPt,
//						Coord.barycenter(begPt, endPt, ((double)j+1)/(nbDebs)).view(),
//						model().speedVector(),
//						model.ownerId()
//				));
//			}
			
			
			
		}
		
	}
	
	public void tradeResources(double mWithdraw, double fWithdraw, double mDeposit, double fDeposit)
	{
		model().militaryInHold += mWithdraw - mDeposit;
		model().fuelInHold += fWithdraw - fDeposit;
		
		// If the plane is in a base
		if (model().curBase instanceof Base)
		{
			((Base)model().curBase).fuelInStock += fDeposit - fWithdraw;
			((Base)model().curBase).militaryGarrison += mDeposit - mWithdraw;
		}
	}
	
	public void fillTank(double quantity)
	{
		model().fuelInTank += quantity;
		if (model().curBase instanceof Base)
		{
			((Base)model().curBase).fuelInStock -= quantity;
		}
	}
	
	public State getState()
	{
		return model().state;
	}
	
	public boolean isFlying() {
		return model().isFlying();
	}
	
}
















