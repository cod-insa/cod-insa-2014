package Global;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

import model.ProgressAxis.Oriented;
import Enums.Behaviour;
import Model.Map;
import Model.Model;
import Model.Node;

import common.SetView;

public class BehavioralConquest extends Behavioral{
	private ArrayList<Node> mSafeNodes;
	private ArrayList<Node> mFrontierNodes;
	private ArrayList<Node> mGoalNodes;
	
	public BehavioralConquest(Behaviour defaultBehaviour, PoolManager pool) {
		super(defaultBehaviour, pool);
		System.out.println("Création du comportement de conquête");
		mSafeNodes = new ArrayList<Node>();
		mFrontierNodes = new ArrayList<Node>();
		mGoalNodes = new ArrayList<Node>();
	}
	
	@Override
	public void update() {
		Common.myId = Logistics.getGame().getMyPlanes().valuesView().iterator().next().ownerId();
		if(this.getGoalList().size()<=3)
		{
			// Si on a pas d'objectifs en cours, on s'en fixe un suivant la stratégie de conquête
			//TODO : trouver comment déterminer le joueur ID
			System.out.println("New strategic goal -------------------------------------------------------------------");
			getLists( Logistics.getGame().getMyPlanes().valuesView().iterator().next().ownerId());
			System.out.println("ID du joueur : "+ Logistics.getGame().getMyPlanes().valuesView().iterator().next().ownerId());
			sortLists(); 
			executeStartegy(); 
		}
		// Une fois qu'un objectif est fix� et que des avions y sont attribués, on les actualise
		// TODO : pour l'instant, on ne traite les objecifs que 1 par 1
		if(!this.getGoalList().isEmpty())
		{
			for(int i=this.getGoalList().size()-1; i>=0; i--)
			{
				for(Plane p : this.getGoalList().get(i).getAllocatedPlanes())
				{
					//System.out.println("Update d'un avion dédié à la stratégie de conquête");
					if(p.isAlive())
						p.update();
				}
				if(this.getGoalList().get(i).getBase().getBase().ownerId() == Logistics.getGame().getMyPlanes().valuesView().iterator().next().ownerId())
				{
					this.getGoalList().remove(i);
				}
			}
		}
	}
	
	void getLists(int country) {
		System.out.println("Construction des listes d'objectifs dans la stratégie pour le joueur "+country);
		Map map = Model.getInstance().getMap();
		
		PriorityQueue<NodeState> queue = new PriorityQueue<>(1, new Comparator<NodeState>() {
			@Override public int compare(NodeState o1, NodeState o2) { return 1; } // insertion toujours � la fin
		});
		queue.add(new NodeState(map.getFirstBase().getBase().id(), 0));
		
		TreeSet<Integer> nodeDone = new TreeSet<>();
		
		// on vide les listes
		mSafeNodes.clear();
		mFrontierNodes.clear();
		mGoalNodes.clear();
		
		// profondeur de la zone non conquise la plus proche
		int depthMinNotCompleted = -1;
		
		while(!queue.isEmpty()) {
			NodeState ns = queue.poll();
			
			// noeud d�j� parcouru
			if(nodeDone.contains(ns.nodeID))
				continue;
			// on note le noeud comme parcouru
			nodeDone.add(ns.nodeID);
			map.getBasicBase(ns.nodeID).setDepth(ns.depth);
			
			
			// noeud � conqu�rir
			//if(map.getFullBase(ns.nodeID).getIdPlayer() != country)
			if(map.getBasicBase(ns.nodeID).getIdPlayer() != country)
			{
				// noeud non poss�d� de profondeur minimal
				if(ns.depth == depthMinNotCompleted || depthMinNotCompleted == -1) {
					depthMinNotCompleted = ns.depth;
					mGoalNodes.add(map.getBasicBase(ns.nodeID));
				}
			}
			// Noeud d�j� conquis
			else {
				System.out.println("Le noeud "+ns.nodeID+" est possédé par le joueur "+map.getBasicBase(ns.nodeID).getIdPlayer());
				// parcours des noeuds voisins
				int nbForeignerNeighbour = 0;
				//SetView<Oriented> set = map.getFullBase(ns.nodeID).getBase().axes();
				SetView<Oriented> set = map.getBasicBase(ns.nodeID).getBase().axes();
				for(Oriented o : set) {
					int vid = o.next().id();
					//int pid = map.getFullBase(vid).getIdPlayer();
					int pid = map.getBasicBase(vid).getIdPlayer();
					
					// compteur de lien vers noeud non conquis
					if(pid != country)
					{
						nbForeignerNeighbour++;
					}
					
					// poursuite du parcours de la map vers les noeuds non parcourus
					if(!nodeDone.contains(pid))
						queue.add(new NodeState(vid, ns.depth+1));
				}
				
				// tous les voisins sont alli�s <=> zone safe
				if(nbForeignerNeighbour == 0)
					mSafeNodes.add(map.getBasicBase(ns.nodeID));
				// un des voisins n'est pas alli�, c'est un frontier
				else {
					map.getBasicBase(ns.nodeID).setNbVoisinsNonAmis(nbForeignerNeighbour);
					mFrontierNodes.add(map.getBasicBase(ns.nodeID));
				}
			}
		}
	}
	
