package Model;

import java.util.ArrayList;

import common.MapView;

import model.Base;
import model.Coord;
import model.ProgressAxis;
import Global.Common;
import Interfaces.fromModel;
import Interfaces.toModel;


// implémente le design pattern singleton
public class Model implements  fromModel, toModel
{
	private static Model instance;
	private static boolean isDefined;
	private Map map;
	
	public static Model createInstance()
	{
		if(!isDefined)
		{
			System.out.println("Creating new instance");
			instance = new Model();
		}
		return instance;
	}
	
	public static Model getInstance()
	{
		if(!isDefined)
		{
			System.out.println("ATTENTION, APPEL À UNE INSTANCE DU MODÈLE PAR DÉFAUT");
			instance = new Model();
		}
		return instance;
	}
	
	private Model()
	{
		this.map = new Map();
		isDefined = true;
	}
	
	private Model(MapView<java.lang.Integer,Base.BasicView> basicMap, MapView<java.lang.Integer,Base.FullView> fullMap)
	{
		this.map = new Map();
		this.map.updateMap(basicMap, fullMap);
		isDefined = true;
	}
	
	public Map getMap()
	{
		return this.map;
	}

	@Override
	public Node getBaseCloserTo(Coord coord)
	{
		return this.map.getBaseCloserTo(coord);
	}
	
	public ArrayList<FullNode> sortBasesByClosestTo(Coord coord)
	{
		return this.map.sortBasesByClosestTo(coord);
	}
	
	public ArrayList<Node> sortBasicBasesByClosestTo(Coord coord)
	{
		return this.map.sortBasicBasesByClosestTo(coord);
	}


	@Override
	public void updateMap(MapView<java.lang.Integer,Base.BasicView> basicMap, MapView<java.lang.Integer,Base.FullView> fullMap) {
		this.map.updateMap(basicMap, fullMap);
	}
	
	@Override
	public void updateNodeCriticity(int id, BaseCriticityLevel level)
	{
		this.map.updateNodeCriticity(level, id);
	}

	@Override
	public Node getBaseCloserTo(Node node) {
		double distanceMin = Integer.MAX_VALUE;
		Node closestNode = null;
		for(ProgressAxis.Oriented axe : node._base.axes())
		{
			if(Common.distanceSquare(node.getPosition(), axe.next().position.copied()) < distanceMin)
			{
				closestNode = Model.getInstance().getMap().getBasicBase(axe.next().id());
				distanceMin = Common.distanceSquare(node.getPosition(), axe.next().position.copied());
			}
		}
		return closestNode;
	}
}
