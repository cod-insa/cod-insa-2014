package game;

import java.util.ArrayList;
import java.util.List;

import model.BaseModel;
import model.PlaneModel;

import common.Immutable;
import common.ListView;
import common.Unique;
import common.Util;
import common.Util.Converter;

public class World {
	
	private int currentSnapshotId = 0;
	private Snapshot currentSnapshot;
	
	List<Base> bases = new ArrayList<Base>();
	
	// All the current entities of the game
	List<Entity<?>> entities = new ArrayList<Entity<?>>();
	
	public World (Sim sim) {
		//sim.world = this;
		
		
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
		
//		bases.add(new Base(sim, new Coord.Unique(.1,.2)));
//		bases.add(new Base(sim, new Coord.Unique(.2,.5)));
//		bases.add(new Base(sim, new Coord.Unique(.7,.6)));
		
    	/*************************************/
		
	}

	public static class Snapshot {
		
		public final int id;
		
		public final Immutable<ListView<BaseModel.View>> bases;
		public final Immutable<ListView<PlaneModel.View>> planes;
		
		Snapshot(World w) {
			
			this.id = w.currentSnapshotId;
			
			// For bases:
			
			// We start by making a list view of all our bases' models (each game.Base has a model.BaseModel attribute named "model")
			
			ListView<BaseModel> vbases = Util.transform (w.bases, new Converter<Base, BaseModel>() {
				public BaseModel convert(Base src) { return src.model; }
			});
			
			// Then we make a unique deep copy of all those models using the fact that model.Base is Copyable,
			// and using Util.getListCopier() to perform the deep copy on the java.util.List
			
			Unique<List<BaseModel>> ubases = new Unique.Copy<>(vbases.asUnmodifiableList(), Util.<BaseModel>getListCopier());
			
			// Finally, we can create a safe immutable view of this copy because no one else can access
			// this unique copy and thus no one can modify it
			
			bases = new Immutable<>(ubases, Util.<BaseModel, BaseModel.View>getListViewer());
			
			
			// This was a lot of fun. Let's proceed differently for planes (we don't have a w.planes collection):
			
			// Make a Unique.Collection of PlaneModels that we will fill with unique PlaneModels
			
			Unique.Collection<PlaneModel, List<PlaneModel>> uplanes =
					new Unique.Collection<PlaneModel, List<PlaneModel>>(ArrayList.class);
			
			// Fill the list with unique copies of our plane models
			
			for (Entity<?> e : w.entities)
				if (e.model instanceof PlaneModel)
					uplanes.add(Unique.Copy.make((PlaneModel)e.model));

			// Get a safe immutable list view for this unique list
			
			planes = new Immutable<ListView<PlaneModel.View>>(uplanes, Util.<PlaneModel, PlaneModel.View>getListViewer());
			
		}
	}
	
	public void takeSnapshot() {
		currentSnapshot = new Snapshot(this);
		++currentSnapshotId;
		
		synchronized (this) { notifyAll(); }
		
		System.out.println("Made a new snapshot. Number of planes: "+currentSnapshot.planes.view.size());
		
	}
	
	public Snapshot getCurrentSnapshot() {
		//if (currentSnapshot == null)
		return currentSnapshot;
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








