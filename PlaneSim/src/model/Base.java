package model;

import view.BaseView;
import view.EntityView;

public class Base extends Entity {
	
	public Base(Sim sim, Coord pos) {
		super(sim, pos);
	}
	
	@Override
	public void updateSpecialized(double period) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public EntityView<Base> getView() {
		return new BaseView(this);
	}
	
}

