package game;

import java.util.ArrayList;
import java.util.List;

import model.Coord;

import common.Immutable;
import common.ListView;
import common.Unique;
import common.Util;
import common.Util.Converter;

public class World {
	
	private int currentSnapshotId = 0;
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

	static class Snapshot {
		
		public final int id;
		
		/*
		public final ImList<model.Base.View> bases;
		//public final Immutable<List<model.Base.View>> bases;
		public final ImList<model.Plane.View> planes;
		*/
		//public final Immutable<List<model.Base.View>> bases;
		public final Immutable<ListView<model.BaseModel.View>> bases;
		public final Immutable<ListView<model.PlaneModel.View>> planes;
		
		Snapshot(World w) {
			
			this.id = w.currentSnapshotId;
			
			//bases = Util.view(bases);
			//planes = null;
			
			//Copyable.Copier<List<Coord>> a = Util.getListCopier();
			
			//bases = Util.immutableCopy();
//			bases = new Immutable<ListView<model.Base.View>>(
//					new Unique.Copy<List<Base>>(w.bases, Util.getListCopier()),
//					Util.listViewer
//				);
			
			// We start by making a list view of all our bases' models (each game.Base has a model.Base attribute named "model")
			
			ListView<model.BaseModel> vbases = Util.transform (w.bases, new Converter<Base, model.BaseModel>() {
				public model.BaseModel convert(Base src) { return src.model; }
			});
			
			// Then we make a unique deep copy of all those models using the fact that model.Base is Copyable,
			// and using Util.getListCopier() to perform the deep copy on the java.util.List
			
			//new Unique.Copy<ListView<model.Base>>(vbases.asUnmodifiableList(), Util.getListViewCopier());
			//Unique<List<model.Base>> ubases = new Unique.Copy<List<model.Base>>(vbases.asUnmodifiableList(), Util.<model.Base>getListCopier());
			Unique<List<model.BaseModel>> ubases = new Unique.Copy<>(vbases.asUnmodifiableList(), Util.<model.BaseModel>getListCopier());
			
			// Finally, we can create a safe immutable view of this copy because no one else can access
			// this unique copy and thus no one can modify it
			
			//bases = new Immutable<ListView<model.Base.View>>(ubases, Util.<model.Base, model.Base.View>getListViewer());
			bases = new Immutable<>(ubases, Util.<model.BaseModel, model.BaseModel.View>getListViewer());
			
			/*
			planes = new Immutable<>(
					new Unique.Copy<>(
							Util.transformIf (w.entities, new ConditionalConverter<Entity<?>, model.Plane>() {
								public model.Plane convert(Entity<?> src) { return (model.Plane)src; }
								public boolean canConvert(Entity<?> src) { return src instanceof Plane; }
							}),
							Util.<model.Base>getListCopier()
						),
					Util.<model.Plane, model.Plane.View>getListViewer()
				);
			*/
			
			//Unique.List<model.Plane> uplanes = new Unique.List<>();
			Unique.Collection<model.PlaneModel, List<model.PlaneModel>> uplanes =
					//new Unique.Collection<model.Plane, List<model.Plane>>((Class<List>)ArrayList.class);
					//new Unique.Collection<model.Plane, List<model.Plane>>(List.class);
					new Unique.Collection<model.PlaneModel, List<model.PlaneModel>>(ArrayList.class);
			
			
			for (Entity<?> e : w.entities)
				if (e.model instanceof model.PlaneModel)
					uplanes.add(Unique.Copy.make((model.PlaneModel)e.model));
			
			planes = new Immutable<ListView<model.PlaneModel.View>>(uplanes, Util.<model.PlaneModel, model.PlaneModel.View>getListViewer());
			
		}
	}
	
	public void takeSnapshot() {
		currentSnapshot = new Snapshot(this);
		++currentSnapshotId;
		
		System.out.println("Made a new snapshot. Number of planes: "+currentSnapshot.planes.view.size());
		
	}
	
	public Snapshot getCurrentSnapshot() {
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
	
	/*
	@Override
	public World copy() {
		new World(sim);
		map
		return null;
	}
	*/
}








