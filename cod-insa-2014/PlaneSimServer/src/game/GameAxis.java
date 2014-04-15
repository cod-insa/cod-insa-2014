package game;

import model.Base;
import model.ProgressAxis;
import display.AxisDisplay;
import display.EntityDisplay;

/**
 * Created by LP on 27/03/2014.
 */
public class GameAxis extends GameEntity {
	
	double militaryTransfer = 0;
	
	boolean clashing = false;
	
	public final GameBase base1, base2;

	public GameAxis(Game sim, GameBase base1, GameBase base2) {
		super(new ProgressAxis(makeNextId(), base1.model(), base2.model()), sim, Altitude.GROUND);
		base1.axes.add(new Oriented(base2));
		base2.axes.add(new Oriented(base1));
		this.base1 = base1;
		this.base2 = base2;
	}
	
//	public GameAxis(Game sim, Unique<Coord> pos, String name) {
//		//super(new model.BaseModel(getNextId(), pos), sim, pos, Altitude.GROUND);
//		super(new Base(makeNextId(), pos), sim, Altitude.GROUND);
//		radius = RADIUS;
//		cityname = name;
//	}
//

	public class Oriented {

		public final GameBase next;
		public final ProgressAxis.Oriented model;

		public Oriented(GameBase next) {
			this.next = next;
			model = model().arcTo(next.model());
		}

		public GameAxis axis() { return GameAxis.this; }

	}
	
	@Override
	public void updateSpecialized(double period) {
		
		assert militaryTransfer == 0 || model().base1.ownerId() == model().base2.ownerId();
		
		model().base1.militaryGarrison -= militaryTransfer*period;
		model().base2.militaryGarrison += militaryTransfer*period;
		militaryTransfer = 0;
		
//		double ratioSpeed1 = .002;
		double ratioSpeed1 = .002 / model().length; // TODO: function of the fuel
		
		if (model().base1.ownerId() == model().base2.ownerId()) {
			
//			model().ratio1 = 1;
//			model().ratio2 = 0;
			model().ratio1 = .5;
			model().ratio2 = .5;

		} else {
			
			if (!model().base1.owned()) {
				model().ratio1 = 0;
				clashing = false;
			}
			if (!model().base2.owned()) {
				model().ratio2 = 0;
				clashing = false;
			}
			if (model().base1.militaryGarrison <= 0 || model().base2.militaryGarrison <= 0)
				clashing = false;

			//assert !(model().base1.militaryGarrison > 0) || model().base1.owned();
			assert model().base1.owned() || !(model().base1.militaryGarrison > 0);
			assert model().base2.owned() || !(model().base2.militaryGarrison > 0);

			if (!clashing) {
				//			if (model().base1.owned() && model().ratio1 < 1 - model().ratio2 && model().base1.militaryGarrison > 0)
				if (model().ratio1 < 1 - model().ratio2 && model().base1.militaryGarrison > 0)
					model().ratio1 += ratioSpeed1 * period;
				if (model().ratio2 < 1 - model().ratio1 && model().base2.militaryGarrison > 0)
					model().ratio2 += ratioSpeed1 * period;
				if (model().ratio1 > 1 - model().ratio2
						//					&& model().base1.owned()
						//					&& model().base2.owned()
						&& model().base1.militaryGarrison > 0
						&& model().base2.militaryGarrison > 0) {
					clashing = true;
					model().ratio1 = (model().ratio1 + (1 - model().ratio2)) / 2;
					model().ratio2 = 1 - model().ratio1;
				}
				if (model().ratio1 >= 1) {
					model().ratio1 = 1;
					
					// TODO: handle siege when militaryGarrison > 0
					
					
//					if (model().base2.ownerId() == 0)
//						base2.capture(model().base1.ownerId());

					if (model().base2.owned())
						clashing = true;
					
				}
				if (model().ratio2 >= 1) {
					model().ratio2 = 1;
					
					
//					if (model().base1.ownerId() == 0)
//						base1.capture(model().base2.ownerId());

					if (model().base1.owned())
						clashing = true;
					
				}
			} else {

				double dyingSpeed  = .01;
				double balanceImportance  = .3;

				//		if (clashing) {
				double balance1 = model().base1.militaryGarrison / model().base2.militaryGarrison;
				double balance2 = 1/balance1;

				model().base1.militaryGarrison -=
						period * dyingSpeed * (1 + balanceImportance * balance2);
				model().base2.militaryGarrison -=
						period * dyingSpeed * (1 + balanceImportance * balance1);

				model().ratio1 += ratioSpeed1 * (model().base1.militaryGarrison - model().base2.militaryGarrison)
						/ (model().base1.militaryGarrison + model().base2.militaryGarrison);
				model().ratio2 = 1 - model().ratio1;

			}

			if (model().ratio1 > 1)
				model().ratio1 = 1;
			else if (model().ratio1 < 0)
				model().ratio1 = 0;
			
			if (model().ratio2 > 1)
				model().ratio2 = 1;
			else if (model().ratio2 < 0)
				model().ratio2 = 0;
		}
		
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









