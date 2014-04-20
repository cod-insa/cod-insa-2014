package game;

import game.MapLoader.MapInfo;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import main.Main;
import model.Coord;
import model.GameSettings;
import model.Plane.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import players.NetworkPlayerManager;

import common.CoordConverter;
import common.ListView;
import common.Util;

import control.Controller;
import display.Displayer;

public class Game {
	
	public static final Logger log = LoggerFactory.getLogger(Game.class);
	
	public final static long update_period = Settings.GAME_FRAMES_PER_SECOND; //30;
//	public final static long world_snapshot_frame_period = 50;
	
	//private Controller stepUpdate;
	private FinalCountdown clock;
//	private Scores scores;
	
	
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
	public ListView<GameCountry> countries;
	
	public MapLoader mapLoader;
	public String mapName;
	public CoordConverter converter;
	public MapInfo mapInfo;
	
	//final Timer updateTimer = new Timer();
	//final Timer updateTimer = null;
	Timer updateTimer = null;
	
	public final long timeOut;
	
	int fps = 0;
	long lastTime = -1;
	
	public NetworkPlayerManager npm;
	
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
	
	public final double[] scores;
	
	
	public Game (Displayer disp, int nbplay, String mapName, long seconds) {
		
		this.timeOut = seconds;
		
		this.disp = disp;
		this.nbPlayers = nbplay;
//		this.scores = new Scores(nbplay); // FIXME
		this.scores = new double[nbPlayers+1];
		
		try {
			this.mapLoader = new MapLoader(this, mapName);
		} catch (FileNotFoundException | URISyntaxException e) {
			log.error("Map not found exception");
			System.exit(-1);
		}
		this.converter = mapLoader.getConverter();
		this.mapInfo = mapLoader.getM();
		
		//init countdown
//		clock = new FinalCountdown(20*60);	//in seconds
				
		
		//this.stepUpdate = new Controller(update_period);
		
//		this.world = new World(this);    // [no more true:] also sets this.world... to avoid NPE..
		
		
//		world.bases.add(new Base(this, new Coord.Unique(.1,.2)));
//		world.bases.add(new Base(this, new Coord.Unique(.2,.5)));
//		world.bases.add(new Base(this, new Coord.Unique(.7,.6)));
//		world.bases.add(new GameBase(this, new Coord.Unique(.3,.2),"debugbase"));
//		world.bases.add(new GameBase(this, new Coord.Unique(.4,.5),"debugbase"));
//		world.bases.add(new GameBase(this, new Coord.Unique(.9,.6),"debugbase"));
		
		bases = Util.shallowView(world.bases);
		countries = Util.shallowView(world.countries);
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
	
	private void deb_base(int id, int owner, double garrison) {
		getBase(id).model().ownerId(owner);
		getBase(id).model().militaryGarrison = garrison;
	}
	
	public void start()
	{
		//TODONE (called when all players have joined the game)

		if (clock != null)
			clock.start();
		
		//if (updateTimer == null) return;
		
		//if (updateTimer == null)  new Timer();
		if (updateTimer == null) updateTimer = new Timer();
		//if (updateTimer == null) new Timer();
		//if (updateTimer == null) new Timer();
		
		if (!running) {

			world.initialize(this);

			if (Main.DEBUG_MODE) {

				/////////////////////////////////////
				// FIXME: testing

				world.bases.get(0).model().ownerId(1);
				world.bases.get(0).model().militaryGarrison = 8;
				world.bases.get(1).model().ownerId(1);
				world.bases.get(1).model().militaryGarrison = 10;
				getBase(17).model().ownerId(2);
				getBase(17).model().militaryGarrison = 15;

//				getBase(17).model().ownerId(2);
//				getBase(17).model().militaryGarrison = 2;
//				getBase(2).model().ownerId(2);
//				getBase(2).model().militaryGarrison = .3;
//				getBase(17).ownerId(2);
//				getBase(17).model().militaryGarrison = 15;
//
//				deb_base( 2, 1, .5);
//				deb_base(11, 1, .5);
//				deb_base(17, 1, .5);
//				deb_base( 3, 1, .5);
//				deb_base(15, 1, .5);
//				deb_base(22, 1, .5);
//	
//				deb_base( 8, 2,  5);

				/////////////////////////////////////
			}

//			final double[] scores = new double[nbPlayers+1];
			
			final long launchTime = System.currentTimeMillis();
			
			updateTimer.schedule(new TimerTask() {
				@Override
				public void run() {

					if (current_frame/Settings.GAME_FRAMES_PER_SECOND < timeOut) {
						
						long time = System.currentTimeMillis();
						//System.out.println(1f/(float)(time-lastTime));
						fps = (int) (1000f / (float) (time - lastTime));
						lastTime = time;

						//System.out.println("A");
						update();
						Controller.get().update(Game.this);

						if (current_frame % GameSettings.TIME_UNITS_PER_FRAME == 0)
							world.takeSnapshot();

						/////////////////////////////////////
						// FIXME: testing
						if (current_frame % GameSettings.TIME_UNITS_PER_FRAME == 0) {
							int N = 0;
							for (int i = 0; i < N; i++) {
								double w = world.width, h = world.height;
								new GamePlane(Game.this, new Coord.Unique(Util.rand.nextDouble() * w, Util.rand.nextDouble() * h), 3, Type.MILITARY);
								new GamePlane(Game.this, new Coord.Unique(Util.rand.nextDouble() * w, Util.rand.nextDouble() * h), 4, Type.MILITARY);
							}
						}
						/////////////////////////////////////

						current_frame++;
						//throw new Error();
						//System.out.println("B");
						
						for (GameBase b: world.bases) {
							if(b.model().ownerId() < scores.length)
								scores[b.model().ownerId()] += 1; //b.model().militaryGarrison;
						}

					} else {
						
						// TODO: game ending
						
						log.info("Game ended after "+current_frame+" frames ("+timeOut+" theoretical seconds)."
								+" Real time spent: "+Math.round(((double)(System.currentTimeMillis()-launchTime))/100d)/10d+" seconds.");
//						log.info("Score ias :" + scores);
//						for (double score : scores)
						System.out.println();
						log.info("Scoreboard:");
						System.out.println("==============================");
						for (int i = 1; i < scores.length; i++)
							System.out.println("Player "+i+" ("+npm.getPlayer(i-1).getNickname()+"): "+scores[i]);
						System.out.println("==============================");
						System.out.println();
						
//						log.info("\n\nScore ias : \n" + scores + "\n\n");
						
//						updateTimer.cancel();
						stop();
						
//						npm.disconnect();
						
					}
					
				}
			}, update_period, update_period);
		}
		
		running = true;
	}
	
	public void stop() {
		running = false;
		
		//if (updateTimer == null) return;
		
		log.info("Stopping simulation");
		if (clock != null)
			clock.interruptCountdown();
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
	
	
//	public Scores getScores()
//	{
//		return scores;
//	}
	
	void update()
	{
		disp.flushEntities();
		
		synchronized (world)
		{
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
	
	
	public static class EntityNotFound extends RuntimeException {
		public EntityNotFound(String name, int id) {
			super("Cannot find "+name+" entity of id "+id);
		}
	};
	
	public GamePlane getPlane(int planeId) throws EntityNotFound {
		// FIXME use a hashmap instead
//		for (Entity<?> e: entities)
//			if (e instanceof Plane && ((Plane)e).id() == planeId)
//				return (Plane)e;
		for (GamePlane p: planes)
			if (p.id() == planeId)
				return p;
		
		//throw new Error("Not found"); // FIXME better exception
//		return null;
		throw new EntityNotFound("Plane", planeId);
	}
	public GameBase getBase(int baseId) throws EntityNotFound {
		// FIXME use a hashmap instead
		for (GameBase b: bases)
			if (b.id() == baseId)
				return b;
//		return null;
		throw new EntityNotFound("Base", baseId);
	}
	
	public Landable getLandable(int id) throws EntityNotFound {
		for (GameBase b: bases)
			if (b.id() == id)
				return b;
		for (GameCountry c: countries)
			if (c.id() == id)
				return c;
//		return null;
		throw new EntityNotFound("Landable", id);
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
//		return "Entities: "+world.entities.size()+" | FPS: "+fps+" | Seconds left: "+getTimeLeft()+"/"+timeOut;
		return "Entities: "+world.entities.size()+" | FPS: "+fps+" | Time: "+(current_frame/Settings.GAME_FRAMES_PER_SECOND)+" of "+timeOut+" seconds";
	}

	public World getWorld() {
		return world;
	}
	
	public long getTimeLeft()
	{
//		return clock.getRemainingTime();
		return timeOut - current_frame/Settings.GAME_FRAMES_PER_SECOND;
	}

	public GameCountry getCountryByAiId(int ai_id) {
		for (GameCountry c : countries)
			if (c.model().ownerId() == ai_id)
				return c;
		return null;
	}

	
	
}


















