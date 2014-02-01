package model;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Sim {
	
	public final long update_period = 30;
	
	int current_frame = 0;
	
	boolean running = true;
	
	World w;
	
	public Sim() {
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
	
	void addEntity (Entity e) {
		w.entities.add(e);
	}
	
	public List<Entity> getEntities() {
		return Collections.unmodifiableList(w.entities);
	}
	public List<Entity> _debug_backdoor() { // FIXME
		return w.entities;
	}
	
	
	void update() {
		w.update();
		
	}
	

}







