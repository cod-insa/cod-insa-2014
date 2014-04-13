package model;

import java.util.ArrayList;
import java.util.List;

import common.Unique;

public class Country extends AbstractBase {

	public final List<ProductionLine> lines;
	
	public class View extends AbstractBase.View {

		@Override
		public Country copied(Context context) {
			return copy(context);
		}
		
	}
	
	public class ProductionLine
	{
		public final double timeBeforePlaneBuilt;
		public final Plane.Type requestedType;
		
		public ProductionLine(double time, Plane.Type type)
		{
			timeBeforePlaneBuilt = time;
			requestedType = type;
		}
	}
	
	public Country(int id, Unique<Coord> pos) {
		super(id, pos);
		lines = new ArrayList<ProductionLine>();
	}

	public Country(View src, Context context) {
		super(src, context);
		lines = new ArrayList<ProductionLine>();
	}

	@Override
	public Country copy(Context context) {
		if (context.containsKey(this))
			return context.getSafe(this);
		Country c = new Country(view(),context);
		return c;
	}

	@Override
	public View view() {
		return new View();
	}

}
