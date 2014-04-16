package game;

import model.Base;
import model.Coord;

import common.Unique;

import display.BaseDisplay;
import display.EntityDisplay;
import model.Plane;
import model.ProgressAxis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class GameBase extends MaterialGameEntity {
	
	public static final double RADIUS = .08; //.04; // .05;
	public final String cityname;
	public Set<GameAxis.Oriented> axes = new HashSet<>();

	public GameBase(Game sim, Unique<Coord> pos, String name) {
		//super(new model.BaseModel(getNextId(), pos), sim, pos, Altitude.GROUND);
		super(new Base(makeNextId(), pos), sim, Altitude.GROUND);
		radius = RADIUS;
		cityname = name;
	}
	
	@Override
	public void updateSpecialized(double period) {
		super.updateSpecialized(period);
		// TODO Auto-generated method stub
	}

	@Override
	public void afterUpdate(double period) {
		if (model().militaryGarrison <= 0) {
			model().militaryGarrison = 0;
//			model().capture(0);
			capture(0);
		}
	}
	
	@SuppressWarnings("deprecated")
	public void capture(int ownerId) {
//		model().ownerId(ownerId);
////		for (ProgressAxis.Oriented arc: model().axes)
//		for (GameAxis.Oriented arc: axes)
//			arc.model.
		
		if (ownerId != model().ownerId())
			while (model().planes.size() > 0)
				sim.getPlane(model().planes.get(0).id).autoPilot.unland();
		
		model().capture(ownerId);

		// java.util.ConcurrentModificationException
//		for (Plane p: model().planes) {
//			sim.getPlane(p.id).autoPilot.unland();
//		}

		// java.util.ConcurrentModificationException
//		for (Iterator<Plane> pit = model().planes.iterator(); pit.hasNext(); ) {
//			sim.getPlane(pit.next().id).autoPilot.unland();
//		}
		
	}

	@Override
	public EntityDisplay<GameBase> getDisplay() {
		return new BaseDisplay(this);
	}
	
	@Override
	Base model() { return (Base) model; }
	
	@Override
	public Base.FullView modelView() { return model().view(); }
	
}
















