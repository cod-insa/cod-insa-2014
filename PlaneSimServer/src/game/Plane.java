package game;

import model.Coord;
import display.EntityDisplay;
import display.PlaneDisplay;

public final class Plane extends Entity<model.Plane> {
	public final AutoPilot autoPilot = new AutoPilot(this);
	final PlaneDisplay view = new PlaneDisplay(this);
	
	//model.Plane.View model() { return null; }
	
	public Plane (Sim sim, Coord pos) {
		super(new model.Plane(getNextId(), pos), sim, pos, Altitude.SKY);
		//_pos.set(p);
		//autoPilot.goTo(new Coord(Util.rand.nextDouble(),Util.rand.nextDouble()).view);
		model._spe = 1E-2;
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

