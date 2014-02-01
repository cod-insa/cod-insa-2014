package model;


public final class AutoPilot {
	
	static final double MAX_ROT_SPEED = Math.PI*.03;
	
	Plane plane;
	
	//double aimX, aimY;
	private final Coord _aim = new Coord(0,0);
	public final Coord.View currentAim = _aim.view;
	
	private Entity entityAim = null;
	
	public AutoPilot(Plane p) {
		plane = p;
	}

	public void goTo(Coord.View aim) {
		entityAim = null;
		_aim.set(aim);
		/*
		double aimAngle = Math.atan2(_aim.y-plane.position.y(), _aim.x-plane.position.x()) - plane.rot;
		aimAngle %= Math.PI;
		System.out.println(aimAngle);
		*/
	}
	public void goTo(Entity e) {
		if (e == plane)
			throw new IllegalArgumentException("Cannot follow oneself!");
		entityAim = e;
	}
	
	//public void refresh(double period) {
	public void refresh(double period) {
		
		// The following branch executes if there is an entityAim and if it exists;
		// if it doesn't exist it sets it to null and doesn't execute the branch
		if (entityAim != null && (entityAim.exists() || (entityAim = null) != null)) {
			_aim.set(entityAim.position);
			//System.out.println(entityAim.id);
		}

		//double aimAngle = Math.atan2(_aim.y-plane.position.y(), _aim.x-plane.position.x()) + Math.PI*2 - plane.rot;
		double aimAngle = Math.atan2(_aim.y-plane.position.y(), _aim.x-plane.position.x()) + Math.PI*2 - plane.rot;
		
		//aimAngle = aimAngle == Math.PI || ? aimAngle: aimAngle % Math.PI;
		/*if (Math.abs(aimAngle) != Math.PI)
			aimAngle %= Math.PI*2;
		*/
		aimAngle %= Math.PI*2;
		
		if (aimAngle > Math.PI)
			aimAngle -= Math.PI*2;
		
		//System.out.println(aimAngle);
		
		double mrs = MAX_ROT_SPEED*period;
		//double mrs = MAX_ROT_SPEED*period*(.7+rand.nextDouble()*.3);
		
		double delta = aimAngle > mrs? mrs: aimAngle;
		delta = delta < -mrs? -mrs: delta;
		
		//plane.rot += aimAngle
		plane.rot += delta;
		//plane.rot %= Math.PI*2;
		
		
	}	
	

}








