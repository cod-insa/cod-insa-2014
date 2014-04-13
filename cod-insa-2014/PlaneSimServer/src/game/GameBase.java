package game;

import model.Base;
import model.Coord;

import common.Unique;

import display.BaseDisplay;
import display.EntityDisplay;


public class GameBase extends MaterialGameEntity {
	
	public static final double RADIUS = .04; // .05;
	public final String cityname;
	
	public GameBase(Game sim, Unique<Coord> pos, String name) {
		//super(new model.BaseModel(getNextId(), pos), sim, pos, Altitude.GROUND);
		super(new Base(makeNextId(), pos), sim, Altitude.GROUND);
		radius = RADIUS;
		cityname = name;
	}
	
	@Override
	public void updateSpecialized(double period) {
		super.updateSpecialized(period);
		// TODO Auto-generated method stub
	}

	@Override
	public void afterUpdate(double period) {
		// ...
	}
	

	@Override
	public EntityDisplay<GameBase> getDisplay() {
		return new BaseDisplay(this);
	}
	
	@Override
	Base model() { return (Base) model; }
	
	@Override
	public Base.FullView modelView() { return model().view(); }
	
}

