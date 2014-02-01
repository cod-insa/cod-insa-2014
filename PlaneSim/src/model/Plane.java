package model;

import view.EntityView;
import view.PlaneView;

public final class Plane extends Entity {
	public final AutoPilot autoPilot = new AutoPilot(this);
	final PlaneView view = new PlaneView(this);
	
	public Plane (Coord.View p) {
		pos.set(p);
		//Random r = new Random();
		//autoPilot.aim = new Coord(r.nextDouble(),r.nextDouble());
		//autoPilot.goTo(new Coord(r.nextDouble(),r.nextDouble()).view);
		spe = 1E-2;
		
	}

	@Override
	public void updateSpecialized(double period) {
		autoPilot.refresh(period);
		//autoPilot.refresh();
	}

	@Override
	public EntityView<Plane> getView() {
		//return new PlaneView(this);
		return view;
	}
	
}

