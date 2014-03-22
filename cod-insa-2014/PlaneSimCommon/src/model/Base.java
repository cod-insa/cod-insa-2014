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

public class Base extends Entity implements Serializable, Viewable<Base.View> {

	private static final long serialVersionUID = 1L;

	public final Immutable<Coord.View> position;

	final List<Plane> planes;

	private static final double DEFAULT_BASE_RADAR_RANGE = 0.7;
	
	public class View extends Entity.View {
		public Immutable<Coord.View> getPosition() {
			return Base.this.position;
		}

		public ListView<Plane.View> getPlanes() {
			return Util.view(planes);
		}

		// public BaseModel copied() {
		// return new BaseModel(BaseModel.this);
		// }
		@Override
		public boolean canSee(Entity.View e) {
			if (e instanceof Plane.View
					&& ((Plane.View) e).state() == State.AT_AIRPORT
					&& isFriend(e))
				// FIXME should return true if e is a plane and is in the
				// current base
				return false;
			return isWithinRadar(e.position);
		}

		@Override
		public boolean isWithinRadar(Coord.View pos) {
			return position.squareDistanceTo(pos) <= radarRange * radarRange;
		}

		public Base copied(Set<Object> context) {
			return copy(context);
		}
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
		this.radarRange = DEFAULT_BASE_RADAR_RANGE;
	}

	public Base(Base.View src, Set<Object> context) {
		super(src.id());
		context.add(this);
		// planes = Util.copy(src.copied().planes);
		// planes = Util.copy(src.getPlanes());
		planes = new ArrayList<Plane>();
		for (Plane.View p : src.getPlanes())
			// planes.add(Util.copy(p, context));
			planes.add(p.copied(context));
		position = src.getPosition(); // Immutable state can be shared
		radarRange = src.radarRange();
	}

	// private BaseModel(BaseModel src)
	// {
	// super(src.id);
	// planes = Util.copy(src.planes);
	// position = src.position;
	// }

	@Override
	public Base copy(Set<Object> context) {
		if (context.contains(this))
			return this;
		Base ret = new Base(view(), context);
		return ret;
	}

	@Override
	public model.Coord.View position() {
		return position.view();
	}

}
