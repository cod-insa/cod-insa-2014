package control;

import game.World.Snapshot;
import genbridge.BaseData;
import genbridge.CoordData;
import genbridge.Data;
import genbridge.PlaneData;

import java.util.ArrayList;

import model.BaseModel;
import model.PlaneModel;
import players.PlayerManager;


/*
 * A data updater by player
 * Every second, it updates data for this team
 * and then, allows the server handle to sent it to the client
 */
public class DataUpdater{

	private PlayerManager pManager;
	private Data tobeSent;

	public DataUpdater(PlayerManager pm)
	{
		pManager = pm;
	}

	public void prepareData(Snapshot snapshot)
	{
		this.tobeSent = new Data();
		this.tobeSent.numFrame = snapshot.id;
		this.tobeSent.bases = new ArrayList<BaseData>();
		this.tobeSent.planes = new ArrayList<PlaneData>();
		
		for (BaseModel.View b : snapshot.bases.view) // Convert game model objects to thrift objects
			tobeSent.bases.add(new BaseData(b.id(), new CoordData(b.position.x(),b.position.y())));
		for (PlaneModel.View p : snapshot.planes.view)
			// FIXME Fix Energy, gaz, action performed, etc...
			tobeSent.planes.add(
					new PlaneData(p.id(), 
					new CoordData(p.position.x(),p.position.y()), 
					-1, -1, -1, false)); // default values to make it work
		
		pManager.dataReady(); // Notify Player
	}
	
	public Data getData() {
		
		Data d = new Data();
		synchronized (tobeSent) {
			d = tobeSent.deepCopy();
		}
		return d;
	}

}


