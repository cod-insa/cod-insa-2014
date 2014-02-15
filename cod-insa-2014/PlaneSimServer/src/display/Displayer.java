package display;

import game.Entity;

import java.util.ArrayList;


public class Displayer {
	
	@SuppressWarnings("serial")
	static class EntityList extends ArrayList<Entity<?>> {}
	
	final EntityList[] entities;
	
	public Displayer() {
		entities = new EntityList[Entity.Altitude.values().length];
		for (int i = 0; i < entities.length; i++)
			entities[i] = new EntityList();
	}
	
	public void addEntity(Entity<?> e) {
		entities[e.altitude.ordinal()].add(e);
	}
	
}
