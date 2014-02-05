package dataRepresentation;

import model.CoordModel;
import model.PlaneModel;

public class Plane extends PlaneModel {

	public Plane(int id, CoordModel c, double r, int chp) {
		super(id, c, r, chp);
	}
	
	// Getters :
	public int getId() { return id;	}
	public CoordModel getCoord() { return coord; }
	public double getRotation() { return rotation; }
	public int getCurHP() { return curHealthPoints; }
	
}
