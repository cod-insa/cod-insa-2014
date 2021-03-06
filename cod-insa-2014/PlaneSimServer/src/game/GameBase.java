package game;

import java.util.HashSet;
import java.util.Set;

import model.Base;
import model.Coord;
import model.GameSettings;

import common.Unique;

import display.BaseDisplay;
import display.EntityDisplay;


public class GameBase extends MaterialGameEntity implements Landable {
	
	public static final double RADIUS = .08; //.04; // .05;
	public final String cityName;
	public Set<GameAxis.Oriented> axes = new HashSet<>();
	
	int nbClashes = 0;

	public GameBase(Game sim, Unique<Coord> pos, String name) {
		//super(new model.BaseModel(getNextId(), pos), sim, pos, Altitude.GROUND);
		super(new Base(makeNextId(), pos), sim, Altitude.GROUND);
		radius = RADIUS;
		cityName = name;
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
			
			/////
			capture(0);
			/////
			
			for (GameAxis.Oriented arc: axes) {
				arc.current.model().resetAxes();
				arc.axis().clashing = false;
			}
		}
		nbClashes = 0;
	}
	
	@SuppressWarnings("deprecated")
	public void capture(int ownerId) {
//		model().ownerId(ownerId);
////		for (ProgressAxis.Oriented arc: model().axes)
//		for (GameAxis.Oriented arc: axes)
//			arc.model.
		
//		if (ownerId != model().ownerId())
//			while (model().planes.size() > 0)
//				if (model().planes.get(0).ownerId() != ownerId)
//					sim.getPlane(model().planes.get(0).id).autoPilot.unland();
		if (ownerId != model().ownerId()) {
//			Iterator<Plane> it = model().planes.iterator();
//			while (it.hasNext()) {
//				if (it.next().ownerId() != ownerId)
//					sim.getPlane(model().planes.get(0).id).autoPilot.unland();
//			}
			for (int i = 0; i < model().planes.size(); i++) {
				if (model().planes.get(0).ownerId() != ownerId) {
					sim.getPlane(model().planes.get(0).id).autoPilot.unland();
					i--;
				}
			}
		}
		
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

	final BaseDisplay disp = new BaseDisplay(this);
	@Override
	public EntityDisplay<GameBase> getDisplay() {
//		return new BaseDisplay(this);
		return disp;
	}

	/**
	 * Including fronts with Gaia (ownerId == 0)
	 */
	public boolean hasFronts() {
		for (GameAxis.Oriented arc: axes) {
//			if (arc.model().base1.ownerId() != arc.model().base2.ownerId())
			if (arc.current.model().ownerId() != arc.next.model().ownerId())
				return true;
		}
		return false;
	}
	
	@Override
	public Base model() { return (Base) model; }
	
	@Override
	public MaterialGameEntity asMaterialGameEntity() {
		return this;
	}
	@Override
	public Base.FullView modelView() { return model().view(); }

	@Override
	public String toString() {
		return super.toStringInfo()+" Base "+cityName;
	}

	@Override
	public int landingCapacity() { return GameSettings.MAX_PLANES_PER_COUNTRY; }
	
}
















