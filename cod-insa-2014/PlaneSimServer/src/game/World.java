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
		
		/*
		new Base(sim, new Coord.Unique(.5,.5));
		new Base(sim, new Coord.Unique(.0,.0));
		new Base(sim, new Coord.Unique(.8,.8));
		*/
		
		/*entities.add(new Plane(sim, new Coord(.4,.8)));
		entities.add(new Plane(sim, new Coord(.0,.1)));
		entities.add(new Plane(sim, new Coord(.6,.6)));
		*/
		
		map.bases.add(new Base(sim, new Coord.Unique(.1,.2)));
		map.bases.add(new Base(sim, new Coord.Unique(.2,.5)));
		map.bases.add(new Base(sim, new Coord.Unique(.7,.6)));
		
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

