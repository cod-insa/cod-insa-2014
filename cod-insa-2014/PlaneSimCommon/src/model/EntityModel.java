package model;

import common.Copyable;
import common.Unique;
import common.Viewable;


public class EntityModel implements Copyable { //, Viewable<EntityModel.View> {
	
	public class View implements Viewable.View {
		public final Coord.View position = _pos.view;
		public int id() { return id; }
		public double rotation() { return _rot; }
		public double speed() { return _spe; }
		public boolean exists() { return _exists; }
		public int ownerId() { return ownerId; }
	}
	
	public final int id;
	
	//public final View view;
	
	public final Coord _pos;// = new Coord(0,0);
	//public final EntityView<?> view;
	
	public double _rot = 0;
	public double _spe = 0;
	//double _alt = 1;
	protected boolean _exists = true;
	
	public int ownerId;
	
	public final Coord.View position;// = _pos.view;
	
	public double rotation() { return _rot; }
	public double speed() { return _spe; }
	//public double altitude() { return _alt; }
	public boolean exists() { return _exists; }
	
	public void rotate (double angle) {
		_rot += angle;
		_rot %= Math.PI*2;
	}

	//public Entity(int id, Coord.Unique pos) {
	public EntityModel(int id, Unique<Coord> pos) {
		this.id = id;
		_pos = pos.take();
		position = _pos.view;
		//view = new View();
		//this.view = view;
	}
	
	public EntityModel (EntityModel src) {
		//this(src.id, Unique.Copy.make(src.position)));
		this(src.id, Unique.Copy.make(src._pos));
	}
	
	@Override public Object copy() {
		return new EntityModel(this);
	}
	
	public View view() {
		return new View();
	}
	
}







