package game;

import model.Coord;
import model.PlaneModel.State;


public final class AutoPilot {
	
//	static final double MAX_ROT_SPEED = Math.PI*.03;

	public enum Mode {
		ATTACK_ON_SIGHT,
		IGNORE,
		FLEE,
	}
	
//	enum AttackMode {
//		NONE,
//		ANY,
//		SPECIFIC
//	}
	
//	enum Action {
//		ATTACK,
//		LAND,
//		NONE,
//	}
	
	Sim sim;
	
	Plane plane;
	
	private final Coord _aim = new Coord(0,0);
	public final Coord.View currentAim = _aim.view();
	
	private double targetSpeed = Plane.MAX_SPEED;
	
	private Entity<?> entityAim = null;
	//boolean specifically_attacking = false;
	//boolean attacking = false;
	//Action current_action = Action.NONE;
	State state = State.IDLE;
	
	public Mode mode = Mode.ATTACK_ON_SIGHT;
	
//	AttackMode attacking_mode = AttackMode.NONE;
	
	public AutoPilot(Sim s, Plane p) {
		sim = s;
		plane = p;
	}
	
	public void goTo(Coord.View aim, Mode m) {
		unland();
		entityAim = null;
		_aim.set(aim);
		plane.model.state = State.GOING_TO;
//		attacking_mode = AttackMode.NONE;
//		mode = Mode.IGNORE;
//		attacking = false;
		state = State.GOING_TO;
		mode = m;
	}
	public void goTo(Entity<?> e, Mode m) {
		if (e == plane)
			throw new IllegalArgumentException("Cannot follow oneself!");
		unland();
		entityAim = e;
//		plane.model.state = State.FOLLOWING;
//		attacking_mode = AttackMode.NONE;
//		mode = Mode.IGNORE;
//		attacking = false;
		state = State.FOLLOWING;
		mode = m;
	}

	public void attackSpecific(Entity<?> e) {
		unland();
		goTo(e, Mode.IGNORE);
		//specifically_attacking = true;
//		attacking_mode = AttackMode.SPECIFIC;
//		attacking = true;
		state = State.ATTACKING;
//		mode = Mode.IGNORE;
	}
	void attack(Entity<?> e) {
		unland();
		goTo(e, Mode.ATTACK_ON_SIGHT);
//		attacking_mode = AttackMode.ANY;
//		attacking = true;
		state = State.ATTACKING;
//		mode = Mode.ATTACK_ON_SIGHT;
	}
	
	
	public void landAt(Base b) {
		unland();
		System.out.println("land on "+b.id());
		goTo(b, Mode.IGNORE);
		state = State.LANDING;
	}
	
	
	void resetEntityAim() {
		unland();
		entityAim = null;
		if (state == State.ATTACKING && mode == Mode.IGNORE)
			mode = Mode.ATTACK_ON_SIGHT;
//		attacking = false;
//		plane.model.state = State.IDLE;
		state = State.IDLE;
	}
	
	private double aimAngle;
	
