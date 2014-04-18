package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.Plane.State;
import common.Immutable;
import common.ListView;
import common.SetView;
import common.Unique;
import common.Util;
import common.Viewable;

public class Base extends AbstractBase implements Serializable, Viewable<Base.FullView>
{
	
	private static final long serialVersionUID = 1L;
	

	public final Set<ProgressAxis.Oriented> axes;
	private static final double DEFAULT_BASE_RADAR_RANGE = 0.7;
	
	public double militaryGarrison;
	public double fuelInStock;
	
	public boolean fullViewInSync = true;
	
	
	/**
	 * This class represents a Base seen, everything is visible
	 */
	public class FullView extends BasicView {

		public FullView() {}
		private FullView(boolean ignoreSync) { this.ignoreSync = ignoreSync; }
		
		public boolean inSync() { return fullViewInSync; }
		
		/**
		 * // TODO
		 * @return
		 */
		public FullView asLastViewed() { return new FullView(true); }
		

		/**
		 * @return All the planes at the airport of the AbstractBase
		 */
		public ListView<Plane.FullView> planes() {
			return Util.view(planes);	
		}
		
		/**
		 * @return The number of resources unit in the garrison of the Base
		 */
		public double militaryGarrison() { checkSynx(fullViewInSync); return militaryGarrison; }
		
		/**
		 * @return The number of resources unit in the stock of the Base
		 */
		public double fuelInStock() { checkSynx(fullViewInSync); return fuelInStock; }
		
		/**
		 * Returns whereas the base sees or not an entity
		 * @param e The entity
		 */
		public boolean canSee(MaterialEntity.View e) 
		{
			checkSynx(fullViewInSync);
			if (e instanceof Plane.FullView
					&& ((Plane.FullView) e).state() == State.AT_AIRPORT
					&& isFriend(e))
				// FIXME should return true if e is a plane and is in the
				// current base
				return planes().containsTypeSafe((Plane.FullView)e);
			if (e instanceof MaterialEntity.View)
				return isWithinRadar(((MaterialEntity.View)e).position);
			else return false;
		}

		
		@Override
		/**
		 * Returns true if the base see the location given in parameter
		 */
		public boolean isWithinRadar(Coord.View pos) {
			checkSynx(fullViewInSync);
			return position.squareDistanceTo(pos) <= radarRange * radarRange;
		}
		/**
		 * Returns true if the base is at the middle of the territory (i.e. if every base around this one have the same ownerId)
		 */
		public boolean isInTerritory()
		{
			checkSynx(fullViewInSync);
			for (ProgressAxis.Oriented pao : axes)
				if (pao.next.ownerId() != ownerId())
					return false;
			return true;
		}
		
		/**
		 * Determine if there is enough military garrison to expand automatically
		 */
		public boolean canExpand() {
			checkSynx(fullViewInSync);
			return militaryGarrison > GameSettings.MINIMUM_BASE_GARRISON;
		}
		/**
		 * Determine if there is enough military garrison to capture automatically
		 */
		public boolean canCapture() {
			checkSynx(fullViewInSync);
			return militaryGarrison > GameSettings.MINIMUM_CAPTURE_GARRISON;
		}
		
		@Override
		/**
		 * A function in order to display a Base
		 */
//		public String toString() { checkSynx(fullViewInSync); return "id: " +id() +" pos: "+ position().toString() +" fuel: "+fuelInStock()+" mil: "+militaryGarrison()+ " owner: "+ownerId() ; }
		public String toString() {
			if (fullViewInSync)
				return "FullBaseView id: "+ id() + " pos: "+ position().toString() + " [out of sync]";
			return "FullBaseView id: " + id() +" pos: "+ position().toString() +" fuel: "+fuelInStock()+" mil: "+militaryGarrison()+ " owner: "+ownerId() ;
		}
	}
	
	/**
	 * This class represents a Base which is not visible by the AI
	 *
	 */
	public class BasicView extends AbstractBase.View {

		/**
		 * Get all the axes from which the base is linked to others
		 */
		public SetView<ProgressAxis.Oriented> axes() {
			return Util.shallowView(axes);
		}

		@Override
		Base copied(Context context) {
			return copy(context);
		}

		
	}


	/**
	 * Must only be called from GameBase.capture
	 */
	@Deprecated
	public void capture(int newId) {
		if (ownerId() != newId) {
			militaryGarrison = 0; // COULD_DO: garrison flees to neighboring bases?
			// (In fact I think it doesn't actually happen to have militaryGarrison > 0 here)
//			System.out.println(ownerId()+" "+newId);
			for (ProgressAxis.Oriented arc: axes) {
//				if (arc.next.ownerId() == newId)
				arc.ratio(0);
				
				
//				if (arc.next.ownerId() == ownerId()) {
//					arc.opposite().ratio(0);
//				}
				
				
			}
		}
		ownerId(newId);
	}
	public void resetAxes() {
		for (ProgressAxis.Oriented arc: axes)
			arc.ratio(0);
	}

	public final FullView fullView; // = new FullView();
	@Override
	public FullView view() {
		return fullView;
	}

	public final BasicView basicView; // = new BasicView();
	public BasicView restrictedView() {
		return basicView;
	}
	
	public Base(int id, Unique<Coord> pos) {
		super(id,pos);
		axes = new HashSet<>();
		radarRange = DEFAULT_BASE_RADAR_RANGE;
		fuelInStock = 0;
		militaryGarrison = 0;

		fullView = new FullView();
		basicView = new BasicView();
	}

	public Base(Base.FullView src, Context context) {
		super(src, context);
		context.putSafe(src.model(), this);
		// planes = Util.copy(src.copied().planes);
		// planes = Util.copy(src.planes());
		for (Plane.FullView p : src.planes())
			// planes.add(Util.copy(p, context));
			planes.add(p.copied(context));
		axes = new HashSet<>();
		radarRange = DEFAULT_BASE_RADAR_RANGE;
		for (ProgressAxis.Oriented oa: src.axes())
			//axes.add(a.copied(context));
			axes.add(oa.copy(context));
//		radarRange = src.radarRange();
		militaryGarrison = src.militaryGarrison();
		fuelInStock = src.fuelInStock();

		fullView = new FullView();
		basicView = new BasicView();
	}
	
	@Override
	public Base copy(Context context) {
		if (context.containsKey(this))
			return context.getSafe(this);
		Base ret = new Base(view(), context);
		return ret;
	}

	
}
