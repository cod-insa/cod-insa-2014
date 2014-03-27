package game;

import display.EntityDisplay;
import model.Base;
import model.Entity;
import model.ProgressAxis;

/**
 * Created by LP on 27/03/2014.
 */
public class GameAxis extends GameEntity {

	public GameAxis(Game sim, Base.View base1, Base.View base2) {
		super(new ProgressAxis(makeNextId(), base1, base2), sim, Altitude.GROUND);
	}
	
//	public GameAxis(Game sim, Unique<Coord> pos, String name) {
//		//super(new model.BaseModel(getNextId(), pos), sim, pos, Altitude.GROUND);
//		super(new Base(makeNextId(), pos), sim, Altitude.GROUND);
//		radius = RADIUS;
//		cityname = name;
//	}
//
	@Override
	public void updateSpecialized(double period) {
		// TODO Auto-generated method stub
	}
//
//	@Override
//	public EntityDisplay<GameBase> getView() {
//		return new BaseDisplay(this);
//	}
//
//	@Override
//	ProgressAxis model() { return (ProgressAxis) model; }
//
//	@Override
//	public Base.View modelView() { return model().view(); }
	
	@Override
	public EntityDisplay<?> getView() {
		return null;
	}
	
	@Override
	Entity model() { return null; }
	
}
