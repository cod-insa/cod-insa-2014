package network;

import game.Game;
import game.World.Snapshot;
import genbridge.BaseBasicData;
import genbridge.BaseFullData;
import genbridge.BaseInitData;
import genbridge.CoordData;
import genbridge.CountryInitData;
import genbridge.Data;
import genbridge.InitData;
import genbridge.PlaneBasicData;
import genbridge.PlaneFullData;
import genbridge.PlaneStateData;
import genbridge.ProgressAxisData;
import genbridge.ProgressAxisInitData;

import java.util.ArrayList;
import java.util.List;

import model.Base;
import model.MaterialEntity;
import model.Plane;

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
		InitData tobeSent = null;
		
		
		if (snapshot.hasSome())
		{
			// Init
			tobeSent = new InitData();
			tobeSent.bases = new ArrayList<BaseInitData>();
			tobeSent.progressAxis = new ArrayList<ProgressAxisInitData>();
			tobeSent.othersCountry = new ArrayList<CountryInitData>();
			
			// Fill init bases
			
			for (Base.View b : snapshot.get().bases.view)
				tobeSent.bases.add(new BaseInitData(b.id(),new CoordData(b.position().x(),b.position().y())));
			
			// Fill map dimensions 
			
			tobeSent.mapWidth = (int)snapshot.get().width;
			tobeSent.mapHeight = (int)snapshot.get().height;
			
			// Fill progress Axis
			
			// FIXME No axis in the snapshot
			/*
			for (ProgressAxis.View a : snapshot.get().axis.view)
				tobeSent.progressAxis.add(new ProgressAxisInitData(a.id(),a.base1().id(),a.base2().id()));
			//*/
			
			// Fill the AI's country
			
			// TODO
			
			// Fill the others' AIs country
			
			// TODO
			
			// Fill the initial money of the AI
			
			// TODO
		}
		else
			Game.log.warn("It seems like the init snapshot is null. The AI won't receibe any initial data");
		
		return tobeSent;
	}
	
	// Could be improved a little if we know all the plane that belong the AI or not
	public static Data prepareData(Snapshot snapshot, int ai_id)
	{
		// Init
		Data tobeSent;
		tobeSent = new Data();
		tobeSent.numFrame = snapshot.id;
		tobeSent.owned_bases = new ArrayList<BaseFullData>();
		tobeSent.not_owned_bases = new ArrayList<BaseBasicData>();
		tobeSent.owned_planes = new ArrayList<PlaneFullData>();
		tobeSent.not_owned_planes = new ArrayList<PlaneBasicData>();
		tobeSent.progressAxis = new ArrayList<ProgressAxisData>();
		
		List<MaterialEntity.View> ai_entities = new ArrayList<>();
		
		// Fill the owned bases
		
		for (Base.View b : snapshot.bases.view) // Convert game model objects to thrift objects
			if (b.ownerId() == ai_id) // This is the base of the ai so we show the planes at the base
			{
				ai_entities.add(b);
				tobeSent.owned_bases.add(new BaseFullData(new BaseBasicData(b.id(), b.ownerId()),b.militaryGarrison(),b.fuelInStock()));
			}
		
		// Fill the owned planes
		
		for (Plane.FullView p : snapshot.planes.view)
			if (p.ownerId() == ai_id)
			{
				int baseId = -1;
				if (p.curBase() != null)
					baseId = p.curBase().id();
				if (p.state() == Plane.State.AT_AIRPORT)
					baseId = p.curBase().id();
				
				tobeSent.owned_planes.add(
					new PlaneFullData(
						new PlaneBasicData(p.id(), 
							new CoordData(p.position().x(),p.position().y()), 
							p.ownerId(), p.health(), 
							p.canAttack(), p.type.id
						), 
						baseId, 
						p.fuelInTank(), 
						DataStateConverter.make(p.state()),
						p.militaryInHold(),
						p.fuelInHold()
					)
				);
				
				ai_entities.add(p);
			}
		
		// Fill the not owned bases
		
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
				tobeSent.not_owned_bases.add(new BaseBasicData(b.id(),baseID));
			}
		
		// Fill the not owned planes 
		
		for (Plane.FullView p : snapshot.planes.view) // For each plane 
			if (p.ownerId() != ai_id) // That is not belonging to the ai 
				for (MaterialEntity.View e : ai_entities)
					if (e.canSee(p)) // If the ai can see it, we give it to her
					{
						tobeSent.not_owned_planes.add(
								new PlaneBasicData(p.id(), 
								new CoordData(p.position().x(),p.position().y()), 
								p.ownerId(), p.health(),p.canAttack(),p.type.id));
						break; // So we have given the plane, we can go the next one
					}

		// Fill the progressAxis
		
		// FIXME need ratio1, ratio2 in ProgressAxis and axis in snapshot
		/*
		for (ProgressAxis a : snapshot.axis.view)
			tobeSent.progressAxis.add(new ProgressAxisData(a.id(),a.ratio1(), a.ratio2()));
		*/
		
		// Fill the country data

		//TODO
		
		// Fill the currentMoney data
			
		//TODO
		
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







