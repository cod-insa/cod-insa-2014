package game;

import model.Coord;
import model.Plane;
import model.Plane.State;

import common.Util;
import common.Util.Predicate;


public final class AutoPilot {
	
//	static final double MAX_ROT_SPEED = Math.PI*.03;

	public enum Mode {
		ATTACK_ON_SIGHT,
		DEFEND_LOCATION, // not implemented yey
		IGNORE,
		FLEE, // not implemented yey
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
	
	Game sim;
	
	GamePlane plane;
	Plane.Type type;
	
	private final Coord _aim = new Coord(0,0);
	public final Coord.View currentAim = _aim.view();

//	private double targetSpeed = GamePlane.MAX_SPEED;
	private double targetSpeed;
	
	private MaterialGameEntity entityAim = null;
	//boolean specifically_attacking = false;
	//boolean attacking = false;
	//Action current_action = Action.NONE;
	State state = State.IDLE;
	
	public Mode default_mode = Mode.ATTACK_ON_SIGHT; // mode used when goingTo action is complete
	// ^ TODO when other actions are complete too!
	public Mode mode = default_mode; // Mode.ATTACK_ON_SIGHT;
	//public Mode onCompleteMode = default_mode;
	
//	AttackMode attacking_mode = AttackMode.NONE;
	
	public AutoPilot(Game s, GamePlane p) {
		sim = s;
		plane = p;
		type = plane.model().type;
		targetSpeed = type.maxSpeed;
		circling_radius = type.maxSpeed / Math.cos(Math.PI/2 - type.maxRotSpeed);
	}
	
	public void goTo(Coord.View aim, Mode m) {
		unland();
		entityAim = null;
//		_aim.set(aim);
		setAim(aim);
		plane.model().state = State.GOING_TO;
//		attacking_mode = AttackMode.NONE;
//		mode = Mode.IGNORE;
//		attacking = false;
		state = State.GOING_TO;
		mode = m;
		
//		System.out.println(plane.model.position.angleWith(_aim.view()));
	}
	public void goTo(MaterialGameEntity e, Mode m) {
		if (e == plane)
			throw new IllegalArgumentException("Cannot follow oneself!");
		unland();
		entityAim = e;
//		_aim.set(entityAim.model().position());
		setAim(entityAim.model().position());
//		plane.model.state = State.FOLLOWING;
//		attacking_mode = AttackMode.NONE;
//		mode = Mode.IGNORE;
//		attacking = false;
		state = State.FOLLOWING;
		mode = m;
	}

