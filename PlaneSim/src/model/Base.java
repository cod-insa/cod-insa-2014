package model;

import display.BaseDisplay;
import display.EntityDisplay;


public class Base extends Entity {
	
	public Base(Sim sim, Coord pos) {
		super(sim, pos);
	}
	
	@Override
	public void updateSpecialized(double period) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public EntityDisplay<Base> getView() {
		return new BaseDisplay(this);
	}
	
}

