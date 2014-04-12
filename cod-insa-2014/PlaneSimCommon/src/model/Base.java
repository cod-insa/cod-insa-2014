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
	
	public final Immutable<Coord.View> position;
	final Set<ProgressAxis.Oriented> axes;
	private static final double DEFAULT_BASE_RADAR_RANGE = 0.7;
	
	
	public class FullView extends BasicView {

		public double militaryGarrison() { return militaryGarrison; }
		public double fuelInStock() { return fuelInStock; }
		
		public boolean canSee(MaterialEntity.View e) 
		{
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
		public boolean isWithinRadar(Coord.View pos) {
			return position.squareDistanceTo(pos) <= radarRange * radarRange;
		}
		public boolean isInTerritory()
		{
			for (ProgressAxis.Oriented pao : axes)
				if (pao.next.ownerId() != ownerId())
					return false;
			return true;
		}
	}
	
	public class BasicView extends AbstractBase.View {
		public Immutable<Coord.View> getPosition() {
			return Base.this.position;
		}
		
		public SetView<ProgressAxis.Oriented> axes() {
			return Util.shallowView(axes);
		}

		@Override
		public Base copied(Context context) {
			return copy(context);
		}
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
		super(id);
		axes = new HashSet<>();
		position = new Immutable<>(pos);
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
		position = src.getPosition(); // Immutable state can be shared
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

	@Override
	public model.Coord.View position() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
