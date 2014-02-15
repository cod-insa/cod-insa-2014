package game;

import java.util.ArrayList;
import java.util.List;

import model.Coord;

public class World {
	
	Map map;
	
	//Basically, the information sent to the client at each round (about every second)
	List<Entity<?>> entities = new ArrayList<Entity<?>>();
	
	public World (Sim sim) {
		sim.w = this;
		map = new Map();
		

    	/********** FIXME DEV TEST: **********/
		
		entities.add(new Plane(sim, new Coord(.4,.8)));
		entities.add(new Plane(sim, new Coord(.0,.1)));
		entities.add(new Plane(sim, new Coord(.6,.6)));
		
		entities.add(new Base(sim, new Coord(.5,.5)));
		entities.add(new Base(sim, new Coord(.0,.0)));
		entities.add(new Base(sim, new Coord(.8,.8)));
		
		/*map.bases.add(new Base(sim, new Coord(.1,.2)));
		map.bases.add(new Base(sim, new Coord(.2,.5)));
		map.bases.add(new Base(sim, new Coord(.7,.6)));*/

    	/*************************************/
		
	}
	
	
	public List<Entity<?>> getEntities() {
		return entities;
	}


	public void update() {
		update(1);
	}
	public void update(double period) {
		
		for (Entity<?> e: entities) {
			
			e.update(period);
			
		}
		
	}

}

