package model;

public class PlaneModel {
	
	public final int id;
	public final Coord coord;
	public double rotation; 
	public int curHealthPoints;
	
	public PlaneModel(int id, Coord c, double r, int chp)
	{
		this.id = id;
		this.coord = c;
		this.rotation = r;
		this.curHealthPoints = chp;
	}
}
