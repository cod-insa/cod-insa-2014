package model;

import java.util.HashMap;
import java.util.Map;

import common.MapView;
import common.Unique;
import common.Util;
import common.Viewable;

public class Country extends AbstractBase implements Viewable<Country.View>{
	public final Map<Integer, Request> productionLine;
	
	public class View extends AbstractBase.View {

		@Override
		public Country copied(Context context) {
			return copy(context);
		}
		public MapView<Integer, Request.View> productionLine() { return Util.view(productionLine); }
		public boolean lineFull() { 
			return productionLine.isEmpty();
		}
	}
	
	public static class Request implements Viewable<Request.View>
	{
		public final int rqId;
		public double timeBeforePlaneBuilt;
		public Plane.Type requestedType;
		
		public Request(int requestId, double time, Plane.Type ptype)
		{
			rqId = requestId;
			timeBeforePlaneBuilt = time;
			ptype = requestedType;
		}
		
		public class View implements Viewable.View {
			public int rqId() { return rqId; }
			public double timeBeforePlaneBuilt() { return timeBeforePlaneBuilt(); }
			public Plane.Type requestedType() { return requestedType; }
		}
		
		@Override
		public View view()
		{
			return new View();
		}
		
	}
	
	
	public Country(int id, Unique<Coord> pos) {
		super(id, pos);
		productionLine = new HashMap<Integer,Request>();
	}

	public Country(View src, Context context) {
		super(src, context);
		productionLine = new HashMap<Integer,Request>();
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
