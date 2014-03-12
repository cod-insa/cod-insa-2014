package display;

import game.GameEntity;

import java.util.ArrayList;
import java.util.List;


public class Displayer {
	
	@SuppressWarnings("serial")
	static class EntityList extends ArrayList<GameEntity> {}
	
	final EntityList[] entitiesByAltitude;
	
	public Displayer() {
		entitiesByAltitude = new EntityList[GameEntity.Altitude.values().length];
		for (int i = 0; i < entitiesByAltitude.length; i++)
			entitiesByAltitude[i] = new EntityList();
	}

	private List<GameEntity> addedEntities = new ArrayList<>(), removedEntities = new ArrayList<>();
	
	public void addEntity(GameEntity e) {
//		entitiesByAltitude[e.altitude.ordinal()].add(e);
		addedEntities.add(e);
	}
	public void removeEntity(GameEntity e) {
//		entitiesByAltitude[e.altitude.ordinal()].remove(e);
		removedEntities.add(e);
	}
	public synchronized void flushEntities() {
		for (GameEntity e : addedEntities)
			entitiesByAltitude[e.altitude.ordinal()].add(e);
		addedEntities.clear();
		for (GameEntity e : removedEntities)
			entitiesByAltitude[e.altitude.ordinal()].remove(e);
		removedEntities.clear();
	}
	
}