	public void attackSpecific(MaterialGameEntity e) {
		if (e == null)
			System.out.println("LOL");;
		unland();
		goTo(e, Mode.IGNORE);
		//specifically_attacking = true;
//		attacking_mode = AttackMode.SPECIFIC;
//		attacking = true;
		state = State.ATTACKING;
//		mode = Mode.IGNORE;
	}
	void attack(MaterialGameEntity e) {
		unland();
		goTo(e, Mode.ATTACK_ON_SIGHT);
//		attacking_mode = AttackMode.ANY;
//		attacking = true;
		state = State.ATTACKING;
//		mode = Mode.ATTACK_ON_SIGHT;
	}
	
	
	public void landAt(Landable b) {
//		System.out.println("land");
//		if (b.model().view().planes().containsTypeSafe(plane.modelView())) {
//			assert state == State.AT_AIRPORT;
//			System.out.println("already at airp");
//			return;
//		}
		if (state == State.AT_AIRPORT && b.model().id == plane.model().curBase.id) {
			assert Util.findFirst(b.model().view().planes(), new Predicate<Plane.FullView>(){
				public Boolean convert(Plane.FullView src) {
					return src.id() == plane.id();
				}}).hasSome();
//			System.out.println("already at airp");
			return;
		}
		unland();
		//System.out.println("land on "+b.id());
		goTo(b.asMaterialGameEntity(), Mode.IGNORE);
		state = State.LANDING;
	}
	void land(Landable b) {
		
		///////////////////////////
		// FIXME TESTING
		//b.model().ownerId(plane.model().ownerId());
		if (b instanceof GameBase)
		{
			((GameBase)b).capture(plane.model().ownerId());
			((GameBase)b).model().militaryGarrison += plane.model().type.holdCapacity/2;
		}
		///////////////////////////
		if (b instanceof GameCountry)
			plane.model().fuelInTank = plane.model().type.tankCapacity;
		
		if (b.model().planes.size()+1 > b.landingCapacity()) {
//			b.model().planes.get(0).unAssign();
			sim.getPlane(b.model().planes.get(0).id).autoPilot.unland(); // TODO: kick out the one with the most fuel first?
		}
		
		state = State.AT_AIRPORT;
		plane.model().assignTo(b.model());//addPlane();
		//plane.model.speed = 0;
//		for (ProgressAxis.Oriented pa: b.model().axes) {
//		}
		
		
	}
//	void unland(Mode newMode) {
	void unland() {
		if (state == State.AT_AIRPORT) {
			state = State.IDLE;
//			mode = newMode;
			mode = Mode.ATTACK_ON_SIGHT;
			//((GameBase)entityAim).model().planes.remove(plane);
			plane.model().unAssign();
		}
	}
	public void takeOff() {
		unland();
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
	
	@SuppressWarnings("unused")
	private void setAim(Coord.View pos) {
//		if (entityAim instanceof GameCountry)
//			System.out.println("!!");
		if ((entityAim != null && entityAim.model.ownerId() == plane.model().ownerId() && entityAim instanceof GameCountry)
		 || (0 <= pos.x() && pos.x() <= sim.getWorld().width
		     &&  0 <= pos.y() && pos.y() <= sim.getWorld().height))
		then:
			_aim.set(pos);
	}
	
	private double aimAngle;
	final double circling_radius; //= type.maxSpeed / Math.cos(Math.PI/2 - type.maxRotSpeed);

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
		
		targetSpeed = type.maxSpeed;
		
		if (state != State.AT_AIRPORT) {
			
			if (entityAim != null) {
				//if (entityAim.model.exists && ( plane.canSee(entityAim) || (state != State.FOLLOWING || plane.isFriend(entityAim)) ))
				if (entityAim.model.exists && /*plane.knowsPositionOf(entityAim)*/ plane.model().view().knowsPositionOf(entityAim.model().view()))
//					_aim.set(entityAim.model().position());
					setAim(entityAim.model().position());
				else resetEntityAim();
			}
			
//			if (state == State.GOING_TO)
//				System.out.println(plane.model().position.distanceTo(currentAim)+" "+circling_radius);
			
			if (state == State.GOING_TO
					&& plane.model().position.distanceTo(currentAim) <= circling_radius // FIXME use a less strict comp?
			) {
				state = State.IDLE;
				mode = default_mode;
			}
			
			if (mode == Mode.ATTACK_ON_SIGHT) {
	//			if (attacking_mode != AttackMode.SPECIFIC) {
				MaterialGameEntity e = seekNearestEnemy();
				if (e != null) {
					attack(e);
				}
			}
			
			double angleToAim = plane.model().position.angleWith(_aim.view());
			
			//aimAngle = Math.atan2(_aim.y-plane.model.position().y(), _aim.x-plane.model.position().x()) + Math.PI*2 - plane.model.rotation;
			aimAngle = angleToAim + Math.PI*2 - plane.model().rotation();
			
			double oldA = aimAngle;
			
			aimAngle %= Math.PI*2;
			if (aimAngle > Math.PI)
				aimAngle -= Math.PI*2;
			
			
			if (state == State.ATTACKING) {
				if (Math.abs(aimAngle) <= GamePlane.MAX_FIRING_ANGLE)
					plane.fire(angleToAim);
				
//				if (Math.abs(aimAngle) <= Plane.MAX_FIRING_ANGLE) {
//					System.out.println(plane.id()+" firing at "+aimAngle+" ("+oldA+")");
//					System.out.println(plane.model.rotation+"  "+plane.model.position.angleWith(_aim.view()));
//					System.out.println(plane.model.position+"  "+_aim.view()+" "+plane.model.id+" "+entityAim.id());
//				}
				
				
//				targetSpeed = Math.sin(aimAngle);

				//if (Math.abs(aimAngle) < Math.PI/2)
				if (Math.abs(aimAngle) <= GamePlane.MAX_FIRING_ANGLE)
//					 targetSpeed = Plane.MIN_SPEED;
					 targetSpeed = GamePlane.MIN_SPEED + (type.maxSpeed - GamePlane.MIN_SPEED) * Math.sin(aimAngle);
				else targetSpeed = type.maxSpeed;
				

//				targetSpeed = Plane.MIN_SPEED + (Plane.MAX_SPEED - Plane.MIN_SPEED) * Math.sin(aimAngle*2);
				
				
				
//				System.out.println(targetSpeed);
				
			}
			
			if (state == State.LANDING) {
	//			double circling_radius = Math.cos(Math.PI/2 - Plane.MAX_ROT_SPEED) * Plane.MAX_SPEED/2;
	//			double circling_radius = Plane.MAX_SPEED / (2 * Math.cos(Math.PI/2 - Plane.MAX_ROT_SPEED));
				//double circling_radius = GamePlane.MAX_SPEED / Math.cos(Math.PI/2 - GamePlane.MAX_ROT_SPEED);
				double d = pos().distanceTo(aimPos());
				
	//			System.out.println(circling_radius+" "+d);
				
				//circling_radius *= 1.1;
				//circling_radius *= 2;
				//circling_radius *= 1.5;
				double larger_radius = circling_radius * 1.2;
				
				if (state == State.LANDING && d <= larger_radius) {
					//targetSpeed = d/circling_radius * Plane.MAX_SPEED;
	//				targetSpeed = Math.sqrt(d/circling_radius*circling_radius * Plane.MAX_SPEED);
	//				targetSpeed = (circling_radius - d) * (circling_radius - d) * Plane.MAX_SPEED;
	//				targetSpeed = (circling_radius - d) * (circling_radius - d) * Plane.MAX_SPEED;
					//targetSpeed = Math.pow(d/circling_radius,2) * Plane.MAX_SPEED;
					targetSpeed = d/larger_radius * type.maxSpeed;
					
					if (d <= entityAim.radius*.7)
					{
						land(((Landable)entityAim));
					}
					
	//				targetSpeed = Plane.MAX_SPEED*Math.cos(aimAngle);
					
					//targetSpeed -= Plane.MAX_DECELERATION;
					
	//				if (targetSpeed < 0) targetSpeed = 0;
					
	//				System.out.println(targetSpeed);
					
					//plane.model.speed = targetSpeed;
				}
				//else targetSpeed = Plane.MAX_SPEED;
			}
			//else targetSpeed = Plane.MAX_SPEED;
			
			adjustOrientation(period);
			
			adjustSpeed(period);
			
		} else {
			
			plane.model().speed = 0;
			
		}
		
		plane.model().state = state;
		
	}
	
	
	
