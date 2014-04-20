package game;

import model.Base;
import model.GameSettings;
import model.ProgressAxis;
import display.AxisDisplay;
import display.EntityDisplay;

/**
 * Created by LP on 27/03/2014.
 */
public class GameAxis extends GameEntity {
	
//	double militaryTransfer = 0;
	
	boolean clashing = false;

	public final GameBase base1, base2;
	public final Oriented toBase1, toBase2;

	public GameAxis(Game sim, GameBase base1, GameBase base2) {
		super(new ProgressAxis(makeNextId(), base1.model(), base2.model()), sim, Altitude.GROUND);
		base1.axes.add(toBase2 = new Oriented(base1, base2));
		base2.axes.add(toBase1 = new Oriented(base2, base1));
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
		
		private double militarySent = 0;

		public final GameBase current, next;
		public final ProgressAxis.Oriented model;

		public Oriented(GameBase current, GameBase next) {
			this.current = current;
			this.next = next;
			model = model().arcTo(next.model());
		}

		public GameAxis axis() { return GameAxis.this; }
		
		public void send(double mil) {
			assert base1.model().ownerId() == base2.model().ownerId();
			
//			assert mil > 0;
			assert mil < 0;
			
			militarySent += mil;
			
		}
		
		public double flushMilitarySent() {
			double ret = militarySent;
			militarySent = 0;
			return ret;
		}

	}










	abstract class Closure {

		abstract double ratioA();
		abstract void ratioA(double value);
		abstract GameBase baseA();
		Base baseAm() { return baseA().model(); }

		abstract double ratioB();
		abstract void ratioB(double value);
		abstract GameBase baseB();
		Base baseBm() { return baseB().model(); }

		void main(){

			if ( ! baseAm().owned()) {
				ratioA(0);
			}
			assert baseAm().owned() || !(baseAm().militaryGarrison > 0);

//					if (ratioA() != ratioB()) {
////						if (ratioA() >= ratioB()) throw new Error();
//						assert ratioA() < ratioB();
//						
////						if (model().ratio1 < 1 - model().ratio2 && model().base1.militaryGarrison > 0 && model().base1.canExpand())
////							model().ratio1 += ratioSpeed1 * period;
//						
//					}

		}

		void progress(double ratioSpeed1) {
			if (baseAm().view().canExpand()) {
//				ratioA(ratioA() + ratioSpeed1 * period);
				ratioA(ratioA() + ratioSpeed1);
				baseAm().fuelInStock -= GameSettings.FUEL_CONSUMPTION_RATE * ratioSpeed1;
			}
		}

		void capture() {
			if (ratioA() >= 1 && baseAm().view().canCapture() && baseBm().militaryGarrison <= 0) {
				baseB().capture(baseAm().ownerId());
				baseBm().militaryGarrison += GameSettings.MINIMUM_CAPTURE_GARRISON/2;
				baseAm().militaryGarrison -= GameSettings.MINIMUM_CAPTURE_GARRISON/2;
			}
		}

//				double clash() {
////					baseAm().militaryGarrison -= period * GameSettings.DYING_RATE * baseBm().militaryGarrison;
//					return baseAm().militaryGarrison - period * GameSettings.DYING_RATE * baseBm().militaryGarrison;
//				}


		void checkValid(){
			if (ratioA() > 1)
				ratioA(1);
			else if (ratioA() < 0)
				ratioA(0);
			if (baseAm().militaryGarrison < 0)
				baseAm().militaryGarrison = 0;
			if (baseAm().fuelInStock < 0)
				baseAm().fuelInStock = 0;
		}
		

