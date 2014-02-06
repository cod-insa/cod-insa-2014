package model;

public class BaseModel {

	public int id;
	public final Coord coord;
	
	public BaseModel(int id, Coord c)
	{
		this.id = id;
		this.coord = c;
	}
}
