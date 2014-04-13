package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import common.Unique;
import common.Viewable;

public class Country extends AbstractBase {

	public final List<Request> productionLine;
	
	public class View extends AbstractBase.View {

		@Override
		public Country copied(Context context) {
			return copy(context);
		}
		public List<Request> lines() { return productionLine; }
		public boolean linesFull() { 
			for (Request pl : productionLine) 
				if (pl.requestedType == null)
					return false;
			return true;
		}
	}
	
	public class Request 
	{
		public double timeBeforePlaneBuilt;
		public Plane.Type requestedType;
		
		public Request(double time, Plane.Type ptype)
		{
			timeBeforePlaneBuilt = time;
			ptype = requestedType;
		}
		
		public class View implements Viewable.View {
			public double timeBeforePlaneBuilt() { return timeBeforePlaneBuilt(); }
			public Plane.Type requestedType() { return requestedType; }
		}
		
		public View view()
		{
			return new View();
		}
		
	}
	
	
	public Country(int id, Unique<Coord> pos) {
		super(id, pos);
		productionLine = new ArrayList<Request>();
	}

	public Country(View src, Context context) {
		super(src, context);
		productionLine = new ArrayList<Request>();
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
