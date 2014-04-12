package model;


import java.util.ArrayList;
import java.util.List;

import common.ListView;
import common.Unique;
import common.Util;
import common.Copyable.Context;

public abstract class AbstractBase extends MaterialEntity {
	
	
	final List<Plane> planes;
	
	public double militaryGarrison;
	public double fuelInStock;
	
	public abstract class View extends MaterialEntity.View {
		public ListView<Plane.FullView> planes() {
			return Util.view(planes);
		}
		public abstract AbstractBase copied(Context context);
	}

	@Override
	public abstract View view();

	public AbstractBase(int id, Unique<Coord> pos) {
		super(id);
		planes = new ArrayList<>();
		fuelInStock = 0;
		militaryGarrison = 0;
	}

	public AbstractBase(AbstractBase.View src, Context context) {
		super(src.id());
		context.putSafe(src.model(), this);
		planes = new ArrayList<>();
		for (Plane.FullView p : src.planes())
			planes.add(p.copied(context));

		radarRange = src.radarRange();
	}

	public AbstractBase(int id) {
		super(id);
		planes = new ArrayList<>();
	}

	@Override
	public abstract model.Coord.View position();

}
