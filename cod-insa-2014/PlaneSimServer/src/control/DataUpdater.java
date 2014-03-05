package control;

import game.World.Snapshot;
import genbridge.Data;
import players.NetworkPlayerManager;


/*
 * A data updater by player
 * Every second, it updates data for this team
 * and then, allows the server handle to sent it to the client
 */
public class DataUpdater { // FIXME rename DataPreparer?
	
	private NetworkPlayerManager pManager; 
	private Data tobeSent;

	public DataUpdater(NetworkPlayerManager pm)
	{
		pManager = pm;
	}

	public void prepareData(Snapshot snapshot)
	{
//		this.tobeSent = new Data();
//		this.tobeSent.numFrame = snapshot.id;
//		this.tobeSent.bases = new ArrayList<BaseData>();
//		this.tobeSent.planes = new ArrayList<PlaneData>();
//		
//		for (BaseModel.View b : snapshot.bases.view) // Convert game model objects to thrift objects
//			tobeSent.bases.add(new BaseData(b.id(), new CoordData(b.position.x(),b.position.y())));
//		for (PlaneModel.View p : snapshot.planes.view)
//			// FIXME Fix Energy, gaz, action performed, etc...
//			tobeSent.planes.add(
//					new PlaneData(p.id(), 
//					new CoordData(p.position.x(),p.position.y()), 
//					-1, -1, -1, false)); // FIXME default values to make it work
//		
//		pManager.dataReady(); // Notify Player (Not sure usefull, players could be notified outside of DataUpdater
	}
	
	public Data getData() {
		
		// FIXME: no need for deep copying, since no one modifies this data!
		
		Data d = new Data();
		synchronized (tobeSent) {
			d = tobeSent.deepCopy();
		}
		return d;
	}

}







