package game;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import common.Accessors.RAccess;

import display.Displayer;

public class Sim {
	
	public final long update_period = 30;
	
	Displayer disp;
	
	int current_frame = 0;
	
	boolean running = true;
	
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
	
	public Sim (Displayer disp) {
		this.disp = disp;
		new World(this); // sets this.w
		
		new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
            	update();
            }
        }, update_period, update_period);
		
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
	

}







