package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import model.Plane.State;

import common.Immutable;
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
	
	@Override
	public FullView view() {
		return new FullView();
	}
	
	public BasicView restrictedView() {
		return new BasicView();
	}
	
	public Base(int id, Unique<Coord> pos) {
		super(id);
		axes = new HashSet<>();
		position = new Immutable<>(pos);
		radarRange = DEFAULT_BASE_RADAR_RANGE;
		fuelInStock = 0;
		militaryGarrison = 0;
	}
	
	public Base(FullView src, Context context) {
		super(src, context);
		axes = new HashSet<>();
		radarRange = DEFAULT_BASE_RADAR_RANGE;
		position = src.getPosition();
		for (ProgressAxis.Oriented oa: src.axes())
			//axes.add(a.copied(context));
			axes.add(oa.copy(context));
		militaryGarrison = src.militaryGarrison();
		fuelInStock = src.fuelInStock();
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
