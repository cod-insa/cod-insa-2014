package datas;

import model.BaseModel;
import model.Coord;

public class Base {
	private BaseModel model;
	private Coord.View coordonnees;
	
	public Base(int id, Coord c)
	{
		model = new BaseModel(id,c);
		coordonnees = c.view;
	}
	
	public int id() { return model.id; }
	public Coord.View coord() { return coordonnees; } 
}
