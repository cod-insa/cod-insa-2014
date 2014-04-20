package Interfaces;


import Model.BaseCriticityLevel;
import Model.Node;
import model.Base;
import model.Coord;
import common.MapView;


public interface toModel 
{
	public void updateMap(MapView<java.lang.Integer,Base.BasicView> basicMap, MapView<java.lang.Integer,Base.FullView> fullMap);
	public void updateNodeCriticity(int id, BaseCriticityLevel level);
}
