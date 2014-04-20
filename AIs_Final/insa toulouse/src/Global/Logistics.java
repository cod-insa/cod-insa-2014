package Global;

import java.util.ArrayList;

import model.Plane.FullView;
import proxy.Proxy;
import Enums.Behaviour;
import Model.Model;

import common.MapView;

public class Logistics {
	private Behavioral behavioralDefend;
	private BehavioralConquest behavioralConquest;
	private Continuous continuous;
	private PoolManager poolManager;
	private Integer lastPlaneId;
	private static Proxy game;
	private ArrayList<Integer> debugList;
	
	public Logistics() {
		this.poolManager = new PoolManager();
		this.behavioralDefend = new Behavioral(Behaviour.DEFENSIVE,this.poolManager);
		this.behavioralConquest = new BehavioralConquest(Behaviour.EXPANSIVE, this.poolManager);
		this.continuous = new Continuous(this.poolManager);
		this.lastPlaneId = -1;
		game = null;
	}
	
	public void update(Proxy game) {
		Logistics.game = game;
		
		/// Maj du modèle
		Model model = Model.getInstance();
		// La première fois, envoie les coordonnées de la base
		if (model.getMap().getCountry() == null) {
			model.getMap().setCountry(game.getCountry());
		}
		// Envoie les modifs de la map en termes de bases
		model.updateMap(game.getAllBases(), game.getVisibleBase());
		
		/// Maj du pool manager : 
		// 1° Envoie les nouveaux avions
		Plane newPlane = null;
		//if (game.getMyPlanes().valuesView().size() > this.lastPlaneId) {
			MapView<Integer, model.Plane.FullView> array = game.getMyPlanes();
			int max=-1;
			for(Integer i : array.keySetView())			
			{
				if (i>max)
					max=i;
			}
			
			if(max>-1 && max > lastPlaneId)
			{
				newPlane = new Plane(array.get(max));
				lastPlaneId = max;
			}
		// 2° Envoie les avions tués

		ArrayList<Integer> listOfKilled = new ArrayList<>();
		for (FullView v : game.getKilledPlanes().valuesView()) {
			listOfKilled.add(v.id());
		}
		this.poolManager.updatePools(newPlane, listOfKilled);
		
		/// Maj du comportement
		((BehavioralConquest)this.behavioralConquest).update();
		((BehavioralConquest)this.behavioralConquest).processObjectives();

		//((BehavioralConquest)this.behavioralConquest).update();
		//behavioralConquest.processGoals();
		
//		if (this.continuous.getBaseToSupply().isEmpty()) {
//			this.continuous.addBaseToSupply(model.getMap().getBasicBase(6));
//			
//		}
		//Maj behaviour
		//this.behavioral.update();
		//this.behavioral.processGoals();
		this.behavioralDefend.update();
		this.behavioralDefend.processGoals();
		
		/// Maj du continu
		this.continuous.update();
		
		/// Process Objectives continu
		this.continuous.processObjectives();
		
		/// Process Objectives comportement
		// TODO
	}
	
	public static Proxy getGame() {
		return game;
	}
}