		public double ratioSpeed(double period) {
			double fuel = baseAm().fuelInStock;
//			baseAm().fuelInStock -= GameSettings.FUEL_CONSUMPTION_RATE;
//			if (baseAm().fuelInStock < 0)
//				baseAm().fuelInStock = 0;
			return period * GameSettings.PROGRESSION_RATE / model().length * fuel * GameSettings.FUEL_PROGRESSION_MULTIPLIER;
		}

	}
	
	
	public void updateTransfers(double period) {
		double militaryTransfer = toBase2.flushMilitarySent() - toBase1.flushMilitarySent();

		model().base1.militaryGarrison -= militaryTransfer * period * GameSettings.MILITARY_TRANSFER_RATE;
		model().base2.militaryGarrison += militaryTransfer * period * GameSettings.MILITARY_TRANSFER_RATE;
	}
	
//	private double ratioSpeed(double period) {
//		return period * .002 / model().length; // TODONE: function of the fuel?;
//	}
	
	@Override
	public void beforeUpdate(final double period) {
//		double militaryTransfer = toBase2.flushMilitarySent() - toBase1.flushMilitarySent();
//
//		model().base1.militaryGarrison -= militaryTransfer * period * GameSettings.MILITARY_TRANSFER_RATE;
//		model().base2.militaryGarrison += militaryTransfer * period * GameSettings.MILITARY_TRANSFER_RATE;
		
		
//		double militaryTransfer = toBase2.flushMilitarySent() - toBase1.flushMilitarySent();
//		
////		assert militaryTransfer == 0 || model().base1.ownerId() == model().base2.ownerId();
//		
//		model().base1.militaryGarrison -= militaryTransfer * period * GameSettings.MILITARY_TRANSFER_RATE;
//		model().base2.militaryGarrison += militaryTransfer * period * GameSettings.MILITARY_TRANSFER_RATE;
////		militaryTransfer = 0;
		
		
		
		
//		double ratioSpeed1 = .002;
//		final double ratioSpeed1 = .002 / model().length;
		final double ratioSpeed1 = A.ratioSpeed(period), ratioSpeed2 = B.ratioSpeed(period);


		if (model().base1.ownerId() == model().base2.ownerId()) {
			
//			model().ratio1 = 1;
//			model().ratio2 = 0;
			model().ratio1 = .5;
			model().ratio2 = .5;

		} else {
			
			A.main();
			B.main();

			A.progress(ratioSpeed1);
			B.progress(ratioSpeed2);

			if (model().ratio1 > 1 - model().ratio2
					&& model().base1.militaryGarrison > 0
					&& model().base2.militaryGarrison > 0) {
				model().ratio1 = (model().ratio1 + (1 - model().ratio2)) / 2;
				model().ratio2 = 1 - model().ratio1;
			}

//			A.capture();
//			B.capture();
			
			if (model().ratio2 == 1 - model().ratio1
					&& model().base1.militaryGarrison > 0
					&& model().base2.militaryGarrison > 0
			) {
				
//				//double dyingSpeed  = .01;
//				double balanceImportance  = .3;
//
//				//		if (clashing) {
//				double balance1 = model().base1.militaryGarrison / model().base2.militaryGarrison;
//				double balance2 = 1/balance1;
//
//				model().base1.militaryGarrison -=
//						period * GameSettings.DYING_RATE * (1 + balanceImportance * balance2);
//				model().base2.militaryGarrison -=
//						period * GameSettings.DYING_RATE * (1 + balanceImportance * balance1);
//
//				model().ratio1 += ratioSpeed1 * (model().base1.militaryGarrison - model().base2.militaryGarrison)
//						/ (model().base1.militaryGarrison + model().base2.militaryGarrison);
//				model().ratio2 = 1 - model().ratio1;


//				model().base1.militaryGarrison -= period * GameSettings.DYING_RATE * model().base2.militaryGarrison;


//				double milA = A.clash();
//				double milB = B.clash();
//
//				model().base1.militaryGarrison = milA;
//				model().base2.militaryGarrison = milB;


				base1.nbClashes++;
				base2.nbClashes++;
				

//				model().ratio1 += ratioSpeed1 * (model().base1.militaryGarrison - model().base2.militaryGarrison)
//						/ (model().base1.militaryGarrison + model().base2.militaryGarrison);
//				model().ratio2 = 1 - model().ratio1;

			}

			A.checkValid();
			B.checkValid();


//			A.capture();
//			B.capture();

		}


	}

