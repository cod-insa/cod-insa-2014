package game;

import model.Coord;
import model.PlaneModel.State;

import common.Unique;

import display.EntityDisplay;
import display.PlaneDisplay;

public final class Plane extends Entity<model.PlaneModel> {
	
	public final static double
		MAX_SPEED = .01,   // 1E-2,
		MIN_SPEED = .003,  // .3E-2,
		MAX_ROT_SPEED = Math.PI*.03
	;
	
	public final AutoPilot autoPilot = new AutoPilot(this);
	final PlaneDisplay view = new PlaneDisplay(this);
	
	//model.Plane.View model() { return null; }
	
	public Plane (Sim sim, Unique<Coord> pos) {
		super(new model.PlaneModel(makeNextId(), pos, 1, State.IDLE), sim, pos, Altitude.SKY);
		//_pos.set(p);
		//autoPilot.goTo(new Coord(Util.rand.nextDouble(),Util.rand.nextDouble()).view);
		//model.speed = 1E-2;
		//model.speed = MIN_SPEED;
		model.speed = MAX_SPEED;
	}
	
	@Override
	public void updateSpecialized(double period) {
		autoPilot.refresh(period);
	}

	@Override
	public EntityDisplay<Plane> getView() {
		return view;
	}
	
}

