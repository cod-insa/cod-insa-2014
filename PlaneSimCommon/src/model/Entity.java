package model;


public class Entity {
	
	public class View {
		public final Coord.View position = _pos.view;
		public double rotation() { return _rot; }
		public double speed() { return _spe; }
		public boolean exists() { return _exists; }
	}
	
	public final int id;
	
	public final View view;
	
	public final Coord _pos;// = new Coord(0,0);
	//public final EntityView<?> view;
	
	public double _rot = 0;
	public double _spe = 0;
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
	
	public Entity(int id, Coord.Unique pos) {
		this.id = id;
		_pos = pos.take();
		position = _pos.view;
		view = new View();
		//this.view = view;
	}
	
}
