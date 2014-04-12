package game;

import display.AxisDisplay;
import display.EntityDisplay;
import model.Base;
import model.Entity;
import model.ProgressAxis;

/**
 * Created by LP on 27/03/2014.
 */
public class GameAxis extends GameEntity {
	
	double militaryTransfer = 0;

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
		model().base1.militaryGarrison -= militaryTransfer*period;
		model().base2.militaryGarrison += militaryTransfer*period;
		militaryTransfer = 0;
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
