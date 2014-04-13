package game;

import java.util.ArrayList;
import java.util.List;

import model.Coord;
import model.Country;
import model.Entity;
import model.Plane;
import common.Unique;
import display.CountryDisplay;
import display.EntityDisplay;

public class GameCountry extends MaterialGameEntity {

	public static final double RADIUS = .08;
	public final String countryname;
	public final List<GameProductionLine> lines;
	
	public GameCountry(Game sim, Unique<Coord> pos, String name, int productionLineNumber) {
		super(new Country(makeNextId(),pos), sim, Altitude.GROUND);
		radius = RADIUS;
		countryname = name;
		lines = new ArrayList<GameProductionLine>();
		for (int i = 0; i < productionLineNumber;i++)
			lines.add(new GameProductionLine());
	}
	
	public class GameProductionLine
	{
		public double timeBeforePlaneBuilt;
		public Plane.Type requestedType;

		public void beginConstruction(Plane.Type type)
		{
			timeBeforePlaneBuilt = type.timeToBuild;
			requestedType = type;
		}
		
		public boolean isPlaneBuilt()
		{
			return timeBeforePlaneBuilt <= 0 && requestedType != null;
		}
		
		public void continueConstruction()
		{
			// TODO Check if this is ok
			timeBeforePlaneBuilt--;
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
	
	@Override
	public EntityDisplay<GameCountry> getDisplay() {
		return new CountryDisplay(this);
	}
	
	@Override
	public void updateSpecialized(double period) {
		super.updateSpecialized(period);
		for (GameProductionLine pl : lines)
		{
			pl.continueConstruction();
			if (pl.isPlaneBuilt())
				pl.createPlane();
		}
	}

	@Override
	public void afterUpdate(double period) {
		// ...
	}

	@Override
	Country model() { return (Country) model; }

	@Override
	public Country.View modelView() { return model().view(); }
	
}