	@Override
	public void updateSpecialized(final double period) {

		// TODO: handle siege?
		
		
		if (model().base1.ownerId() != model().base2.ownerId()) {

			if (model().ratio2 == 1 - model().ratio1
				&& model().base1.militaryGarrison > 0
				&& model().base2.militaryGarrison > 0
			) {

				double forces1 = model().base1.militaryGarrison/((double)base1.nbClashes);
				double forces2 = model().base2.militaryGarrison/((double)base2.nbClashes);

//				model().ratio1 += ratioSpeed(period) * (forces1 - forces2) / (forces1 + forces2);
//				model().ratio2 = 1 - model().ratio1;
				
				if (forces1 > forces2) {
					if (A.baseAm().fuelInStock > 0) {
						model().ratio1 += A.ratioSpeed(period) * (forces1 - forces2) / (forces1 + forces2);
						model().ratio2 = 1 - model().ratio1;
						A.baseAm().fuelInStock -= GameSettings.FUEL_CONSUMPTION_RATE * (forces1 - forces2) / (forces1 + forces2);
					}
				} else if (forces1 < forces2) {
					if (B.baseAm().fuelInStock > 0) {
						model().ratio1 += B.ratioSpeed(period) * (forces1 - forces2) / (forces1 + forces2);
						model().ratio2 = 1 - model().ratio1;
						B.baseAm().fuelInStock -= GameSettings.FUEL_CONSUMPTION_RATE * (forces2 - forces1) / (forces1 + forces2);
					}
				}
				
				
//				double losses = Math.min(forces1,forces2) * GameSettings.DYING_RATE_PERCENTAGE;
//				
//				model().base1.militaryGarrison -= period * losses;
//				model().base2.militaryGarrison -= period * losses;

				model().base1.militaryGarrison -= period * forces2 * GameSettings.DYING_RATE_PERCENTAGE;
				model().base2.militaryGarrison -= period * forces1 * GameSettings.DYING_RATE_PERCENTAGE;




			}
			
			A.checkValid();
			B.checkValid();

			A.capture();
			B.capture();
			
		}

//				//double dyingSpeed  = .01;
//				double balanceImportance  = .3;
//
//				//		if (clashing) {
//				double balance1 = model().base1.militaryGarrison / model().base2.militaryGarrison;
//				double balance2 = 1/balance1;
//
//				model().base1.militaryGarrison -=
//						period * GameSettings.DYING_RATE * (1 + balanceImportance * balance2);
//				model().base2.militaryGarrison -=
//						period * GameSettings.DYING_RATE * (1 + balanceImportance * balance1);
//
//				model().ratio1 += ratioSpeed1 * (model().base1.militaryGarrison - model().base2.militaryGarrison)
//						/ (model().base1.militaryGarrison + model().base2.militaryGarrison);
//				model().ratio2 = 1 - model().ratio1;


//				model().base1.militaryGarrison -= period * GameSettings.DYING_RATE * model().base2.militaryGarrison;


//				double milA = A.clash();
//				double milB = B.clash();
//
//				model().base1.militaryGarrison = milA;
//				model().base2.militaryGarrison = milB;


//			model().base1.nbClashes;
		
		
		
		
		
		
		
		
		
		
		
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


	Closure A = new Closure(){
		double ratioA() { return model().ratio1; }
		void ratioA(double value) { model().ratio1 = value; }
		GameBase baseA() { return base1; }
		double ratioB() { return 1 - model().ratio2; }
		void ratioB(double value) { model().ratio2 = 1 - value; }
		GameBase baseB() { return base2; }
	};
	Closure B = new Closure(){
		double ratioA() { return model().ratio2; }
		void ratioA(double value) { model().ratio2 = value; }
		GameBase baseA() { return base2; }
		double ratioB() { return 1 - model().ratio1; }
		void ratioB(double value) { model().ratio1 = 1 - value; }
		GameBase baseB() { return base1; }
	};
	
}









