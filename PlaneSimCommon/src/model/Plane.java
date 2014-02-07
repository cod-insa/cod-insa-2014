package model;

import java.io.Serializable;

public class Plane extends Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public class View extends Entity.View {
		public double health() { return health; };
	}
	public final View view = new View();
	
	public double health = 1;
	
	public Plane(int id, Coord pos) {
		super(id,pos);
	}
}
