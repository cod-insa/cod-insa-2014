package Interfaces;

import Model.Node;
import model.Base;
import model.Coord;

public interface fromModel 
{
	public Node getBaseCloserTo(Coord coord);
	public Node getBaseCloserTo(Node node);
}
