package model;

import java.io.Serializable;

import common.Viewable;

public class PlaneModel extends MovingEntityModel implements Serializable, Viewable<PlaneModel.View> {
	
	private static final long serialVersionUID = 1L;
	
	
	public double health = 1;
	

	public class View extends MovingEntityModel.View {
		public double health() { return health; }
	}
	
	@Override public View view() {
		return new View();
	}
	
//	public final View view = new View();
	
	
	public PlaneModel (int id, Coord.Unique pos) {
		//super(id,pos);
		super(id, pos);
	}
	
	public PlaneModel (PlaneModel.View p) {
		super(p);
		health = p.health();
	}
	
	@Override
	public Object copy() {
		return new PlaneModel(view());
	}
	
}
