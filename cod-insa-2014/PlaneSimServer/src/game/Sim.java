package game;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import common.Accessors.RAccess;

import control.SimStepUpdater;
import display.Displayer;

public class Sim {
	
	public final long update_period = 30;
	
	private SimStepUpdater stepUpdate;
	
	Displayer disp;
	private int nbPlayers;
	int current_frame = 0;
	public int getCurrentFrame() { return current_frame; }
	boolean running = false;
	World w;
	
	//public final Access<List<Entity>> entities = new BasicAccess(w.entities);
	public final RAccess<List<Entity<?>>> entities = new RAccess<List<Entity<?>>> () {
		public List<Entity<?>> get() {
			return Collections.unmodifiableList(w.entities);
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
		this.stepUpdate = new SimStepUpdater(update_period);
		
		new World(this); // sets this.w
		
		new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
            	update();
            	SimStepUpdater.get().update(Sim.this);	//FIXME don't do that here, at each frame!
            	current_frame++;
            }
        }, update_period, update_period);
		
	}
	
	public void start()
	{
		//TODO (called when all players have joined the game)
		running = true;
	}
	
	public void stop() {
		running = false;
	}
	
	void addEntity (Entity<?> e) {
		w.entities.add(e);
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
		return w.entities;
	}
	
	
	void update() {
		w.update();
		
	}
	
	public Plane getPlane(int planeId) {
		// FIXME use a hashmap instead
		for (Entity<?> e: entities.get())
			if (e instanceof Plane && ((Plane)e).id == planeId)
				return (Plane)e;
		throw new Error("Not found"); // FIXME better exception
	}

	
	public SimStepUpdater getSetpUpdater() {
		return stepUpdate;
	}

	public int getNbPlayers() {
		return nbPlayers;
	}

	public World getW() {
		return w;
	}
	
	
	
}







