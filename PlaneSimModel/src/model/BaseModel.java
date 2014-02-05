package model;

public class BaseModel {

	protected int id;
	protected CoordModel coord;
	
	public BaseModel(int id, CoordModel c)
	{
		this.id = id;
		this.coord = c;
	}
}
