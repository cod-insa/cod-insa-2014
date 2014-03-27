package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.Plane.State;
import common.Immutable;
import common.ListView;
import common.Unique;
import common.Util;
import common.Viewable;

public class Base extends MaterialEntity implements Serializable, Viewable<Base.View> {

	private static final long serialVersionUID = 1L;

	public final Immutable<Coord.View> position;

	final List<Plane> planes;
	
	public double militarResourcesStock;
	public double fuelResourcesStock;

	private static final double DEFAULT_BASE_RADAR_RANGE = 0.7;
	
	public class View extends MaterialEntity.View {
		public double militarResourcesStock() { return militarResourcesStock; }
		public double fuelResourcesStock() { return fuelResourcesStock; }
		public Immutable<Coord.View> getPosition() {
			return Base.this.position;
		}

		public ListView<Plane.FullView> getPlanes() {
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

		public Base copied(Context context) {
			return copy(context);
		}
		
		private Base getModel() { return Base.this; }
	}

	@Override
	public View view() {
		return new View();
	}

	public Base(int id, Unique<Coord> pos) {
		// super(new View(),id,pos);
		// super(id, pos);
		super(id);
		planes = new ArrayList<Plane>();
		position = new Immutable<>(pos);
		radarRange = DEFAULT_BASE_RADAR_RANGE;
		fuelResourcesStock = 0;
		militarResourcesStock = 0;
	}

	public Base(Base.View src, Context context) {
		super(src.id());
		context.putSafe(src.getModel(), this);
		// planes = Util.copy(src.copied().planes);
		// planes = Util.copy(src.getPlanes());
		planes = new ArrayList<Plane>();
		for (Plane.FullView p : src.getPlanes())
			// planes.add(Util.copy(p, context));
			planes.add(p.copied(context));
		position = src.getPosition(); // Immutable state can be shared
		radarRange = src.radarRange();
		militarResourcesStock = src.militarResourcesStock();
		fuelResourcesStock = src.fuelResourcesStock();
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
			return (Base) context.get(this);
		Base ret = new Base(view(), context);
		return ret;
	}

	@Override
	public model.Coord.View position() {
		return position.view();
	}

}
