package dataRepresentation;

import model.BaseModel;
import model.CoordModel;

public class Base extends BaseModel{

	public Base(int id, CoordModel c) {
		super(id, c);
	}
	
	public int getId() { return id; }
	public CoordModel getCoord() { return coord; }

}
