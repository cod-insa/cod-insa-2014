package control;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import players.Player;
import game.Entity;
import game.Sim;
import genbridge.Coord;
import genbridge.Data;
import genbridge.Base;
import genbridge.Plane;


/*
 * A data updater by player
 * Every second, it updates data for this team
 * and then, allows the server handle to sent it to the client
 */
public class DataUpdater extends Thread{

	private long period = 1000;	//milliseconds
	private Sim simul;
	private Player p;
	private int frameNum = 0;

	private Data tobeSent;

	private boolean working;

	/**
	 * 
	 * @param period, by default a second
	 */
	public DataUpdater(Sim simul, Player player){
		this.simul = simul;
		this.p = player;
		this.working = false;

		this.tobeSent = new Data();
		this.tobeSent = new Data();
		this.tobeSent.numFrame = frameNum++;
		this.tobeSent.bases = new ArrayList<Base>();
		this.tobeSent.planes = new ArrayList<Plane>();
	}

	@Override
	public void run() {
		super.run();
		working = true;

		while(working)
		{
			
			try {
				sleep(period);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			synchronized (simul.getW().getEntities()) {
				synchronized (tobeSent) {


					List<Entity<?>> entities = simul.getW().getEntities();
					for (Entity<?> entity : entities) {

						//Updating planes
						if(entity instanceof game.Plane)
						{
							
							Plane p = new Plane();
							p.setPlane_id(entity.id);
							p.setAi_id(0);			//FIXME
							p.setEnergy(0); 		//FIXME
							p.setGaz(0);			//FIXME
							Coord c = new Coord();
							c.longit = 0;			//FIXME
							c.latid = 0;			//FIXME
							p.setPosit(c);
							
							tobeSent.planes.add(p);
						}  
						
						//Updating bases
						if(entity instanceof game.Base)
						{
							Base b = new Base();
							b.setBase_id(entity.id);
							Coord c = new Coord();
							c.longit = 0;			//FIXME
							c.latid = 0;			//FIXME
							b.setPosit(c);
							//FIXME add ID Team that own this base
						}
						
					}
				}
			}
			// Data is ready, tell the player to take it 
			p.getWaitData().notify();

		}
	}

	public void stopWorking()
	{
		working = false;
	}

	public Data getTobeSent() {
		Data d = new Data();
		synchronized (tobeSent) {
			d = tobeSent.deepCopy();
		}
		return d;
	}

}


