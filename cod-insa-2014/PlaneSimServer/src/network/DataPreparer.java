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
			
			tobeSent.mapWidth = (int)snapshot.get().width;
			tobeSent.mapHeight = (int)snapshot.get().height;
		}
		
		return tobeSent;
	}
	
	// Could be improved a little if we know all the plane that belong the AI or not
	public static Data prepareData(Snapshot snapshot, int ai_id)
	{
		Data tobeSent;
		tobeSent = new Data();
		tobeSent.numFrame = snapshot.id;
		tobeSent.bases = new ArrayList<BaseData>();
		tobeSent.planes = new ArrayList<PlaneData>();
		
		List<Entity.View> ai_entities = new ArrayList<Entity.View>();
		

		// We search every planes that belong to the ai
		for (Base.View b : snapshot.bases.view) // Convert game model objects to thrift objects
		{
			if (b.ownerId() == ai_id) // This is the base of the ai so we show the planes at the base
			{
				ai_entities.add(b);
				tobeSent.bases.add(new BaseData(b.id(),b.ownerId(),0,0)); // FIXME Fix the ressources
			}
		}
		
		// Then we search every planes that belong to the ai
		for (Plane.FullView p : snapshot.planes.view)
			if (p.ownerId() == ai_id)
			{
				int baseId = -1;
				if (p.curBase() != null)
					baseId = p.curBase().id();
				// FIXME Fix gaz 
				tobeSent.planes.add(
						new PlaneData(p.id(), 
						new CoordData(p.position().x(),p.position().y()), 
						p.ownerId(), p.health(), baseId, -1, DataStateConverter.make(p.state()),-1,-1));// FIXME Fix the ressources
				ai_entities.add(p);
			}
		
		// We look for every bases that are not seen by the AI
		for (Base.View b : snapshot.bases.view)
			if (b.ownerId() != ai_id) // The base is not belonging to the current AI
			{
				int baseID = 0; // By default, let's  say this base is neutral
				if (b.ownerId() != 0) // If the base is not neutral, we need to know if the AI see it
				{
					boolean isSeen = false;
					for (int i = 0; i < ai_entities.size() && !isSeen;i++) // If the AI don't see it, we display the base as neutral
						if (ai_entities.get(i).canSee(b))
						{
							baseID = b.ownerId(); // The AI see it, so we show the true value of the owner
							isSeen = true;
						}
				}
				tobeSent.bases.add(new BaseData(b.id(),baseID,0,0)); // FIXME Fix the ressources
			}
		
		
		
		for (Plane.FullView p : snapshot.planes.view) // For each plane 
			if (p.ownerId() != ai_id) // That is not belonging to the ai 
				for (Entity.View e : ai_entities)
					if (e.canSee(p))
					{
						int baseId = -1;
						if (p.curBase() != null)
							baseId = p.curBase().id();
						// FIXME Fix gaz
						tobeSent.planes.add(
								new PlaneData(p.id(), 
								new CoordData(p.position().x(),p.position().y()), 
								p.ownerId(), p.health(), baseId, -1, DataStateConverter.make(p.state()),0,0));// FIXME Fix the ressources
						break;
					}
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
			case LANDING:
				psd = PlaneStateData.LANDING;
				break;
			default:
				throw new Error("Unrecognized State");
			}
			return psd;
		}
	}
}







