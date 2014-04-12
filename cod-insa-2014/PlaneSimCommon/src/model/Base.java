package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Plane.State;

import common.Immutable;
import common.ListView;
import common.SetView;
import common.Unique;
import common.Util;
import common.Viewable;

public class Base extends MaterialEntity implements Serializable, Viewable<Base.FullView> {
	
	private static final long serialVersionUID = 1L;
	
	public final Immutable<Coord.View> position;
	
	final List<Plane> planes;

	final Set<ProgressAxis.Oriented> axes;
	
	public double militaryGarrison;
	public double fuelInStock;

	private static final double DEFAULT_BASE_RADAR_RANGE = 0.7;
	
	public class FullView extends BasicView {
		public double militaryGarrison() { return militaryGarrison; }
		public double fuelInStock() { return fuelInStock; }
		public ListView<Plane.FullView> planes() {
			return Util.view(planes);
		}

		@Override
		public boolean canSee(MaterialEntity.View e) {
			if (e instanceof Plane.FullView
					&& ((Plane.FullView) e).state() == State.AT_AIRPORT
					&& isFriend(e))
				// FIXME should return true if e is a plane and is in the
				// current base
				return false;
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
//		private Base model() { return Base.this; }

		public Base copied(Context context) {
			return copy(context);
		}
	}
	
	public class BasicView extends MaterialEntity.View {
		public Immutable<Coord.View> getPosition() {
			return Base.this.position;
		}
		
		public SetView<ProgressAxis.Oriented> axes() {
			return Util.shallowView(axes);
		}
		
	}

	public final FullView fullView = new FullView();
	@Override
	public FullView view() {
		return fullView;
	}

	public final BasicView basicView = new BasicView();
	public BasicView restrictedView() {
		return basicView;
	}
	

	public Base(int id, Unique<Coord> pos) {
		// super(new View(),id,pos);
		// super(id, pos);
		super(id);
		planes = new ArrayList<>();
		axes = new HashSet<>();
		position = new Immutable<>(pos);
		radarRange = DEFAULT_BASE_RADAR_RANGE;
		fuelInStock = 0;
		militaryGarrison = 0;
	}

	public Base(Base.FullView src, Context context) {
		super(src.id());
		context.putSafe(src.model(), this);
		// planes = Util.copy(src.copied().planes);
		// planes = Util.copy(src.planes());
		planes = new ArrayList<>();
		for (Plane.FullView p : src.planes())
			// planes.add(Util.copy(p, context));
			planes.add(p.copied(context));
		axes = new HashSet<>();
		for (ProgressAxis.Oriented oa: src.axes())
			//axes.add(a.copied(context));
			axes.add(oa.copy(context));
		position = src.getPosition(); // Immutable state can be shared
		radarRange = src.radarRange();
		militaryGarrison = src.militaryGarrison();
		fuelInStock = src.fuelInStock();
	}

	// private BaseModel(BaseModel src)
	// {
	// super(src.id);
	// planes = Util.copy(src.planes);
	// position = src.position;
	// }

	@Override
	public Base copy(Context context) {
		if (context.containsKey(this))
			return context.getSafe(this);
		Base ret = new Base(view(), context);
		return ret;
	}

	@Override
	public model.Coord.View position() {
		return position.view();
	}

}
