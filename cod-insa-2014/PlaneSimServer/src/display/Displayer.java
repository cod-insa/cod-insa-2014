package display;

import game.Entity;

import java.util.ArrayList;
import java.util.List;


public class Displayer {
	
	@SuppressWarnings("serial")
	static class EntityList extends ArrayList<Entity> {}
	
	final EntityList[] entitiesByAltitude;
	
	public Displayer() {
		entitiesByAltitude = new EntityList[Entity.Altitude.values().length];
		for (int i = 0; i < entitiesByAltitude.length; i++)
			entitiesByAltitude[i] = new EntityList();
	}

	private List<Entity> addedEntities = new ArrayList<>(), removedEntities = new ArrayList<>();
	
	public void addEntity(Entity e) {
//		entitiesByAltitude[e.altitude.ordinal()].add(e);
		addedEntities.add(e);
	}
	public void removeEntity(Entity e) {
//		entitiesByAltitude[e.altitude.ordinal()].remove(e);
		removedEntities.add(e);
	}
	public synchronized void flushEntities() {
		for (Entity e : addedEntities)
			entitiesByAltitude[e.altitude.ordinal()].add(e);
		addedEntities.clear();
		for (Entity e : removedEntities)
			entitiesByAltitude[e.altitude.ordinal()].remove(e);
		removedEntities.clear();
	}
	
}
