package model;

import view.EntityView;

public abstract class Entity {
	
	//double x, y, altitude;
	
	private static int nb = 0;
	
	public int id = nb++;
	
	final Coord pos = new Coord(0,0);
	//final Coord spe = new Coord(0,0);
	
	double alt = 1;
	double rot = 0;
	double spe = 0;
	protected boolean _exists = true;

	public final Coord.View position = pos.view;
	//public final Coord.View speed = spe.view;
	
	public double rotation() { return rot; }
	public double altitude() { return alt; }
	public double speed() { return spe; }
	public boolean exists() { return _exists; }
	
	/*
	public CloneView<Double> altitude = new CloneView<>(alt);
	
	public double rotation;
	
	//public final EntityView view;
	*/
	
	public final void update(double period) {
		pos.x += Math.cos(rot)*spe;
		pos.y += Math.sin(rot)*spe;
		updateSpecialized(period);
	}
	public abstract void updateSpecialized(double period);
	
	//@SuppressWarnings("rawtypes")
	public abstract EntityView<?> getView();

}

