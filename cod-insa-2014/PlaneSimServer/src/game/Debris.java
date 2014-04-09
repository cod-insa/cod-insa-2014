package game;

import model.Coord;
import model.MovingEntity;

import common.Unique;
import common.Util;

import display.DebrisDisplay;
import display.EntityDisplay;

public class Debris extends MaterialGameEntity {
	
	public static double
		SPEED = .02,
//		ANG_SPEED = Math.PI*.1,
		TIME_TO_LIVE = 50,
		TIME_TO_LIVE_RANDOMNESS = 10,
		FADING_RANGE = TIME_TO_LIVE*.75,
		HIT_POINTS = .1
	;

	final DebrisDisplay disp = new DebrisDisplay(this);
	
	private double timeToLive;
	public double timeToLive() { return timeToLive; };
	
	public static final double ANG_SPE = Math.PI*.06; //Math.PI*.03; //Math.PI*.2;
	public double ang_speed = Math.random()*ANG_SPE*2 - ANG_SPE;
//	public double internal_rotation;
	
	public Debris (Game sim, Coord.View pos1, Coord.View pos2, Unique<Coord> inertia, int ownerId) {
		super(new MovingEntity(makeNextId(), new Coord.Unique(Coord.barycenter(pos1, pos2, .5).view()), inertia), sim, Altitude.SKY);
		//model().inertia.shift();
//		model().rotation(Math.random()*Math.PI*2);
//		internal_rotation = pos1.angleWith(pos2);
		model().rotation(pos1.angleWith(pos2));
		radius = pos1.distanceTo(pos2)/2;
		
//		model().speed = SPEED;
		
		model().speed = 0;
//		model().inertia.set(Coord.origin);
//		model().inertia.mult(.5); // .5 ??? otherwise it's 2x too fast...
		
		
		model().ownerId(ownerId);
		timeToLive = TIME_TO_LIVE - TIME_TO_LIVE_RANDOMNESS/2 + Util.rand.nextDouble()*TIME_TO_LIVE_RANDOMNESS + FADING_RANGE;
		
//		System.out.println("deb "+pos1+" "+pos2);
	}
	
	@Override
	public final void updateSpecialized (double period) {
		super.updateSpecialized(period);
//		timeToLive -= model().position().distanceTo(lastPosition); // FIXME useless calculations...
//		timeToLive -= SPEED; // FIXME WorldWrap hack
		timeToLive -= period;
		
//		internal_rotation += ang_speed;
		model().rotation(model().rotation() + ang_speed * period);
//		ang_speed *= Math.pow(.95, period);
		ang_speed *= Math.pow(.975, period);
		
//		model().inertia.mult(Math.pow(.98, period)); // TODONE period dep
//		if (model().inertia.norm() > SPEED/2)
//			model().inertia.mult(.965);
		
		if (timeToLive < 0) {
			die();
			return;
		}
//		if (timeToLive < FADING_RANGE) {
//			return;
//		}
		for (GamePlane p : sim.planes) {
			if (p.isFlying() && model().position().distanceTo(p.model().position()) < .4*radius + p.radius) {
				p.takeHit(HIT_POINTS); // TODO add some randomness/distance dependency
				//die();
				
//				Coord s = p.model().speedVector().take();
//				s.mult(.5);
//				model().inertia.add(s.view());

//				Coord dir = model().speedVector().take();
				
				Coord dir = model().position.addedTo(p.model().position(), -1);
//				dir.mult(-1);
				dir.mult(1/dir.norm());
				
//				Coord s = p.model().speedVector().take().projectedOn(dir.view());
				//s.mult(.5);
//				model().inertia.add(s.view());
				
				Coord s = p.model().speedVector().take();
				s.add(model().inertia.view(), -1);
//				s.mult(1/s.norm());
				s.mult(.4);
				model().inertia.add(s.projectedOn(dir.view()).view());

//				System.out.println();
//				System.out.println(dir.norm());
//				System.out.println(p.model().speedVector().take().norm());
//				System.out.println(s.projectedOn(dir.view()).norm());
				
//				Coord dir = model().position.addedTo(p.model().position(), -1);
//				dir.mult(p.model().speedVector().take().norm()/dir.norm() * .5);
//				model().inertia.add(dir.view());
				
			}
		}
	}
	
	@Override
	public EntityDisplay<Debris> getDisplay() {
		return disp;
	}
	
	@Override
	MovingEntity model() { return (MovingEntity) model; }
	
	@Override
	public MovingEntity.View modelView() { return model().view(); }
	
	
}





















