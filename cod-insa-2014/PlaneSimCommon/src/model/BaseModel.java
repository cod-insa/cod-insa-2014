package model;

import java.io.Serializable;

import common.Viewable;

public class BaseModel extends EntityModel implements Serializable, Viewable<BaseModel.View> {
	
	private static final long serialVersionUID = 1L;
	
	
	
	public class View extends EntityModel.View {
		
	}
	
	@Override public View getView() {
		return new View();
	}
	
	public BaseModel (int id, Coord.Unique pos) {
		//super(new View(),id,pos);
		super(id,pos);
	}
	
	public BaseModel (BaseModel src) {
		super(src);
	}
	
	@Override public Object copy() {
		return new BaseModel(this);
	}

	
}
