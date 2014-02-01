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
			
			e.update(period);
			
		}
		
	}

}

