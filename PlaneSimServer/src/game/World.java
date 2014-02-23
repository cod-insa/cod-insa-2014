package game;

import java.util.ArrayList;
import java.util.List;

import model.Coord;

public class World {
	
	Map map;
	
	List<Entity<?>> entities = new ArrayList<>();
	
	public World (Sim sim) {
		sim.w = this;
		map = new Map();
		
		
    	/********** FIXME DEV TEST: **********/
		
		map.bases.add(new Base(sim, new Coord.Unique(.1,.2)));
		map.bases.add(new Base(sim, new Coord.Unique(.2,.5)));
		map.bases.add(new Base(sim, new Coord.Unique(.7,.6)));
		
    	/*************************************/
		
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

