package game;

import model.Coord;
import model.MovingEntityModel;
import display.EntityDisplay;



public abstract class Entity<Model extends model.EntityModel> {
	
	public static enum Altitude {
		GROUND,
		MIDDLE,
		SKY,
		GUI
	}
	
	private static int _nb = 0;
	//protected static int getNextId() { return _nb+1; }
	protected static int makeNextId() { return _nb++; }
	
//	public final int id = makeNextId();
	
	
	final Model model;
	public final Model.View modelView;
	// FIXME: in fact, maybe it'd be better to write it as a getter (even though we loose some invariants)
	public Model.View model() { return model.view(); }
	// Then "model" refers to the entity, and model() to its view.
	
	public final Altitude altitude;
	
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
	public Entity(Model model, Sim sim, Coord.Unique pos, Altitude alt) {
		//_pos.set(pos);
		///System.out.println(T.unit);
		//_pos = pos;
		//position = _pos.view;
		//this.view = view;
		//model = new model.Entity(pos);
		this.model = model;
		modelView = model.view();
		
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
	
	public final void update(double period) {
//		model.position.x += Math.cos(model.rotation)*model.speed;
//		model.position.y += Math.sin(model.rotation)*model.speed;
		
		if (model instanceof MovingEntityModel) {
			MovingEntityModel model = (MovingEntityModel) this.model;
			model.position.x += Math.cos(model.rotation)*model.speed;
			model.position.y += Math.sin(model.rotation)*model.speed;
		}
		
		updateSpecialized(period);
	}
	
	public abstract void updateSpecialized(double period);
	
	public abstract EntityDisplay<?> getView();
	
}







