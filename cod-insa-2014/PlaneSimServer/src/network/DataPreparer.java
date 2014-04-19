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
import genbridge.RequestData;

import java.util.ArrayList;
import java.util.List;

import model.Base;
import model.Country;
import model.Country.Request;
import model.MaterialEntity;
import model.Plane;
import model.ProgressAxis;

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
	public static InitData prepareInitData(Nullable<Snapshot> snapshot, int ai_id)
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
			
			for (Base.FullView b : snapshot.get().bases.view)
				tobeSent.bases.add(new BaseInitData(b.id(),new CoordData(b.position().x(),b.position().y())));
			
			// Fill map dimensions 
			
			tobeSent.mapWidth = (int)snapshot.get().width;
			tobeSent.mapHeight = (int)snapshot.get().height;
			
			// Fill progress Axis
			
			for (ProgressAxis.View pa : snapshot.get().axes.view)
				tobeSent.progressAxis.add(new ProgressAxisInitData(pa.id(),pa.base1().id(),pa.base2().id()));
			
			// Fill the AI's country

			for (Country.View c : snapshot.get().countries.view)
				if (c.ownerId() == ai_id)
					tobeSent.myCountry = new CountryInitData(c.id(), new CoordData(c.position.x(),c.position.y()));
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
		tobeSent.not_owned_visible_bases = new ArrayList<BaseFullData>();
		tobeSent.not_owned_not_visible_bases = new ArrayList<BaseBasicData>();
		tobeSent.owned_planes = new ArrayList<PlaneFullData>();
		tobeSent.not_owned_planes = new ArrayList<PlaneBasicData>();
		tobeSent.progressAxis = new ArrayList<ProgressAxisData>();
		tobeSent.productionLine = new ArrayList<RequestData>();
		
		List<MaterialEntity.View> ai_entities = new ArrayList<>();
		
		// Fill the owned bases
		
		for (Base.FullView b : snapshot.bases.view) // Convert game model objects to thrift objects
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
				if (p.curBase() != null) {
					baseId = p.curBase().id();
//				if (p.state() == Plane.State.AT_AIRPORT)
//					baseId = p.curBase().id();
				}
//				assert p.curBase() == null || p.state() == Plane.State.AT_AIRPORT;
				assert (p.curBase() != null) == (p.state() == Plane.State.AT_AIRPORT);
				
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
		
		for (Base.FullView b : snapshot.bases.view)
			if (b.ownerId() != ai_id) // The base is not belonging to the current AI
			{
				boolean isSeen = false;
				int baseID = 0; // By default, let's  say this base is neutral
				for (int i = 0; i < ai_entities.size() && !isSeen;i++) // If the AI don't see it, we display the base as neutral
					if (ai_entities.get(i).canSee(b))
					{
						baseID = b.ownerId(); // The AI see it, so we show the true value of the owner
						isSeen = true;
					}
				
				if (isSeen) // The base is seen, so we put it in the visible bases
					tobeSent.not_owned_visible_bases.add(new BaseFullData(new BaseBasicData(b.id(), b.ownerId()),b.militaryGarrison(),b.fuelInStock()));
				else
					tobeSent.not_owned_not_visible_bases.add(new BaseBasicData(b.id(),b.ownerId()));
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
		
		for (ProgressAxis.View a : snapshot.axes.view)
			tobeSent.progressAxis.add(new ProgressAxisData(a.id(),a.ratio1(), a.ratio2()));
		
		
		// Fill the production line data

		for (Country.View c : snapshot.countries.view)
			if (c.ownerId() == ai_id)
				for (Request.View r : c.productionLine().valuesView())
				{
					Game.log.debug("Request " + r.rqId() + " : " + r.timeBeforePlaneBuilt());
					tobeSent.productionLine.add(new RequestData(r.rqId(),r.timeBeforePlaneBuilt(),r.requestedType().id));
				}
		
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







