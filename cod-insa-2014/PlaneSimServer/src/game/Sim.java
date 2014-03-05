package game;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import model.Coord;

import common.Accessors.RAccess;

import control.Controller;
import display.Displayer;

public class Sim {
	
	public final long update_period = 30;
	public final long world_snapshot_frame_period = 50;
	
	//private Controller stepUpdate;
	
	Displayer disp;
	private int nbPlayers;
	int current_frame = 0;
	public int getCurrentFrame() { return current_frame; }
	boolean running = false;
	public final World world;
	//final Timer updateTimer = new Timer();
	//final Timer updateTimer = null;
	Timer updateTimer = null;
	
	//public final Access<List<Entity>> entities = new BasicAccess(w.entities);
	public final RAccess<List<Entity<?>>> entities = new RAccess<List<Entity<?>>> () {
		public List<Entity<?>> get() {
			return Collections.unmodifiableList(world.entities);
		}
	};
	/*// compare to the simpler:
	public final List<Entity> getEntities() {
		return Collections.unmodifiableList(w.entities);
	}
	*/ 
	
	public Sim (Displayer disp, int nbplay) {
		this.disp = disp;
		
		this.nbPlayers = nbplay;
		//this.stepUpdate = new Controller(update_period);
		
		this.world = new World(this);    // [no more true:] also sets this.world... to avoid NPE..
		

		world.bases.add(new Base(this, new Coord.Unique(.1,.2)));
		world.bases.add(new Base(this, new Coord.Unique(.2,.5)));
		world.bases.add(new Base(this, new Coord.Unique(.7,.6)));
		
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
		
		//if (updateTimer == null) return;
		
		//if (updateTimer == null)  new Timer();
		if (updateTimer == null) updateTimer = new Timer();
		//if (updateTimer == null) new Timer();
		//if (updateTimer == null) new Timer();
		
		if (!running)
			updateTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					//System.out.println("A");
					update();
					Controller.get().update(Sim.this);
					
					if (current_frame%world_snapshot_frame_period == 0)
						world.takeSnapshot();
						
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
		
		System.out.println("Stopping simulation");
		
		updateTimer.cancel();
		updateTimer.purge();
		
		//updateTimer = null;
		//System.gc();
	}
	
	void addEntity (Entity<?> e) {
		world.entities.add(e);
		//if (disp != null)
		disp.addEntity(e);
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
	public List<Entity<?>> _debug_backdoor() { // FIXME
		return world.entities;
	}
	
	
	void update() {
		world.update();
		
	}
	
	public Plane getPlane(int planeId) {
		// FIXME use a hashmap instead
		for (Entity<?> e: entities.get())
			if (e instanceof Plane && ((Plane)e).id == planeId)
				return (Plane)e;
		//throw new Error("Not found"); // FIXME better exception
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

	public World getW() {
		return world;
	}
	
	
	
}







