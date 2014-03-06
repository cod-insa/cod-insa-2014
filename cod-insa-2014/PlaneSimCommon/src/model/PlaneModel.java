package model;

import java.io.Serializable;

import common.Viewable;

public class PlaneModel extends EntityModel implements Serializable, Viewable<PlaneModel.View> {
	
	private static final long serialVersionUID = 1L;
	
	
	public double health = 1;
	

	public class View extends EntityModel.View {
		public double health() { return health; }
	}
	
	@Override public View view() {
		return new View();
	}
	
//	public final View view = new View();
	
	
	public PlaneModel (int id, Coord.Unique pos) {
		super(id,pos);
	}
	
	public PlaneModel (PlaneModel p) {
		super(p);
		health = p.health;
	}

	@Override
	public Object copy() {
		return new PlaneModel(this);
	}
	
}
