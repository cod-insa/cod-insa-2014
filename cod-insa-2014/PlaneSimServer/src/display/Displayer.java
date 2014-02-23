package display;

import game.Entity;

import java.util.ArrayList;


public class Displayer {
	
	@SuppressWarnings("serial")
	static class EntityList extends ArrayList<Entity<?>> {}
	
	final EntityList[] entitiesByAltitude;
	
	public Displayer() {
		entitiesByAltitude = new EntityList[Entity.Altitude.values().length];
		for (int i = 0; i < entitiesByAltitude.length; i++)
			entitiesByAltitude[i] = new EntityList();
	}
	
	public void addEntity(Entity<?> e) {
		entitiesByAltitude[e.altitude.ordinal()].add(e);
	}
	
}
