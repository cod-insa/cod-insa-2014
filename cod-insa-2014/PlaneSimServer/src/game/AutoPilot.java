package game;

import model.Coord;
import model.PlaneModel.State;


public final class AutoPilot {
	
//	static final double MAX_ROT_SPEED = Math.PI*.03;

	public enum Mode {
		ATTACK_ON_SIGHT,
		IGNORE,
		FLEE
	}
	
//	enum AttackMode {
//		NONE,
//		ANY,
//		SPECIFIC
//	}
	
	Sim sim;
	
	Plane plane;
	
	private final Coord _aim = new Coord(0,0);
	public final Coord.View currentAim = _aim.view();
	
	private double targetSpeed = Plane.MAX_SPEED;
	
	private Entity<?> entityAim = null;
	//boolean specifically_attacking = false;
	boolean attacking = false;

	public Mode mode = Mode.ATTACK_ON_SIGHT;
	
//	AttackMode attacking_mode = AttackMode.NONE;
	
	public AutoPilot(Sim s, Plane p) {
		sim = s;
		plane = p;
	}
	
	public void goTo(Coord.View aim, Mode m) {
		entityAim = null;
		_aim.set(aim);
		plane.model.state = State.GOING_TO;
//		attacking_mode = AttackMode.NONE;
//		mode = Mode.IGNORE;
		mode = m;
	}
	public void goTo(Entity<?> e, Mode m) {
		if (e == plane)
			throw new IllegalArgumentException("Cannot follow oneself!");
		entityAim = e;
		plane.model.state = State.FOLLOWING;
//		attacking_mode = AttackMode.NONE;
//		mode = Mode.IGNORE;
		mode = m;
	}

	public void attackSpecific(Entity<?> e) {
		goTo(e, Mode.IGNORE);
		//specifically_attacking = true;
//		attacking_mode = AttackMode.SPECIFIC;
		attacking = true;
//		mode = Mode.IGNORE;
	}
	void attack(Entity<?> e) {
		goTo(e, Mode.ATTACK_ON_SIGHT);
//		attacking_mode = AttackMode.ANY;
		attacking = true;
//		mode = Mode.ATTACK_ON_SIGHT;
	}
	
	void resetEntityAim() {
		entityAim = null;
		if (attacking && mode == Mode.IGNORE)
			mode = Mode.ATTACK_ON_SIGHT;
		attacking = false;
		plane.model.state = State.IDLE;
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
		
		if (entityAim != null) {
			if (entityAim.model.exists)
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
		
		aimAngle = Math.atan2(_aim.y-plane.model.position().y(), _aim.x-plane.model.position().x()) + Math.PI*2 - plane.model.rotation;
		aimAngle %= Math.PI*2;
		
		if (attacking) {
			if (Math.abs(aimAngle) <= Plane.MAX_FIRING_ANGLE)
				plane.fire();
		}
		
		adjustOrientation(period);
		
		adjustSpeed(period);
		
	}
	
	
	
	private Entity<?> seekNearestEnemy() {
		Double minSDist = null;
		Entity<?> ret = null;
		for (Entity<?> e: sim.entities.get())
		//	if (e.model.ownerId != plane.model.ownerId && e.altitude == plane.altitude)
			if (plane.isEnemy(e) && e.altitude == plane.altitude) {
				double sd = plane.model().position().squareDistanceTo(e.model().position());
				if (minSDist == null || sd < minSDist)
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
	

}
















