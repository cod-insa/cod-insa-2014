package model;

import view.BaseView;
import view.EntityView;

public class Base extends Entity {
	
	@Override
	public void updateSpecialized(double period) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public EntityView<Base> getView() {
		return new BaseView(this);
	}
	
}