	private MaterialGameEntity seekNearestEnemy() {
		Double minSDist = null;
		MaterialGameEntity ret = null;
//		for (Entity<?> e: sim.entities)
//		//	if (e.model.ownerId != plane.model.ownerId && e.altitude == plane.altitude)
//			if (plane.isEnemy(e) && e.altitude == plane.altitude) {
//				double sd = plane.model().position().squareDistanceTo(e.model().position());
//				if (sd <= Plane.VISION_DIST_SQUARED && (minSDist == null || sd < minSDist))
//				{ minSDist = sd; ret = e; }
//			}
		for (GamePlane e: sim.planes)
		//	if (e.model.ownerId != plane.model.ownerId && e.altitude == plane.altitude)
		//	if (plane.isEnemy(e) && e.altitude == plane.altitude && plane.canSee(e))
			if (plane.model().view().canAttack(e.model().view()))
			{
				double sd = plane.modelView().position().squareDistanceTo(e.modelView().position());
//				if (sd <= GamePlane.VISION_DIST_SQUARED && (minSDist == null || sd < minSDist))
				if (sd <= plane.model().type.radarRange_squared && (minSDist == null || sd < minSDist))
				{ minSDist = sd; ret = e; }
			}
		return ret;
	}

	private void adjustOrientation(double period)
	{
//		double aimAngle = Math.atan2(_aim.y-plane.model.position().y(), _aim.x-plane.model.position().x()) + Math.PI*2 - plane.model.rotation;
//		
//		aimAngle %= Math.PI*2;
//		
//		if (aimAngle > Math.PI)
//			aimAngle -= Math.PI*2;
		
		double mrs = type.maxRotSpeed*period;
		//double mrs = MAX_ROT_SPEED*period*(.7+rand.nextDouble()*.3);
		
		double delta = aimAngle > mrs? mrs: aimAngle;
		delta = delta < -mrs? -mrs: delta;
		
		plane.model().rotate(delta);
	}
	
	private void adjustSpeed(double period)
	{
		
		double min_speed = state == State.LANDING? 0: GamePlane.MIN_SPEED;
		if (targetSpeed < min_speed)
			targetSpeed = min_speed;
		else if (targetSpeed > type.maxSpeed)
			targetSpeed = type.maxSpeed;
		
		
		if (plane.model().speed < targetSpeed) {
			plane.model().speed += GamePlane.MAX_ACCELERATION*period;
			if (plane.model().speed > targetSpeed)
				plane.model().speed = targetSpeed;
		} else if (plane.model().speed > targetSpeed) {
			plane.model().speed -= GamePlane.MAX_DECELERATION*period;
			if (plane.model().speed < targetSpeed)
				plane.model().speed = targetSpeed;
		}
	}
	
	
	public Coord.View pos() {
		return plane.model().position();
	}
	public Coord.View aimPos() {
		return entityAim.model().position();
	}
	
	

}
















