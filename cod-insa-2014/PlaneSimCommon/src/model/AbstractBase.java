package model;


import java.util.ArrayList;
import java.util.List;

import common.Immutable;
import common.ListView;
import common.Unique;
import common.Util;

public abstract class AbstractBase extends MaterialEntity {
	
	
	final List<Plane> planes;
	public final Immutable<Coord.View> position;
	
	public abstract class View extends MaterialEntity.View {
		public Immutable<Coord.View> getPosition() {
			return AbstractBase.this.position;
		}
		
		public ListView<Plane.FullView> planes() {
			return Util.view(planes);
		}
		public abstract AbstractBase copied(Context context);
	}

	@Override
	public abstract View view();

	public AbstractBase(AbstractBase.View src, Context context) {
		super(src);
		context.putSafe(src.model(), this);
		planes = new ArrayList<>();
		for (Plane.FullView p : src.planes())
			planes.add(p.copied(context));

		position = src.getPosition(); // Immutable state can be shared
		radarRange = src.radarRange();
	}

	public AbstractBase(int id, Unique<Coord> pos) {
		super(id);

		position = new Immutable<>(pos);
		planes = new ArrayList<>();
	}


	@Override
	public Coord.View position() {
		return position.view();
	}

}