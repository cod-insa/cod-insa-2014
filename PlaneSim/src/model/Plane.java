package model;

import display.EntityDisplay;
import display.PlaneDisplay;

public final class Plane extends Entity {
	public final AutoPilot autoPilot = new AutoPilot(this);
	final PlaneDisplay view = new PlaneDisplay(this);
	
	public Plane (Sim sim, Coord pos) {
		super(sim, pos);
		//_pos.set(p);
		//autoPilot.goTo(new Coord(Util.rand.nextDouble(),Util.rand.nextDouble()).view);
		_spe = 1E-2;
		
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

