package game;

import model.Entity;
import display.EntityDisplay;



public abstract class GameEntity {
	
	public static enum Altitude {
		GROUND,
		MIDDLE,
		SKY,
		GUI,
	}
	
	private static int _nb = 0;
	//protected static int getNextId() { return _nb+1; }
	protected static int makeNextId() { return _nb++; }
	
//	public final int id = makeNextId();
	
	
	protected final Entity model;
//	public final Model.View modelView;
	// FIXME: in fact, maybe it'd be better to write it as a getter (even though we loose some invariants)
	public Entity.View modelView() { return model.view(); }
	Entity model() { return model; }
	
	
	
	// Then "model" refers to the entity, and model() to its view.
//	private final Coord _lastPosition; // = new Coord(0,0);
//	public final Coord.View lastPosition; // = _lastPosition.view();
	
	public final Altitude altitude;
	
	protected final Game sim;
	
	/*
	final Coord _pos;// = new Coord(0,0);
	//public final EntityView<?> view;
	
	private double _rot = 0;
	double _spe = 0;
	//double _alt = 1;
	protected boolean _exists = true;
	
	public final Coord.View position;// = _pos.view;
	
	public double rotation() { return _rot; }
	public double speed() { return _spe; }
	//public double altitude() { return _alt; }
	public boolean exists() { return _exists; }
	
	public void rotate (double angle) {
		_rot += angle;
		_rot %= Math.PI*2;
	}
	*/
	
	//public Entity(Coord.View pos) {
	//public Entity(Sim sim, EntityView<?> view, Coord pos) {
	//public Entity(Model model, Sim sim, Unique<Coord> pos, Altitude alt) {
	public GameEntity(Entity model, Game sim, Altitude alt) {
		//_pos.set(pos);
		///System.out.println(T.unit);
		//_pos = pos;
		//position = _pos.view;
		//this.view = view;
		//model = new model.Entity(pos);
		this.model = model;
//		modelView = model.view();
		this.sim = sim;
		
//		if (model instanceof MaterialEntity) {
//			_lastPosition = ((MaterialEntity)model).position().copied();
//			lastPosition = _lastPosition.view();
//		} else {
//			_lastPosition = null;
//			lastPosition = null;
//		}
		
		this.altitude = alt;
		sim.addEntity(this);
	}
	
	
	/*
	public final void update(double period) {
		_pos.x += Math.cos(_rot)*_spe;
		_pos.y += Math.sin(_rot)*_spe;
		updateSpecialized(period);
	}*/

	public final int id() {
		return model.id;
	}
	
	public final void die() {
		sim.removeEntity(this);
		model.exists = false;
	}
	
//	public final double radius() {
//		return radius;
//	}


	
	public final void update(double period) {
//		model.position.x += Math.cos(model.rotation)*model.speed;
//		model.position.y += Math.sin(model.rotation)*model.speed;
		
//		if (model instanceof MaterialEntity)
//			_lastPosition.set(((MaterialEntity)model).position());
		
//		if (model instanceof MovingEntity) {
//			
//			MovingEntity model = (MovingEntity) this.model;
//			
////			model.position.x += Math.cos(model.rotation())*model.speed;
////			model.position.y += Math.sin(model.rotation())*model.speed;
//			
//			Coord speedVec = model.speedVector().take();
//			model.position.x += speedVec.x;
//			model.position.y += speedVec.y;
//			
//			
//			if (World.WORLD_WRAP)
//			{
//				World w = sim.getWorld();
//				if (model.position.x < 0) {
//					model.position.x += w.width; // World.WIDTH;
//					_lastPosition.x += w.width;
//				}
//				else if (model.position.x > w.width) {
//					model.position.x -= w.width;
//					_lastPosition.x -= w.width;
//				}
//				if (model.position.y < 0) {
//					model.position.y += w.height; // World.HEIGHT;
//					_lastPosition.y += w.height;
//				}
//				else if (model.position.y > w.height) {
//					model.position.y -= w.height;
//					_lastPosition.y -= w.height;
//				}
//			}
//			
//		}
		
		updateSpecialized(period);
	}
	
	public abstract void updateSpecialized(double period);

	public void afterUpdate(double period) {
		
	}
	
	public abstract EntityDisplay<?> getDisplay();

	
	public String toStringInfo() {
		return "[Entity "+model.id+"]";
	}
	@Override
	public String toString() {
		return toStringInfo();
	}
	
	
}







