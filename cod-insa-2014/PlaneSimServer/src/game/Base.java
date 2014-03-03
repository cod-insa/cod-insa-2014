package game;

import model.Coord;
import display.BaseDisplay;
import display.EntityDisplay;


public class Base extends Entity<model.BaseModel> {
	
	public Base(Sim sim, Coord.Unique pos) {
		super(new model.BaseModel(getNextId(), pos), sim, pos, Altitude.GROUND);
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

