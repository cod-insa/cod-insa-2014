package game;

import model.BaseModel;
import model.Coord;

import common.Unique;

import display.BaseDisplay;
import display.EntityDisplay;


public class Base extends Entity {
	
	public static final double RADIUS = .04; // .05;
	
	
	public Base(Sim sim, Unique<Coord> pos) {
		//super(new model.BaseModel(getNextId(), pos), sim, pos, Altitude.GROUND);
		super(new BaseModel(makeNextId(), pos), sim, Altitude.GROUND);
		radius = RADIUS;
	}
	
	@Override
	public void updateSpecialized(double period) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public EntityDisplay<Base> getView() {
		return new BaseDisplay(this);
	}
	
	@Override
	BaseModel model() { return (BaseModel) model; }
	
	@Override
	public BaseModel.View modelView() { return model().view(); }
	
}

