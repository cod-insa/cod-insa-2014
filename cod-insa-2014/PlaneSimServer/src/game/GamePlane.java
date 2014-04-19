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
	
//		MAX_SPEED = .01,   // 1E-2,
		MIN_SPEED = .008,  // .3E-2,
		
//		MAX_ROT_SPEED = Math.PI*.03,
		
		MAX_ACCELERATION = .001,
		MAX_DECELERATION = MAX_ACCELERATION,
		
//		VISION_DIST = .7, VISION_DIST_SQUARED = VISION_DIST*VISION_DIST, // FIXMED remove
		
		//MAX_FIRING_ANGLE = Math.PI*.2,
		MAX_FIRING_ANGLE = Math.PI*.4
		
//		RADIUS = .03
		
//		FUEL_BY_FRAME_MIL = .1,
//		FUEL_BY_FRAME_COM = FUEL_BY_FRAME_MIL*1.6
		
	;
	
	public final AutoPilot autoPilot = new AutoPilot(sim, this);
	final PlaneDisplay disp = new PlaneDisplay(this);
	
	double targetTankFuel = -1;
	
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
	
	void takeFuelFromBase(double qty) {
		if (model().curBase instanceof Base)
//			((Base)model().curBase).fuelInStock(((Base)model().curBase).fuelInStock() - qty);
			((Base)model().curBase).fuelInStock -= qty;
		model().fuelInTank += qty;
	}
	
	@Override
	public void updateSpecialized(double period) {
		super.updateSpecialized(period);
		autoPilot.refresh(period);
		//System.out.println(model().fuelInTank/model().tankCapacity);
//		System.out.println(model().type.fuelConsumptionPerDistanceUnit*model().speed*period);
		
//		if (model().militaryInHold > 10)
//			System.out.println(model().militaryInHold);
		
		if (model().state == State.AT_AIRPORT) {
			model().health += Plane.Type.REGENERATION_SPEED;
			if (targetTankFuel > 0) {
				 if (targetTankFuel > model().fuelInTank) {
//					model().fuelInTank += Plane.Type.FUEL_REFILL_SPEED;
					 takeFuelFromBase(Plane.Type.FUEL_REFILL_SPEED);
				}
				if (targetTankFuel < model().fuelInTank) {
					takeFuelFromBase(targetTankFuel - model().fuelInTank);
					targetTankFuel = -1;
				}
				if (targetTankFuel == model().fuelInTank)
					targetTankFuel = -1;
			}
		} else if (!Settings.DEBUG_GOD_MODE)
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
		if (model().health > model().type.fullHealth) {
			model().health = model().type.fullHealth;
		}
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
		new Projectile(sim, new Coord.Unique(model().position()), model().speedVector(), model.ownerId(), angle, model().type.firingRange);
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
		
		double blastPowerRandomness = .4;

//		for (Point2D.Double p: disp.shape.points) {
		int s = disp.shape.points.size();
		for (int i = 0; i < s; i++) {
//			Point2D.Double p1 = disp.shape.points.get(i), p2 = disp.shape.points.get(i%s);
			//final int nbDebs = Math.random() > .5? 1: 2;
//			final int nbDebs = Util.rand.nextInt(max_pieces)+min_pieces;
			int nbDebs = Util.rand.nextInt(max_pieces)+min_pieces;

//			Coord.View prevPt = model().position();
//			Coord.View prevPt = new Coord(p1).view();
//			final Coord.View endPt = new Coord(p2).view();

			Coord.View prevPt = new Coord(disp.shape.points.get(i)).addedTo(model().position()).view();
			final Coord.View endPt = new Coord(disp.shape.points.get((i+1)%s)).addedTo(model().position()).view();

			
//			System.out.println(prevPt.distanceTo(endPt));
//			nbDebs = (prevPt.distanceTo(endPt) > 0.05)? 1: 2; // FIXME: doesn't work?
			nbDebs = 1;
			
			int nbDebsRemaining = nbDebs;
			
			for (int j = 0; j < nbDebs; j++) {
//				Coord.View bary = Coord.barycenter(prevPt, endPt, ((double)j+1)/(nbDebs)).view();
				Coord.View bary = Coord.barycenter(prevPt, endPt, ((double)1)/(nbDebsRemaining)).view();
				
				//double blastPower = .003; // .005;
				
				Coord blast = prevPt.addedTo(model().position(), -1);
				blast.mult(blastPower/blast.norm() * (1 - blastPowerRandomness/2 + blastPowerRandomness * Math.random()));
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
	
	public void exchangeResources(double milQuantity, double fuelQuantity, boolean deleteResources)
	{
		model().militaryInHold += milQuantity;
		model().fuelInHold += fuelQuantity;
		
		// If the plane is in a base
		if (model().curBase instanceof Base && ! deleteResources)
		{
			((Base)model().curBase).fuelInStock += fuelQuantity;
			((Base)model().curBase).militaryGarrison += milQuantity;
		}
	}
	
	public void fillTank(double quantity)
	{
//		model().fuelInTank += quantity;
//		if (model().curBase instanceof Base)
//		{
//			((Base)model().curBase).fuelInStock -= quantity;
//		}
		if (model().state == State.AT_AIRPORT) {
			targetTankFuel = quantity;
		} else {
			Game.log.debug("Could not fill fuel when not at airport");
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
















