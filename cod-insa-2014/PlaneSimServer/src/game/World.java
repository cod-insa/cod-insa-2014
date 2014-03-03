package game;

import java.util.ArrayList;
import java.util.List;

import model.Coord;

import common.Immutable;
import common.ListView;
import common.Unique;
import common.Util;

public class World {
	
	private int snapshotId = 0;
	private Snapshot currentSnapshot;
	
	List<Base> bases = new ArrayList<Base>();
	
	//Basically, the information sent to the client at each round (about every second)
	List<Entity<?>> entities = new ArrayList<Entity<?>>();
	
	public World (Sim sim) {
		sim.w = this;
		
		
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
		
		bases.add(new Base(sim, new Coord.Unique(.1,.2)));
		bases.add(new Base(sim, new Coord.Unique(.2,.5)));
		bases.add(new Base(sim, new Coord.Unique(.7,.6)));
		
    	/*************************************/
		
	}
	
	class static Snapshot {
		/*
		public final ImList<model.Base.View> bases;
		//public final Immutable<List<model.Base.View>> bases;
		public final ImList<model.Plane.View> planes;
		*/
		//public final Immutable<List<model.Base.View>> bases;
		public final Immutable<ListView<model.Base.View>> bases;
		
		public Snapshot(World w) {
			//bases = Util.view(bases);
			//planes = null;
			
			//Copyable.Copier<List<Coord>> a = Util.getListCopier();
			
			//bases = Util.immutableCopy();
			bases = new Immutable<ListView<model.Base.View>>(
					new Unique.Copy<List<Base>>(w.bases, Util.getListCopier()),
					Util.listViewer
				);
			
		}
	}
	
	public Snapshot getCurrentSnapshot() {
		return null;
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
	
	/*
	@Override
	public World copy() {
		new World(sim);
		map
		return null;
	}
	*/
}