	private class NodeState {
		int nodeID;
		int depth;
		
		public NodeState(int nodeID, int depth) {
			this.nodeID = nodeID;
			this.depth = depth;
		}
	}

	public ArrayList<Node> getSafeNodes() {
		return mSafeNodes;
	}

	public ArrayList<Node> getFrontierNodes() {
		return mFrontierNodes;
	}

	public ArrayList<Node> getGoalNodes() {
		return mGoalNodes;
	}
	// Cette fonction trie les listes selon des critères différents pour chaque : 
	/*
	 * Pour la liste de noeuds safe : on place en tête de liste les noeuds possédant beaucoup de troupes mobilisables
	 * pour la liste de noeuds frontaliers : on place en tête de liste les noeuds voisins de peu de noeuds à conquérir
	 * pour la liste de noeuds à prendre : on place en tête de liste les noeuds neutres, et sinon on les classe par force (les plus faibles devant) 
	 */
	static class SafeComparator implements Comparator<Node>
	 {
	     public int compare(Node n1, Node n2)
	     {
	    	 return n1.getDepth()-n2.getDepth();
	     }
	 }
	
	static class FrontierComparator implements Comparator<Node>
	 {
	     public int compare(Node n1, Node n2)
	     {
	    	return n1.getNbVoisinsNonAmis() - n2.getNbVoisinsNonAmis();
	     }
	    	
	 }
	
	static class GoalComparator implements Comparator<Node>
	 {
	     public int compare(Node n1, Node n2)
	     {
	    	 if(!n1.getBase().isEnemy(Model.getInstance().getMap().getFirstBase().getBase())
	    			 && n2.getBase().isEnemy(Model.getInstance().getMap().getFirstBase().getBase()))
			 {
	    		 return -1;
			 }
	    	 if(n1.getBase().isEnemy(Model.getInstance().getMap().getFirstBase().getBase())
	    			 && !n2.getBase().isEnemy(Model.getInstance().getMap().getFirstBase().getBase()))
			 {
	    		 return 1;
			 }
	    	 if(!n1.getBase().isEnemy(Model.getInstance().getMap().getFirstBase().getBase())
	    			 && !n2.getBase().isEnemy(Model.getInstance().getMap().getFirstBase().getBase()))
			 {
	    		 return 0;
			 }
	    	 if(n1.getBase().isEnemy(Model.getInstance().getMap().getFirstBase().getBase())
	    			 && n2.getBase().isEnemy(Model.getInstance().getMap().getFirstBase().getBase()))
			 {
	    		 if( Model.getInstance().getMap().getFullBase(n1.getBase().id()).getBase().militaryGarrison()
	    				 - Model.getInstance().getMap().getFullBase(n2.getBase().id()).getBase().militaryGarrison() < 0)
	    		 {
	    			 return -1;
	    		 }
	    		 else
	    		 {
	    			 return 1;
	    		 }
			 }
	    	 return 0;
	     }
	    	
	 }
	
	public void sortLists()
	{
		System.out.println("Tri des listes dans la stratégie");
		if(this.mSafeNodes.size()>0)
		{
			System.out.println("Tri des noeuds safe ("+this.mSafeNodes.size()+" noeuds)");
			Collections.sort(this.mSafeNodes, new SafeComparator());
		}
		if(this.mFrontierNodes.size()>0)
		{
			Collections.sort(this.mFrontierNodes, new FrontierComparator());
		}
		if(this.mGoalNodes.size()>0)
		{
			Collections.sort(this.mGoalNodes, new GoalComparator());
		}
		//System.out.println("Noeuds safe triés : ");
		for(Node n : mSafeNodes)
		{
			System.out.println(n.getBase().id()+" : Noeud safe de profondeur "+n.getDepth());
		}
		for(Node n : mFrontierNodes)
		{
			System.out.println(n.getBase().id()+" : Noeud frontière de profondeur "+n.getDepth());
		}
		for(Node n : mGoalNodes)
		{
			System.out.println(n.getBase().id()+" : Noeud objectif de profondeur "+n.getDepth());
		}
		System.out.println("Fin du tri");
	}
	
