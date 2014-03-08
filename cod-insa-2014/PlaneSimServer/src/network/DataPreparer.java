package network;

import game.World.Snapshot;
import genbridge.BaseData;
import genbridge.BaseInitData;
import genbridge.CoordData;
import genbridge.Data;
import genbridge.InitData;
import genbridge.PlaneData;
import genbridge.PlaneStateData;

import java.util.ArrayList;

import model.BaseModel;
import model.PlaneModel;

import common.Nullable;


/*
 * A data updater by player
 * Every second, it updates data for this team
 * and then, allows the server handle to sent it to the client
 */
public abstract class DataPreparer {

	/**
	 * Getting a null snapshot means the client is disconnecting or being disconnected (eg: game shutting down)
	 */
	public static InitData prepareInitData(Nullable<Snapshot> snapshot)
	{
		InitData tobeSent = new InitData();
		tobeSent.bases = new ArrayList<BaseInitData>();
		
		if (snapshot.hasSome())
		{
			for (BaseModel.View b : snapshot.get().bases.view)
				tobeSent.bases.add(new BaseInitData(b.id(),new CoordData(b.position().x(),b.position().y())));
		}
		
		return tobeSent;
	}
	
	public static Data prepareData(Snapshot snapshot)
	{
		Data tobeSent;
		tobeSent = new Data();
		tobeSent.numFrame = snapshot.id;
		tobeSent.bases = new ArrayList<BaseData>();
		tobeSent.planes = new ArrayList<PlaneData>();
		
		for (BaseModel.View b : snapshot.bases.view) // Convert game model objects to thrift objects
			tobeSent.bases.add(new BaseData(b.id())); // For now, it does nothing, but it will...
		for (PlaneModel.View p : snapshot.planes.view)
			// FIXME Fix gaz and ai_id
			tobeSent.planes.add(
					new PlaneData(p.id(), 
					new CoordData(p.position().x(),p.position().y()), 
					p.ownerId(), p.health(), -1, DataStateConverter.make(p.state())));
		
		//System.out.println(">> ("+tobeSent.bases.get(0).base_id+")"+tobeSent.bases.get(0).posit.latid);
		
		return tobeSent;
	}
	
	public static Data prepareEndofGame()
	{
		Data tobeSent;
		tobeSent = new Data();
		tobeSent.numFrame = -1;
//		tobeSent.bases = new ArrayList<BaseData>();
//		tobeSent.planes = new ArrayList<PlaneData>();
		return tobeSent;
	}
	public static class DataStateConverter {
		public static PlaneStateData make(PlaneModel.State s)
		{
			PlaneStateData psd = null;
			switch (s)
			{
			case AT_AIRPORT:
				psd = PlaneStateData.AT_AIRPORT;
				break;
			case ATTACKING:
				psd = PlaneStateData.ATTACKING;
				break;
			case IDLE:
				psd = PlaneStateData.IDLE;
				break;
			case MOVING:
				psd = PlaneStateData.GOING_TO;
				break;
			case DEAD: 
				psd = PlaneStateData.DEAD;
				break;
			case FOLLOWING:
				psd = PlaneStateData.FOLLOWING;
				break;
			}
			return psd;
		}
	}
}







