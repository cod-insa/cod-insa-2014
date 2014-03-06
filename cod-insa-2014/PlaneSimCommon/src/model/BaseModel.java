package model;

import java.io.Serializable;

import common.Immutable;
import common.Viewable;

public class BaseModel extends EntityModel implements Serializable, Viewable<BaseModel.View> {
	
	private static final long serialVersionUID = 1L;
	
	
	public final Immutable<Coord.View> position;
	
	
	public class View extends EntityModel.View {
		public Immutable<Coord.View> getPosition() {
			return position;
		}
	}
	
	@Override public View view() {
		return new View();
	}
	
	public BaseModel (int id, Coord.Unique pos) {
		//super(new View(),id,pos);
		//super(id, pos);
		super(id);
		position = new Immutable<>(pos);
	}
	
	public BaseModel (BaseModel.View src) {
		super(src.id());
		position = src.getPosition(); // Immutable state can be shared
	}
	
	@Override public Object copy() {
		return new BaseModel(view());
	}

	@Override
	public model.Coord.View position() {
		return position.view();
	}
	
}

