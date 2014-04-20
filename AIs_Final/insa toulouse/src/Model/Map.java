package Model;
import java.util.ArrayList;
import java.util.HashMap;

import model.Base;
import model.Base.BasicView;
import model.Base.FullView;
import model.Coord;
import model.Country;
import model.ProgressAxis.Oriented;
import Global.Common;

import common.MapView;

public class Map 
{
	
	private HashMap<Integer, FullNode> _listOfFullBases;
	private HashMap<Integer,Node> _listOfBasicBases;
	Country.View _country;
	Node _firstBase;
	private ArrayList<int[]> firstBases; // {0:id, 1:nb de noeud dans le group}
	
	public Map()
	{
		_listOfBasicBases = new HashMap<>();
		_listOfFullBases = new HashMap<>();
		_country = null;
		_firstBase = null;
	}
	
	public void updateMap(
			MapView<java.lang.Integer,Base.BasicView> basicMap,
			MapView<java.lang.Integer,Base.FullView> fullMap)
	{
		// Met √† jour tous les noeuds du graphe en attribuant pour chaque id de la hashmap l'√©l√©ment correspondant
		for (BasicView  view : basicMap.valuesView()) {
			
			
			if(!_listOfBasicBases.containsKey(view.id()))
			{
				_listOfBasicBases.put(
						view.id(),
						new Node(BaseCriticityLevel.NORMAL, view));
			}
			else
			{
				_listOfBasicBases.get(view.id()).setBase(view);
			}
		}
		
		for(FullView view : fullMap.valuesView()) {
			_listOfFullBases.put(
					view.id(),
					new FullNode(BaseCriticityLevel.NORMAL, view));
		}	
		// On a besoin de calculer la fisrtBase une seule fois, et elle n'√©volue plus jamais.
		if(this._firstBase == null && this._country != null)
		{
			this._firstBase = getBaseCloserTo(this._country.position.copied());
		}
		if(this.firstBases == null)
			firstBases = getFirstBaseInPartitions();
	}
	
	public Node getBasicBase(int id)
	{
		if(_listOfBasicBases.containsKey(id))
		{
			return _listOfBasicBases.get(id);
		}
		else
		{
			System.out.println("La base demand√©e n'existe pas");
			return null;
		}
	}
	
	public FullNode getFullBase(int id)
	{
		if(_listOfFullBases.containsKey(id))
		{
			return _listOfFullBases.get(id);
		}
		else
		{
			System.out.println("La base demand√©e n'existe pas");
			return null;
		}
	}
	
	public void updateNodeCriticity(BaseCriticityLevel newCriticity, int id)
	{
		this._listOfBasicBases.get(id).setCriticity(newCriticity);
		if(this._listOfFullBases.containsKey(id))
		{
			this._listOfFullBases.get(id).setCriticity(newCriticity);
		}
	}
	
	public HashMap<Integer, Node> getListOfBasicBases() {
		return _listOfBasicBases;
	}
	
	public Country.View getCountry()
	{
		return _country;
	}
	
	public void setCountry(Country.View country)
	{
		_country = country;
		// D√®s qu'on d√©finit le country, on d√©finit la base qui en est le plus proche
		this._firstBase = getBaseCloserTo(this._country.position.copied());
		
	}
	
	public Node getBaseCloserTo(Coord coord)
	{
		double distanceMin = Integer.MAX_VALUE;
		Node closestNode = null;
		for(Node n : this.getListOfBasicBases().values())
		{
			if(Common.distanceSquare(coord, n.getPosition())<distanceMin)
			{
				closestNode = n;
				distanceMin = Common.distanceSquare(coord, n.getPosition());
			}
		}
		return closestNode;
	}
	
	// retourne la liste de l'id des zones qui sont les plus proches deu country parmi celles du mÍme groupe
	private ArrayList<int[]> getFirstBaseInPartitions() {
		HashMap<Integer, Boolean> nodeDone = new HashMap<>(); // <node_id, groupNum>
		// remplissage de 'false'
		for(FullNode n : _listOfFullBases.values())
			nodeDone.put(n.getBase().id(), false);
		
		ArrayList<int[]> firstId = new ArrayList<>();
		// parcours de tous les groupes, noeuds par noeuds
		for(int id : nodeDone.keySet())
		if(!nodeDone.get(id)) {
			double r[] = reccDistMin(id, nodeDone);
			firstId.add(new int[]{(int) r[1], (int) r[2]});
		}
		
		return firstId;
	}
	// return {distMin, ID du node ‡ la dist min, nb noeuds}
	private double[] reccDistMin(int fromId, HashMap<Integer, Boolean> nodeDone) {
		// noeud dÈj‡ parcouru
		if(nodeDone.get(fromId))
			return new double[]{-1,0, 0};
		// noeud maintenant parcouru
		nodeDone.put(fromId, true);
		
		FullView fv = _listOfFullBases.get(fromId).getBase();
		// recherche du noeud le plus proche ...
		double distMin = Common.distanceSquare(getCountry().position.copied(), fv.position.copied());
		int id = fromId;
		int size = 1;
		
		// parmi tous les noeuds du mÍme groupe (appels rÈccursifs)
		for(Oriented o : fv.axes()) {
			double d[] = reccDistMin(o.next().id(), nodeDone);
			// si au moins un noeud a ÈtÈ parcouru
			if(d[2] >= 1) {
				size += d[2]; // ajout du nombre de noeud parcouru
				if(d[0] < distMin) {
					distMin = d[0];  // nouv dist min
					id = (int) d[1]; // id du plus proche
				}
			}
		}
		
		return new double[]{distMin, id, size};
	}
	
	public ArrayList<FullNode> sortBasesByClosestTo(Coord coord)
	{
		boolean attributed = false;
		double distanceMin = Integer.MAX_VALUE;

		ArrayList<FullNode> closestNodes = new ArrayList<FullNode>();
		for(FullNode n : _listOfFullBases.values())
		{
			attributed = false;
			for(int indChecked = 0; indChecked < closestNodes.size(); indChecked++)
			{
				if(Common.distanceSquare(coord, n.getPosition()) < Common.distanceSquare(coord, closestNodes.get(indChecked).getPosition()))
				{
					//Nouveau plus proche1
					closestNodes.add(indChecked, n);
					attributed = true;
					break;
				}
			}
			if(attributed == false)
				closestNodes.add(n);
		}
		return closestNodes;
	}
	
	public ArrayList<Node> sortBasicBasesByClosestTo(Coord coord)
	{
		boolean attributed = false;
		double distanceMin = Integer.MAX_VALUE;

		ArrayList<Node> closestNodes = new ArrayList<Node>();
		for(Node n : _listOfBasicBases.values())
		{
			attributed = false;
			for(int indChecked = 0; indChecked < closestNodes.size(); indChecked++)
			{
				if(Common.distanceSquare(coord, n.getPosition()) < Common.distanceSquare(coord, closestNodes.get(indChecked).getPosition()))
				{
					//Nouveau plus proche1
					closestNodes.add(indChecked, n);
					attributed = true;
					break;
				}
			}
			if(attributed == false)
				closestNodes.add(n);
		}
		return closestNodes;
	}
	
	public Node getFirstBase()
	{
		return this._firstBase;
	}
}
