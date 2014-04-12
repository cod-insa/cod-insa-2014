package model;

import common.Viewable;
import common.Copyable.Context;

public class ProductionLine extends Entity implements Viewable<ProductionLine.View>
{
	
	public double timeBeforePlaneBuilt;
	public Plane.Type requestedType;
	
	public class View extends Entity.View
	{
		public double timeBeforePlaneBuilt() { return timeBeforePlaneBuilt; }
		public Plane.Type requestedType() { return requestedType; }
	}

	public ProductionLine(int id) {
		super(id);
	}

	public ProductionLine(View pl, Context context) {
		super(pl.id());
	}
	

	@Override
	public View view()
	{
		return new View();
	}
	
	@Override
	public Object copy(Context context) {
		if (context.containsKey(this))
			return context.getSafe(this);
		ProductionLine ret = new ProductionLine(view(), context);
		return ret;
	}
	
	
}
