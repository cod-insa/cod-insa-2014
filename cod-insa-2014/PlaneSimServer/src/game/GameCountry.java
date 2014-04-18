package game;

import java.util.ArrayList;
import java.util.List;

import model.*;
import model.Country.Request;
import model.Country.Request.View;
import model.Plane.State;
import common.Unique;
import display.CountryDisplay;
import display.EntityDisplay;

public class GameCountry extends MaterialGameEntity implements Landable {
	public static final double RADIUS = GameBase.RADIUS * 2;
	public final String countryname;
	
	public GameCountry(Game sim, Unique<Coord> pos, String name) {
		super(new Country(makeNextId(),pos), sim, Altitude.GROUND);
		radius = RADIUS;
		countryname = name;
	}
	
	private static int _nbRq = 0;
	private static int makeNextRqId() { return _nbRq++; }
	public class GameRequest extends model.Country.Request
	{
		public GameRequest(Plane.Type type)
		{
			GameCountry.this.model().super(makeNextRqId(),type.timeToBuild + totalTimeToBuild(),type);
		}
		
		public boolean isPlaneBuilt()
		{
			return timeBeforePlaneBuilt <= 0;
		}
		
		public void continueConstruction(double period)
		{
			timeBeforePlaneBuilt -= period;
		}
		
		public void createPlane()
		{
			if (isPlaneBuilt())
			{
				GamePlane gp = new GamePlane(sim,new Coord.Unique(model().position().x(), model().position().y()),model().ownerId(),requestedType);
				gp.autoPilot.land(GameCountry.this);
			}
		}
	}
	public void buildPlane(GameRequest r)
	{
		model().productionLine.put(r.rqId,r);
	}
	
	private double totalTimeToBuild()
	{
		double max = 0;
		for (Request r : model().productionLine.values())
			if (r.timeBeforePlaneBuilt > max)
				max = r.timeBeforePlaneBuilt;
		return max;
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
		for (Object o : model().productionLine.values().toArray()) 
		{
			GameRequest r = (GameRequest)o;
			r.continueConstruction(period);
			if (r.isPlaneBuilt())
			{
				model().productionLine.remove(r.rqId);
				r.createPlane();
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
	
	@Override
	public int landingCapacity() { return GameSettings.MAX_PLANES_PER_COUNTRY; }

	
}





