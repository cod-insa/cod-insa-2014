package Global;
import java.util.ArrayList;

import Enums.GoalType;
import Enums.OrderType;
import Enums.Priority;
import Interfaces.toContinuous;
import Model.Model;
import Model.Node;


public class Continuous implements toContinuous {
	ArrayList<Node> mBaseToSupply;
	private PoolManager mPoolManager;
	ArrayList<Goal> mCreatedGoal;
	
	public Continuous(PoolManager poolManager) {
		mPoolManager = poolManager;
		mCreatedGoal = new ArrayList<Goal>();
		mBaseToSupply = new ArrayList<>();
	}

	public void update() {
		
//		// Traite les besoins de ravitaillement lointains
//		if(!mBaseToSupply.isEmpty()) 
//		{
//			for(int i = 0; i < mBaseToSupply.size(); i++) {
//				ArrayList<Goal> baseGoals = new ArrayList<>();
//				for (Goal g : mCreatedGoal) {
//					if (g.getType() == GoalType.COUNTRY_FOREIGN_RESSOURCES) {
//						baseGoals.add(g);
//					}
//				}
//				if (baseGoals.isEmpty()) {
//					Goal g = new Goal(mBaseToSupply.get(i), GoalType.COUNTRY_FOREIGN_RESSOURCES, Priority.HIGH);
//					// Par défaut on demande un avion pour ce genre de tâches
//					ArrayList<Plane> planes = mPoolManager.askForNPlanes(g, 1);
//					System.out.println("Ajout de la tâche, nbAlloués : " + planes.size());
//					if(!planes.isEmpty()) {
//						for (Plane p : planes)
//							g.addAllocatedPlane(p);
//						mCreatedGoal.add(g);
//						baseGoals.add(g);
//					}
//				}
//				else {
//					for (Goal g : baseGoals) {
//						boolean removable = true;
//						for (Plane p : g.getAllocatedPlanes()) {
//							if (p.getOrder().getType() != OrderType.WAITING_FOR_ORDER &&
//								p.getOrder().getType() != OrderType.NO_ORDER &&
//								p.isAlive()) {
//								removable = false;
//								
//							}
//						}
//						if (removable) {
//							System.out.println("Remove base");
//							mBaseToSupply.remove(g.getBase());
//							mCreatedGoal.remove(g);
//						}
//					}
//				}
//			}
//		}
		
		Goal ravitaillement = null;
		// Recherche du goal de ravitaillement global
		for (Goal g : mCreatedGoal) {
			if (g.getType() == GoalType.COUNTRY_CLOSE_RESSOURCES) {
				ravitaillement = g;
			}
		}
		if (ravitaillement == null) {
			Model model = Model.getInstance();
            Node target = model.getMap().getFirstBase();
            ravitaillement = new Goal(target, GoalType.COUNTRY_CLOSE_RESSOURCES, Priority.MEDIUM);
            mCreatedGoal.add(ravitaillement);
		}
		// Envoi de tous les autres avions de la supply pool sur le ravitaillement de base
		Integer nbRemainingPlanes = mPoolManager.getNumberOfAvailablePlanes(ravitaillement);
		System.out.println("Nb available : " + nbRemainingPlanes);

		if (nbRemainingPlanes > 0) {
			ArrayList<Plane> planes = mPoolManager.askForNPlanes(ravitaillement, nbRemainingPlanes/10);
			System.out.println("Nb planes affected : " + planes.size());
			for (Plane p : planes) {
				ravitaillement.addAllocatedPlane(p);
			}
		}
	}
	
	public void processObjectives() {
		// Envoi des objectifs aux avions
		for (Goal g : mCreatedGoal) {
			ArrayList<Plane> planes = g.getAllocatedPlanes();
			for (Plane p : planes) {
				if (p.isAlive()) {
					if (p.getOrder().getType() == OrderType.WAITING_FOR_ORDER || 
						p.getOrderPriority().ordinal() < g.getPriority().ordinal()) {
						switch (g.getType()) {
							case COUNTRY_CLOSE_RESSOURCES:
							case COUNTRY_FOREIGN_RESSOURCES:
								p.supplyBase(g.getBase(), g.getPriority());
								break;
							case ATTACK:
								break;
							case DEFEND:
								break;
							case MOVING_RESSOURCES:
								break;
							default:
								break;
						}

					}
					p.update();
				}
			}
		}
	}

	public void addBaseToSupply(Node base) {
		mBaseToSupply.add(base);
	}
	
	public ArrayList<Node> getBaseToSupply() {
		return mBaseToSupply;
	}
}
