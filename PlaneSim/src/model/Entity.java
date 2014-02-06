package model;

import view.EntityView;


public abstract class Entity {
	
	private static int _nb = 0;
	
	public int id = _nb++;
	
	final Coord _pos;// = new Coord(0,0);
	//public final EntityView<?> view;
	
	private double _rot = 0;
	double _spe = 0;
	double _alt = 1;
	protected boolean _exists = true;
	
	public final Coord.View position;// = _pos.view;
	
	public double rotation() { return _rot; }
	public double speed() { return _spe; }
	public double altitude() { return _alt; }
	public boolean exists() { return _exists; }
	
	//public Entity(Coord.View pos) {
	//public Entity(Sim sim, EntityView<?> view, Coord pos) {
	public Entity(Sim sim, Coord pos) {
		//_pos.set(pos);
		///System.out.println(T.unit);
		_pos = pos;
		position = _pos.view;
		//this.view = view;
		sim.addEntity(this);
	}
	
	public void rotate (double angle) {
		_rot += angle;
		_rot %= Math.PI*2;
	}
	
	public final void update(double period) {
		_pos.x += Math.cos(_rot)*_spe;
		_pos.y += Math.sin(_rot)*_spe;
		updateSpecialized(period);
	}
	public abstract void updateSpecialized(double period);
	
	public abstract EntityView<?> getView();

}

