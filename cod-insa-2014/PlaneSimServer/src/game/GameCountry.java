package game;

import java.util.ArrayList;
import java.util.List;

import model.Coord;
import model.Country;
import model.Entity;
import model.ProductionLine;
import common.Unique;
import display.CountryDisplay;
import display.EntityDisplay;

public class GameCountry extends MaterialGameEntity {

	public static final double RADIUS = .08;
	public final String countryname;
	public final List<GameProductionLine> lines;
	
	public GameCountry(Game sim, Unique<Coord> pos, String name) {
		super(new Country(makeNextId(),pos), sim, Altitude.GROUND);
		radius = RADIUS;
		countryname = name;
		lines = new ArrayList<GameProductionLine>();
	}
	
	public class GameProductionLine extends GameEntity {

		public GameProductionLine(Entity model, Game sim) {
			super(new ProductionLine(makeNextId()), sim, Altitude.GROUND);
		}

		@Override
		public void updateSpecialized(double period) {
			
		}

		@Override
		public EntityDisplay<?> getDisplay() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Override
	public EntityDisplay<GameCountry> getDisplay() {
		return new CountryDisplay(this);
	}
	
	@Override
	public void updateSpecialized(double period) {
		super.updateSpecialized(period);
		// TODO Auto-generated method stub
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
