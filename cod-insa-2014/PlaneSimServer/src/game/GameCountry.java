package game;

import java.util.ArrayList;
import java.util.List;

import model.Coord;
import model.Country;
import model.Entity;
import model.Plane;
import model.Plane.State;
import common.Unique;
import display.CountryDisplay;
import display.EntityDisplay;

public class GameCountry extends MaterialGameEntity implements Landable {
	public static final double RADIUS = GameBase.RADIUS * 2;
	public final String countryname;
	public final List<Request> productionLine;
	
	public GameCountry(Game sim, Unique<Coord> pos, String name) {
		super(new Country(makeNextId(),pos), sim, Altitude.GROUND);
		radius = RADIUS;
		countryname = name;
		productionLine = new ArrayList<Request>();
	}
	
	private static int _nbRq = 0;
	private static int makeNextRqId() { return _nbRq++; }
	public class Request
	{
		public final int RqId;
		public double timeBeforePlaneBuilt;
		public Plane.Type requestedType;

		public Request(Plane.Type type)
		{
			RqId = makeNextRqId();
			timeBeforePlaneBuilt = type.timeToBuild + totalTimeToBuild();
			requestedType = type;
		}
		
		public boolean isPlaneBuilt()
		{
			return timeBeforePlaneBuilt <= 0;
		}
		
		public void continueConstruction(double period)
		{
			// TODO Check if this is ok
			timeBeforePlaneBuilt -= period;
		}
		
		public void createPlane()
		{
			if (isPlaneBuilt())
			{
				GamePlane gp = new GamePlane(sim,new Coord.Unique(model().position().x(), model().position().y()),model().ownerId(),requestedType);
				// FIXME gp.land();
				requestedType = null;
			}
		}
	}
	public void buildPlane(Request r)
	{
		productionLine.add(r);
	}
	
	private double totalTimeToBuild()
	{
		int s = 0;
		for (Request r : productionLine)
			s += r.timeBeforePlaneBuilt;
		return s;
	}

	final CountryDisplay disp = new CountryDisplay(this);
	@Override
	public EntityDisplay<GameCountry> getDisplay() {
		return disp;
	}
	
	@Override
	public void updateSpecialized(double period) {
		super.updateSpecialized(period);
		
		// Avoid concurrent access exception because we modify productionLine
		for (Object o : productionLine.toArray()) 
		{
			Request pl = (Request)o;
			pl.continueConstruction(period);
			if (pl.isPlaneBuilt())
			{
				productionLine.remove(pl);
				pl.createPlane();
			}
		}
	}

	@Override
	public void afterUpdate(double period) {
		// ...
	}

	@Override
	public Country model() { return (Country) model; }
	@Override
	public MaterialGameEntity asMaterialGameEntity() {
		return this;
	}
	@Override
	public Country.View modelView() { return model().view(); }

	
}
