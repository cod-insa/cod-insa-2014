package Model;

import model.Base;
import model.Coord;

public class Node {
	BaseCriticityLevel _criticityLevel;
	Base.BasicView _base;
	int _nbVoisinsNonAmis;
	int _depth;

	public Node(BaseCriticityLevel criticityLevel, Base.BasicView base) {
		this._base = base;
		this._criticityLevel = criticityLevel;
		this._nbVoisinsNonAmis=0;
	}
	
	public int getIdPlayer()
	{
		return this._base.ownerId();
	}
	
	public BaseCriticityLevel getCriticity()
	{
		return this._criticityLevel;
	}
	
	public void setCriticity(BaseCriticityLevel criticity)
	{
		this._criticityLevel = criticity;
	}
	
	public Base.BasicView getBase()
	{
		return this._base;
	}
	
	public void setBase(Base.BasicView newBase)
	{
		this._base = newBase;
	}
	
	public Coord getPosition()
	{
		return this._base.position.copied();
	}
	
	public int getNbVoisinsNonAmis()
	{
		return this._nbVoisinsNonAmis;
	}
	
	public void setNbVoisinsNonAmis(int nb)
	{
		this._nbVoisinsNonAmis = nb;
	}
	
	public int getDepth()
	{
		return this._depth;
	}
	
	public void setDepth(int depth)
	{
		this._depth = depth;
	}
}

