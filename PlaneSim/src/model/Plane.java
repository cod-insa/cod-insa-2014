package model;

import view.EntityView;
import view.PlaneView;

public final class Plane extends Entity {
	public final AutoPilot autoPilot = new AutoPilot(this);
	final PlaneView view = new PlaneView(this);
	
	public Plane (Coord.View p) {
		pos.set(p);
		//autoPilot.goTo(new Coord(Util.rand.nextDouble(),Util.rand.nextDouble()).view);
		spe = 1E-2;
		
	}

	@Override
	public void updateSpecialized(double period) {
		autoPilot.refresh(period);
	}

	@Override
	public EntityView<Plane> getView() {
		//return new PlaneView(this);
		return view;
	}
	
}

