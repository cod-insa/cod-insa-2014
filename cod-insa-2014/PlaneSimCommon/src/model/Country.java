package model;

import java.util.LinkedHashMap;
import java.util.Map;

import common.ListView;
import common.MapView;
import common.Unique;
import common.Util;
import common.Viewable;

public class Country extends AbstractBase implements Viewable<Country.View>{
	public final Map<Integer, Request> productionLine;
	
	/**
	 * This class represent your country
	 */
	public class View extends AbstractBase.View {

		/**
		 * @return All the planes at the airport of the AbstractBase
		 */
		public ListView<Plane.FullView> planes() {
			return Util.view(planes);	
		}
		
		@Override
		Country copied(Context context) {
			return copy(context);
		}
		/**
		 * Get the production line of the country
		 */
		public MapView<Integer, Request.View> productionLine() { return Util.view(productionLine); }
		@Override
		/**
		 * Get a string representation of the country
		 */
		public String toString() { return "id: " + id(); }
	}
	
	/**
	 * This class represents a request in the production line
	 */
	public class Request implements Viewable<Request.View>
	{
		public final int rqId;
		public double timeBeforePlaneBuilt;
		final public Plane.Type requestedType;
		
		public Request(int requestId, double time, Plane.Type ptype)
		{
			rqId = requestId;
			timeBeforePlaneBuilt = time;
//			ptype = requestedType;
			requestedType = ptype;
		}
		
		/**
		 * This class represents the view of a request in the production line
		 */
		public class View implements Viewable.View {
			/**
			 * Get the id of the request
			 */
			public int rqId() { return rqId; }
			/**
			 * Get the time before the corresponding plane will be built
			 */
			public double timeBeforePlaneBuilt() { return timeBeforePlaneBuilt(); }
			
			/**
			 * Get the type of the plane requested
			 */
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
		productionLine = new LinkedHashMap<Integer,Request>();
	}

	public Country(View src, Context context) {
		super(src, context);
		productionLine = new LinkedHashMap<Integer,Request>();
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
