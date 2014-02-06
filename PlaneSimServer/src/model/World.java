package model;

import java.util.ArrayList;
import java.util.List;

public class World {
	
	Map map;
	
	List<Entity> entities = new ArrayList<>();
	
	public World (Sim sim) {
		sim.w = this;
		map = new Map();
		

    	/********** FIXME DEV TEST: **********/

		map.bases.add(new Base(sim, new Coord(.1,.2)));
		map.bases.add(new Base(sim, new Coord(.2,.5)));
		map.bases.add(new Base(sim, new Coord(.7,.6)));

    	/*************************************/
		
	}
	
	public void update() {
		update(1);
	}
	public void update(double period) {
		
		for (Entity e: entities) {
			
			e.update(period);
			
		}
		
	}

}

