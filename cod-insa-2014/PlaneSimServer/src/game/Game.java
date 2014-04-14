package game;

import game.MapLoader.MapInfo;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import model.Coord;
import model.Plane.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.CoordConverter;
import common.ListView;
import common.Util;
import control.Controller;
import display.Displayer;

public class Game {
	
	public static final Logger log = LoggerFactory.getLogger(Game.class);
	
	public final static long update_period = 30;
//	public final static long world_snapshot_frame_period = 50;
	
	//private Controller stepUpdate;
	private FinalCountdown clock;
	private Scores scores;
	
	
	Displayer disp;
	private int nbPlayers;
	int current_frame = 0;
	public int getCurrentFrame() { return current_frame; }
	boolean running = false;
//	public final World world;
	private final World world = new World(this);
	public ListView<GameEntity> entities = Util.shallowView(world.entities);
	public ListView<GamePlane> planes = Util.shallowView(world.planes);
	public ListView<GameBase> bases;
	
	public MapLoader mapLoader;
	public String mapName;
	public CoordConverter converter;
	public MapInfo mapInfo;
	
	//final Timer updateTimer = new Timer();
	//final Timer updateTimer = null;
	Timer updateTimer = null;
	
	int fps = 0;
	long lastTime = -1;
	
	//public final Access<List<Entity>> entities = new BasicAccess(w.entities);
//	public final RAccess<List<Entity<?>>> entities = new RAccess<List<Entity<?>>> () {
//		public List<Entity<?>> get() {
//			return Collections.unmodifiableList(world.entities);
//		}
//	};
	/*// compare to the simpler:
	public final List<Entity> getEntities() {
		return Collections.unmodifiableList(w.entities);
	}
	*/ 
	
	public Game (Displayer disp, int nbplay, String mapName, long seconds) {
		
		this.disp = disp;
		this.nbPlayers = nbplay;
		this.scores = new Scores(nbplay);
		
		try {
			this.mapLoader = new MapLoader(this, mapName);
		} catch (FileNotFoundException | URISyntaxException e) {
			log.error("Map not found exception");
			System.exit(-1);
		}
		this.converter = mapLoader.getConverter();
		this.mapInfo = mapLoader.getM();
				
		//init countdown
		clock = new FinalCountdown(20*60);	//in seconds
				
		
		//this.stepUpdate = new Controller(update_period);
		
//		this.world = new World(this);    // [no more true:] also sets this.world... to avoid NPE..
		
		
//		world.bases.add(new Base(this, new Coord.Unique(.1,.2)));
//		world.bases.add(new Base(this, new Coord.Unique(.2,.5)));
//		world.bases.add(new Base(this, new Coord.Unique(.7,.6)));
//		world.bases.add(new GameBase(this, new Coord.Unique(.3,.2),"debugbase"));
//		world.bases.add(new GameBase(this, new Coord.Unique(.4,.5),"debugbase"));
//		world.bases.add(new GameBase(this, new Coord.Unique(.9,.6),"debugbase"));
		
		bases = Util.shallowView(world.bases);
		
		//new Timer(); new Timer();
		
//		(updateTimer = new Timer()).schedule(new TimerTask() {
//            @Override
//            public void run() {
//            	update();
//            	Controller.get().update(Sim.this);
//            	
//            	if (current_frame%world_snapshot_frame_period == 0)
//            		w.takeSnapshot();
//            	
//            	current_frame++;
//            }
//        }, update_period, update_period);
		
	}
	
	public void start()
	{
		//TODO (called when all players have joined the game)
		
		//clock.start();
		
		//if (updateTimer == null) return;
		
		//if (updateTimer == null)  new Timer();
		if (updateTimer == null) updateTimer = new Timer();
		//if (updateTimer == null) new Timer();
		//if (updateTimer == null) new Timer();
		
		if (!running)
			updateTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					
					long time = System.currentTimeMillis();
					//System.out.println(1f/(float)(time-lastTime));
					fps = (int) (1000f/(float)(time-lastTime));
					lastTime = time;
					
					//System.out.println("A");
					update();
					Controller.get().update(Game.this);
					
					if (current_frame % model.Game.TIME_UNITS_PER_FRAME == 0)
						world.takeSnapshot();
					
					
					if (current_frame % model.Game.TIME_UNITS_PER_FRAME == 0) {
						int N = 0;
						for (int i = 0; i < N; i++) {
							double w = world.width, h = world.height;
							new GamePlane(Game.this, new Coord.Unique(Util.rand.nextDouble()*w, Util.rand.nextDouble()*h), 3, Type.MILITARY);
							new GamePlane(Game.this, new Coord.Unique(Util.rand.nextDouble()*w, Util.rand.nextDouble()*h), 4, Type.MILITARY);
						}
					}
					
					current_frame++;
					//throw new Error();
					//System.out.println("B");
				}
			}, update_period, update_period);
		
		running = true;
	}
	
	public void stop() {
		running = false;
		
		//if (updateTimer == null) return;
		
		log.info("Stopping simulation");
		
		updateTimer.cancel();
		updateTimer.purge();
		
		//updateTimer = null;
		//System.gc();
	}
	
	private List<GameEntity> addedEntities = new ArrayList<>(), removedEntities = new ArrayList<>();
	
	void addEntity (GameEntity e) {
//		world.entities.add(e);
		addedEntities.add(e);
		//if (disp != null)
		disp.addEntity(e);
	}

	void removeEntity (GameEntity e) {
//		world.entities.remove(e);
		removedEntities.add(e);
		//if (disp != null)
		disp.removeEntity(e);
	}
	
	public Scores getScores()
	{
		return scores;
	}
	
	void update()
	{
		disp.flushEntities();
		
		world.entities.addAll(addedEntities);
		for (GameEntity e : addedEntities)
			if (e instanceof GamePlane)
				world.planes.add((GamePlane)e);
		addedEntities.clear();
		world.entities.removeAll(removedEntities);
		world.planes.removeAll(removedEntities);
		removedEntities.clear();
		
		world.update();
	}
	
	
	
	/*
	public void setDisplayer (Displayer disp) {
		this.disp = disp;
	}
	*/
	
	/*
	public List<Entity> getEntities() {
		return Collections.unmodifiableList(w.entities);
	}*/
	public List<GameEntity> _debug_backdoor() { // FIXME
		return world.entities;
	}
	
	
	public GamePlane getPlane(int planeId) {
		// FIXME use a hashmap instead
//		for (Entity<?> e: entities)
//			if (e instanceof Plane && ((Plane)e).id() == planeId)
//				return (Plane)e;
		for (GamePlane p: planes)
			if (p.id() == planeId)
				return p;
		
		//throw new Error("Not found"); // FIXME better exception
		return null;
	}
	public GameBase getBase(int baseId) {
		// FIXME use a hashmap instead
		for (GameBase b: bases)
			if (b.id() == baseId)
				return b;
		return null;
	}
	

	/*
	public Controller getSetpUpdater() {
		return stepUpdate;
	}
	*/
	
	public int getNbPlayers() {
		return nbPlayers;
	}

//	public World getW() {
//		return world;
//	}

	public String getInfoString() {
		return "Entities: "+world.entities.size()+" | FPS: "+fps;
	}

	public World getWorld() {
		return world;
	}
	
	public long getTimeLeft()
	{
		return clock.getRemainingTime();
	}
	
	
}


