	// On appelle cette fonction une fois que les arrayList ont été initialisées et TODO triées
	public void executeStartegy()
	{
		Map map = Model.getInstance().getMap();
		
		//System.out.println("Choix de la prochaine action de conquête "+mSafeNodes.size() +" "+mFrontierNodes.size()+" "+mGoalNodes.size());
		// Tout d'abord, on choisit un noeud depuis lequel on va prendre le maximum de ressources pour les amener vers le noeud cible.
		//Node noeudAVider = null;
		//Node noeudAServir = null;
		ArrayList<Goal> goals = new ArrayList<>();
		// Gestion des safe zones
		for(Node n : mSafeNodes) {
			if(map.getFullBase(n.getBase().id()).getBase().militaryGarrison() >= 30) {
				Goal conquest = new Goal(null, n, Enums.GoalType.MOVING_RESSOURCES, Enums.Priority.HIGH, new ArrayList<Plane>());
				ArrayList<Plane> a = getPoolManager().askForNPlanes(conquest, 1);

				//System.out.println("Zone safe ??? "+goals.get(0).getSource().getBase().id());
				if(a.size() != 1)
					break;
				
				//System.out.println("zone safe ok "+goals.get(0).getSource().getBase().id());
				conquest.addAllocatedPlane(a.get(0));
				goals.add(conquest);
				//this.addGoal(conquest);
			}
		}
		
		/*if(mSafeNodes.size() != 0)
		{
			noeudAVider = mSafeNodes.get(0);
			System.out.println("On va prendre les soldats du noeud "+noeudAVider.getBase().id()+", dans la liste de noeuds safe");
		}
		else 
		if(mGoalNodes.get(0) != Model.getInstance().getMap().getFirstBase())
		{
			noeudAVider = Model.getInstance().getMap().getFirstBase();
			System.out.println("On va prendre les soldats du noeud "+noeudAVider.getBase().id()+", noeud initial de la carte");
		}
		else
		{
			noeudAVider = null;
			System.out.println("On est tout au début de la stratégie, on doit capturer le noeud initial");
//			Goal conquest = new Goal(mGoalNodes.get(0),  Enums.GoalType.COUNTRY_FOREIGN_RESSOURCES, Enums.Priority.HIGH);
//			this.addGoal(conquest);  
//			Integer nbRemainingPlanes = getPoolManager().getNumberOfAvailablePlanes(conquest);
//			System.out.println("Nb available : " + nbRemainingPlanes);
//			if (nbRemainingPlanes > 0) {
//				ArrayList<Plane> planes = getPoolManager().askForNPlanes(conquest, nbRemainingPlanes);
//				System.out.println("Nb planes affected : " + planes.size());
//				for (Plane p : planes) {
//					conquest.addAllocatedPlane(p);
//				}
//			}
 
			//this.getPoolManager().askForPlanes(conquest);
			//System.out.println("Le système a obtenu "+conquest.getAllocatedPlanes().size()+" avions pour attaquer");
			return;
		}*/
		
		// On choisit un noeud à servir
		for(Node n : mGoalNodes) {
			System.out.println("goal for "+n.getBase().id());
			if(n.getBase().ownerId() == 0) {
				System.out.println("owner is 0 "+n.getBase().id());
				if(goals.size() > 0) {
					System.out.println("with safe "+n.getBase().id());
					Goal g = goals.remove(0);
					g.setTargetBase(n);
					this.addGoal(g);

					System.out.println("goal safe "+g.getSource().getBase().id());
				}
				else {
					System.out.println("with othere "+n.getBase().id());
					Goal conquest = new Goal(n, Enums.GoalType.COUNTRY_FOREIGN_RESSOURCES, Enums.Priority.VERY_HIGH);
					ArrayList<Plane> p = this.getPoolManager().askForNPlanes(conquest, 1);
					if(p.size() == 0)
						break;
					conquest.addAllocatedPlanes(p);
					this.addGoal(conquest);
					System.out.println("goal other "+conquest.getSource().getBase().id());
				}
			}
		}
		for(Node n : mFrontierNodes) {
			if(goals.size() > 0) {
				Goal g = goals.remove(0);
				g.setTargetBase(n);
				this.addGoal(g);
				System.out.println("goal front safe "+g.getSource().getBase().id());
			}
			else {
				Goal conquest = new Goal(n, Enums.GoalType.COUNTRY_FOREIGN_RESSOURCES, Enums.Priority.HIGH);
				ArrayList<Plane> p = this.getPoolManager().askForNPlanes(conquest, 1);
				if(p.size() == 0)
					break;
				conquest.addAllocatedPlanes(p);
				this.addGoal(conquest);
				System.out.println("goal front other "+conquest.getSource().getBase().id());
			}
		}
		
		/*
		{
			
			// On se protège contre la possibilité de ne plus avoir de noeud cible
			/*if(n.getBase().ownerId() == 0)
			{
				// Dans ce cas, il existe un noeud neutre et c'est top moumoute
				noeudAServir = mGoalNodes.get(0);
				System.out.println("On prend pour cible un noeud neutre d'id "+noeudAServir.getBase().id());
			}
			else* /
			{
				// S'il n'y a pas de noeud neutre, on amène des ressources au niveau n-1, 
				// n étant le niveau des noeuds de la frontière extérieure
				// On sait que le noeud frontalier connecté au plus de noeuds non alliés est un noeud de niveau 
				// n, ce qui nous permet de trouver n et par là même n-1
				//System.out.println(("Id du joueur du premier noeud : "+mGoalNodes.get(0).getBase().ownerId()));
				System.out.println("On a pas de noeuds neutres à cibler, on va choisir le noeud le moins militarisé");
				int n = mFrontierNodes.get(mFrontierNodes.size()-1).getDepth();
				int minNumberOfVoisins = Integer.MAX_VALUE;
				for(Node node : mFrontierNodes)
				{
					if(node.getNbVoisinsNonAmis() < minNumberOfVoisins
							&& node.getDepth() == n-1)
					{
						minNumberOfVoisins = node.getNbVoisinsNonAmis();
						noeudAServir = node;
					}
				}
				System.out.println("On va servir le noeud portant l'id "+noeudAServir+", qui est le noeud de niveau "+(n-1)+" le moins connecté à l'ennemi");
			}
			
			// Maintenant qu'on a choisi le noeuds d'où les soldats partiront, et le noeud où ils iront,
			// il n'y a plus qu'à donner l'ordre
			//Goal conquest = new Goal(noeudAServir, noeudAVider,  Enums.GoalType.MOVING_RESSOURCES, Enums.Priority.HIGH);
			//TODO : transformer en déplacement de ressources
			Goal conquest = new Goal(noeudAServir, Enums.GoalType.COUNTRY_FOREIGN_RESSOURCES, Enums.Priority.HIGH);
			Goal frontierPatrol = new Goal(noeudAServir, Enums.GoalType.DEFEND, Enums.Priority.HIGH);
			//Goal conquest = new Goal(noeudAServir, Enums.GoalType.ATTACK, Enums.Priority.HIGH);
			this.addGoal(conquest);
			//this.setBases(mFrontierNodes);
			this.addGoal(frontierPatrol);
//			Integer nbRemainingPlanes = getPoolManager().getNumberOfAvailablePlanes(conquest);
//			System.out.println("Nb available : " + nbRemainingPlanes);
//			if (nbRemainingPlanes > 0) {
//				ArrayList<Plane> planes = getPoolManager().askForNPlanes(conquest, nbRemainingPlanes);
//				System.out.println("Nb planes affected : " + planes.size());
//				for (Plane p : planes) {
//					conquest.addAllocatedPlane(p);
//				}
//			}
			//TODO   
			for(Plane p :this.getPoolManager().askForNPlanes(conquest, 20))
			{
				conquest.addAllocatedPlane(p);
			}
			
			for(Plane p : this.getPoolManager().askForNPlanes(frontierPatrol, 30))
				conquest.addAllocatedPlane(p);
			
			System.out.println("Le système a obtenu "+conquest.getAllocatedPlanes().size()+" avions pour conquérir un noeud neutre");
		}
		else
		{
			System.out.println("ON EST DÉSOUVRÉS LÀ, IL N'Y A RIEN À CONQUÉRIR !");
		}*/
	}
	
	
	public void processObjectives() {
		super.processGoals();
		/*// Envoi des objectifs aux avions
		for (Goal g : this.getGoalList()) {
			ArrayList<Plane> planes = g.getAllocatedPlanes();
			for (Plane p : planes) {
				if (p.isAlive()) {
					if (p.getOrder().getType() == OrderType.WAITING_FOR_ORDER || 
						p.getOrderPriority().ordinal() < g.getPriority().ordinal()) {
						switch (g.getType()) {
							case COUNTRY_CLOSE_RESSOURCES:
							case COUNTRY_FOREIGN_RESSOURCES:
								System.out.println("On demande à un avion de ravitailler la base "+g.getBase().getBase().id());
								p.supplyBase(g.getBase(), g.getPriority());
								break;
							case ATTACK:
								break;
							case DEFEND:
								p.patrol(Model.getInstance().sortBasicBasesByClosestTo(p.getPosition()), g.getPriority());
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
		}*/
	}
	
}
