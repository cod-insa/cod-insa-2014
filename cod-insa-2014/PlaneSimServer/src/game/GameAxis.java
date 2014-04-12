package game;

import model.Base;
import model.ProgressAxis;
import display.AxisDisplay;
import display.EntityDisplay;

/**
 * Created by LP on 27/03/2014.
 */
public class GameAxis extends GameEntity {

	public GameAxis(Game sim, Base base1, Base base2) {
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
	@Override
	public EntityDisplay<GameAxis> getDisplay() {
		return new AxisDisplay(this);
	}

	@Override
	ProgressAxis model() { return (ProgressAxis) model; }

	@Override
	public ProgressAxis.View modelView() { return model().view(); }
	
//	@Override
//	public EntityDisplay<?> getDisplay() {
//		return null;
//	}
//	
//	@Override
//	Entity model() { return null; }
	
}
