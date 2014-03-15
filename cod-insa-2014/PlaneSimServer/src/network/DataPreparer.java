package network;

import game.World;
import game.World.Snapshot;
import genbridge.BaseData;
import genbridge.BaseInitData;
import genbridge.CoordData;
import genbridge.Data;
import genbridge.InitData;
import genbridge.PlaneData;
import genbridge.PlaneStateData;

import java.util.ArrayList;
import java.util.List;

import model.Base;
import model.Entity;
import model.Entity.View;
import model.Plane;
import common.ListView;
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
			for (Base.View b : snapshot.get().bases.view)
				tobeSent.bases.add(new BaseInitData(b.id(),new CoordData(b.position().x(),b.position().y())));
			
			tobeSent.mapWidth = snapshot.get().width;
			tobeSent.mapHeight = snapshot.get().height;
		}
		
		return tobeSent;
	}
	
	public static Data prepareData(Snapshot snapshot, int ai_id)
	{
		Data tobeSent;
		tobeSent = new Data();
		tobeSent.numFrame = snapshot.id;
		tobeSent.bases = new ArrayList<BaseData>();
		tobeSent.planes = new ArrayList<PlaneData>();
		
		List<Entity.View> ai_entities = new ArrayList<Entity.View>();
		
		for (Base.View b : snapshot.bases.view) // Convert game model objects to thrift objects
		{
			List<Integer> id_planes = new ArrayList<Integer>();
			if (b.ownerId() == ai_id) // This is the base of the ai so we show the planes at the base
			{
				ai_entities.add(b);
				for (Plane.View p : b.getPlanes())
					id_planes.add(p.id());
			}
			tobeSent.bases.add(new BaseData(b.id(),id_planes,b.ownerId()));
		}
		
		
		for (Plane.View p : snapshot.planes.view)
			if (p.ownerId() == ai_id)
			{
				// FIXME Fix gaz 
				tobeSent.planes.add(
						new PlaneData(p.id(), 
						new CoordData(p.position().x(),p.position().y()), 
						p.ownerId(), p.health(), -1, DataStateConverter.make(p.state())));
				ai_entities.add(p);
			}
		
		for (Plane.View p : snapshot.planes.view) // For each plane 
			if (p.ownerId() != ai_id) // That is not belonging to the ai 
				if (Entity.isVisibleByEntities(ai_entities, p.position)) // And which are visible
					// FIXME Fix gaz
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
		public static PlaneStateData make(Plane.State s)
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
			case GOING_TO:
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







