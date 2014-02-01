package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
	
	Map map;
	
	List<Entity> entities = new ArrayList<>();
	
	Random r = new Random();

	public void update() {
		update(1);
	}
	public void update(double period) {
		
		for (Entity e: entities) {
			//e.position.x += 1;
			//e.pos.x += 1;
			
			/*
			int var = 4;
			e.pos.x += r.nextInt(var+1)-var/2;
			e.pos.y += r.nextInt(var+1)-var/2;
			*/
			
			//e.rot += .01;
			/*
			e.pos.x += Math.cos(e.rot)*e.spe;
			e.pos.y += Math.sin(e.rot)*e.spe;
			*/
			e.update(period);
			
		}
		
	}

}

