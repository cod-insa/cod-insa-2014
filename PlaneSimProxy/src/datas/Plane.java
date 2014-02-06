package datas;

import model.Coord;
import model.PlaneModel;

public class Plane {
	private PlaneModel model;
	private Coord.View coordonnees;
	
	public Plane(int id, Coord c, double rotation, int hp)
	{
		model = new PlaneModel(id,c,rotation,hp);
		coordonnees = c.view;
	}
	
	public int id() { return model.id; }
	public Coord.View coord() { return coordonnees; } 
}
