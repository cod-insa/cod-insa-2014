package Model;
import model.Base;
import model.Coord;

public class FullNode {
	BaseCriticityLevel _criticityLevel;
	Base.FullView _base;

	public FullNode(BaseCriticityLevel criticityLevel, Base.FullView base) {
		this._base = base;
		this._criticityLevel = criticityLevel;
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
	
	public Base.FullView getBase()
	{
		return this._base;
	}
	
	public Coord getPosition()
	{
		return this._base.position.copied();
	}
}
