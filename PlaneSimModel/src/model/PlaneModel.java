package model;

public class PlaneModel {
	
	protected int id;
	protected CoordModel coord;
	protected double rotation; //FIXME usefull ? On peut g�rer �a uniquement sur le moteur de jeu et �pargner les IA de �a
	protected int curHealthPoints;
	
	public PlaneModel(int id, CoordModel c, double r, int chp)
	{
		this.id = id;
		this.coord = c;
		this.rotation = r;
		this.curHealthPoints = chp;
	}
}