	public void refresh(double period) {
		
//		// The following branch executes if there is an entityAim and if it exists;
//		// if it doesn't exist it sets it to null and doesn't execute the branch
//		if (entityAim != null && (entityAim.model.exists || (entityAim = null) != null)) {
//			_aim.set(entityAim.model.position());
//		}
//		else plane.model.state = State.IDLE;
//		
//		if (entityAim == null)
//			plane.model.state = State.IDLE;
		
		if (state != State.AT_AIRPORT) {
			
			if (entityAim != null) {
				//if (entityAim.model.exists && ( plane.canSee(entityAim) || (state != State.FOLLOWING || plane.isFriend(entityAim)) ))
				if (entityAim.model.exists && plane.knowsPositionOf(entityAim))
					 _aim.set(entityAim.model.position());
				else resetEntityAim();
			}
			
			
			if (mode == Mode.ATTACK_ON_SIGHT) {
	//			if (attacking_mode != AttackMode.SPECIFIC) {
				Entity<?> e = seekNearestEnemy();
				if (e != null) {
					attack(e);
				}
			}
	
			//aimAngle = Math.atan2(_aim.y-plane.model.position().y(), _aim.x-plane.model.position().x()) + Math.PI*2 - plane.model.rotation;
			aimAngle = plane.model.position.angleWith(_aim.view()) + Math.PI*2 - plane.model.rotation;
			aimAngle %= Math.PI*2;
			
			if (state == State.ATTACKING) {
				if (Math.abs(aimAngle) <= Plane.MAX_FIRING_ANGLE)
					plane.fire();
			}
			
			if (state == State.LANDING) {
	//			double circling_radius = Math.cos(Math.PI/2 - Plane.MAX_ROT_SPEED) * Plane.MAX_SPEED/2;
	//			double circling_radius = Plane.MAX_SPEED / (2 * Math.cos(Math.PI/2 - Plane.MAX_ROT_SPEED));
				double circling_radius = Plane.MAX_SPEED / Math.cos(Math.PI/2 - Plane.MAX_ROT_SPEED);
				double d = pos().distanceTo(aimPos());
				
	//			System.out.println(circling_radius+" "+d);
				
				//circling_radius *= 1.1;
				//circling_radius *= 2;
				//circling_radius *= 1.5;
				circling_radius *= 1.2;
				
				if (state == State.LANDING && d <= circling_radius) {
					//targetSpeed = d/circling_radius * Plane.MAX_SPEED;
	//				targetSpeed = Math.sqrt(d/circling_radius*circling_radius * Plane.MAX_SPEED);
	//				targetSpeed = (circling_radius - d) * (circling_radius - d) * Plane.MAX_SPEED;
	//				targetSpeed = (circling_radius - d) * (circling_radius - d) * Plane.MAX_SPEED;
					//targetSpeed = Math.pow(d/circling_radius,2) * Plane.MAX_SPEED;
					targetSpeed = d/circling_radius * Plane.MAX_SPEED;
					
					if (d <= entityAim.radius*.7)
					{
						land(((Base)entityAim));
					}
					
	//				targetSpeed = Plane.MAX_SPEED*Math.cos(aimAngle);
					
					//targetSpeed -= Plane.MAX_DECELERATION;
					
	//				if (targetSpeed < 0) targetSpeed = 0;
					
	//				System.out.println(targetSpeed);
					
					//plane.model.speed = targetSpeed;
				}
				else targetSpeed = Plane.MAX_SPEED;
			}
			else targetSpeed = Plane.MAX_SPEED;
			
			adjustOrientation(period);
			
			adjustSpeed(period);
			
		} else {
			
			plane.model.speed = 0;
			
		}
		
		plane.model.state = state;
		
	}
	
	
	
	private Entity<?> seekNearestEnemy() {
		Double minSDist = null;
		Entity<?> ret = null;
		for (Entity<?> e: sim.entities)
		//	if (e.model.ownerId != plane.model.ownerId && e.altitude == plane.altitude)
			if (plane.isEnemy(e) && e.altitude == plane.altitude) {
				double sd = plane.model().position().squareDistanceTo(e.model().position());
				if (sd <= Plane.VISION_DIST_SQUARED && (minSDist == null || sd < minSDist))
				{ minSDist = sd; ret = e; }
			}
		return ret;
	}

	private void adjustOrientation(double period)
	{
//		double aimAngle = Math.atan2(_aim.y-plane.model.position().y(), _aim.x-plane.model.position().x()) + Math.PI*2 - plane.model.rotation;
//		
//		aimAngle %= Math.PI*2;
		
		if (aimAngle > Math.PI)
			aimAngle -= Math.PI*2;
		
		double mrs = Plane.MAX_ROT_SPEED*period;
		//double mrs = MAX_ROT_SPEED*period*(.7+rand.nextDouble()*.3);
		
		double delta = aimAngle > mrs? mrs: aimAngle;
		delta = delta < -mrs? -mrs: delta;
		
		plane.model.rotate(delta);
	}
	
	private void adjustSpeed(double period)
	{
		if (plane.model.speed < targetSpeed) {
			plane.model.speed += Plane.MAX_ACCELERATION*period;
			if (plane.model.speed > targetSpeed)
				plane.model.speed = targetSpeed;
		} else if (plane.model.speed > targetSpeed) {
			plane.model.speed -= Plane.MAX_DECELERATION*period;
			if (plane.model.speed < targetSpeed)
				plane.model.speed = targetSpeed;
		}
	}
	
	
	public Coord.View pos() {
		return plane.model.position();
	}
	public Coord.View aimPos() {
		return entityAim.model.position();
	}
	
	void land(Base b) {
		state = State.AT_AIRPORT;
		b.model.planes.add(plane.model); //addPlane();
		//plane.model.speed = 0;
	}
	void unland() {
		if (state == State.AT_AIRPORT) {
			state = State.IDLE;
			((Base)entityAim).model.planes.remove(plane);
		}
	}
	
	

}
















