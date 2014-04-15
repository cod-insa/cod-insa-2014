package game;

import java.util.*;

import model.AbstractBase;
import model.Base;
import model.Country;
import model.Plane;
import model.ProgressAxis;
import common.Immutable;
import common.ListView;
import common.Unique;
import common.Unique.Collection;
import common.Util;
import common.Util.Converter;
import common.Viewable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class World implements Viewable<World.View> {

	public static final Logger log = LoggerFactory.getLogger(World.class);

	private static double S = 3;
	private static final double DEFAULT_WIDTH = S, DEFAULT_HEIGHT = S;
	
	public final double width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;
	
	private int currentSnapshotId = 0;
	private Snapshot currentSnapshot;
	public final Object snapshotsMonitor = new Object();
	
	public List<GameBase> bases = new ArrayList<>();
	
	// All the current entities of the game
	List<GameEntity> entities = new ArrayList<>();
	public List<GamePlane> planes = new ArrayList<>();
	public List<GameCountry> countries = new ArrayList<>();
	
//	public List<GameAxis> axes = new ArrayList<>();
	
	private boolean initialized = false;
	
	public World (Game sim) {
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
	
	class View implements Viewable.ViewOf<World> {
		public final ListView<Base.FullView> bases = Util.transformView (World.this.bases, new Converter<GameBase, Base.FullView>() {
			public Base.FullView convert(GameBase src) { return src.model().view(); }
		});
		
	}
	public final View view = new View();
	
	public View view() { return view; }
	
	public static class Snapshot {
		
		public final int id;
		
		public final Immutable<ListView<Base.FullView>> bases;
		public final Immutable<ListView<Plane.FullView>> planes;
		public final Immutable<ListView<ProgressAxis.View>> axes;
		public final Immutable<ListView<Country.View>> countries;
		
		public final double width, height;
		
		Snapshot(World w) {

			width = w.width;
			height = w.height;
			
			this.id = w.currentSnapshotId;
			
			// For bases:
			
			// We start by making a list view of all our bases' models (each game.Base has a model.BaseModel attribute named "model")
			
			ListView<Base> vbases = Util.transformView (w.bases, new Converter<GameBase, Base>() {
				public Base convert(GameBase src) { return src.model(); }
			});
			
			// Then we make a unique deep copy of all those models using the fact that model.Base is Copyable,
			// and using Util.getListCopier() to perform the deep copy on the java.util.List
			
			Unique<List<Base>> ubases = new Unique.Copy<>(vbases.asUnmodifiableList(), Util.<Base>getListCopier());
			
			// Finally, we can create a safe immutable view of this copy because no one else can access
			// this unique copy and thus no one can modify it

			bases = new Immutable<>(ubases, Util.<Base, Base.FullView>getListViewer());
			//bases = new Immutable<>(ubases, Util.getViewer(new ArrayList<BaseModel>())); // works but does a useless instantiation
			//bases = new Immutable<>(ubases, Util.getInternalViewer(ubases)); // doesn't work
			
			
//			// This was a lot of fun. Let's proceed differently for planes (we don't have a w.planes collection):
//			
//			// Make a Unique.Collection of PlaneModels that we will fill with unique PlaneModels
//			
//			Unique.Collection<Plane, List<Plane>> uplanes =
//					new Unique.Collection<Plane, List<Plane>>(ArrayList.class);
//			
//			// Fill the list with unique copies of our plane models
//			
//			for (GameEntity e : w.entities)
//				if (e.model instanceof Plane)
//					uplanes.add(Unique.Copy.make((Plane)e.model));
//
//			// Get a safe immutable list view for this unique list
//			
//			planes = new Immutable<ListView<Plane.FullView>>(uplanes, Util.<Plane, Plane.FullView>getListViewer());

			ListView<Plane> vplanes = Util.transformView (w.planes, new Converter<GamePlane, Plane>() {
				public Plane convert(GamePlane src) { return src.model(); }
			});
			planes = new Immutable<>(new Unique.Copy<>(vplanes.asUnmodifiableList(), Util.<Plane>getListCopier()), Util.<Plane, Plane.FullView>getListViewer());
			
			
			
			// This was a lot of fun. Let's proceed differently for planes (we don't have a w.axes collection):
			
			// Make a Unique.Collection of PlaneModels that we will fill with unique PlaneModels
			
			Unique.Collection<ProgressAxis, List<ProgressAxis>> uaxes =
					new Unique.Collection<ProgressAxis, List<ProgressAxis>>(ArrayList.class);
			
			// Fill the list with unique copies of our plane models
			
			for (GameEntity e: w.entities)
			{
				if (e.model instanceof ProgressAxis)
					uaxes.add(Unique.Copy.make((ProgressAxis)e.model));
			}
			
			// Get a safe immutable list view for this unique list
			
			axes = new Immutable<ListView<ProgressAxis.View>>(uaxes, Util.<ProgressAxis, ProgressAxis.View>getListViewer());
			
			ListView<Country> vcountries = Util.transformView (w.countries, new Converter<GameCountry, Country>() {
				public Country convert(GameCountry src) { return src.model(); }
			});
			
			countries = new Immutable<>(new Unique.Copy<>(vcountries.asUnmodifiableList(), Util.<Country>getListCopier()), Util.<Country, Country.View>getListViewer());
			
		}
	}
	
	public void takeSnapshot() {
		currentSnapshot = new Snapshot(this);
		++currentSnapshotId;
		
		synchronized (this.snapshotsMonitor) { snapshotsMonitor.notifyAll(); }
		
		//System.out.println("Made a new snapshot. Number of planes: "+currentSnapshot.planes.view.size());
		
	}
	
	public Snapshot getCurrentSnapshot() {
		//if (currentSnapshot == null)
		return currentSnapshot;
	}
	
	public List<GameEntity> getEntities() {
		return entities;
	}


	//Map<GameBase,Map<GameBase,GameBase>> nextBaseToGoTo;
	
	class BaseCache {
//		final GameBase parent;
//		final int distance;
	}
	
	Map<GameBase,Map<GameBase,GameBase>> nextBaseToGoTo;
	
	public void initialize(Game gam) {
		
		nextBaseToGoTo = new HashMap<>();
		
		boolean connected = true;
				
		for (GameBase src: bases) {
			
			Map<GameBase,GameBase> toGoTo = new HashMap<>();
			nextBaseToGoTo.put(src, toGoTo);
			
			/// Performs a BFS to assign which base troops should go through
			/// to go from src to all other bases

			Set<GameBase> visited = new HashSet<>();
			Queue<GameBase> toVisit = new LinkedList<>();
			
			toVisit.add(src);
			
			while (toVisit.size() != 0) {
				GameBase current = toVisit.poll();
				
				for (GameAxis.Oriented arc: current.axes) {
					GameBase target = arc.next;
					if (!visited.contains(target)) {
						visited.add(target);
						toGoTo.put(target, current);
					}
				}
			}
			
			connected = connected && visited.size() == bases.size();
		}
		if (!connected)
			log.warn("The bases graph seems not to be one connected component!");

		initialized = true;
	}

	public void update() {
		update(1);
	}
	public void update(double period) {
		
		if (!initialized)
			throw new Error("The World object was not initialized.");


		//globalUnitNumber

		// use distance to front as a heuristic?

//		for (GameAxis ax: axes) {

		Map<Integer,List<GameBase>> playerBases = new HashMap<>();
		
		for (GameBase b: bases) {
			if (b.model.ownerId() != 0) {
				List<GameBase> bases = playerBases.get(b.model.ownerId());
				if (bases == null) {
					bases = new ArrayList<GameBase>();
					playerBases.put(b.model.ownerId(), bases);
				}
				bases.add(b);
			}
		}
		
		for (int pid: playerBases.keySet()) {
			List<GameBase> bases = playerBases.get(pid);
			double totalMilitary = 0;
			for (GameBase b: bases) {
				totalMilitary += b.model().militaryGarrison;
			}
		}
		
		


		for (GameEntity e: entities) {
			
			e.update(period);
			
		}

		for (GameEntity e: entities)
			e.afterUpdate(period);
		
		
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
	
	
	
	
	
}








