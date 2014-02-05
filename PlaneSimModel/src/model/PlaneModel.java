package model;

public class PlaneModel {
	
	protected int id;
	protected CoordModel coord;
	protected double rotation; //FIXME usefull ? On peut gérer ça uniquement sur le moteur de jeu et épargner les IA de ça
	protected int curHealthPoints;
	
	public PlaneModel(int id, CoordModel c, double r, int chp)
	{
		this.id = id;
		this.coord = c;
		this.rotation = r;
		this.curHealthPoints = chp;
	}
}
