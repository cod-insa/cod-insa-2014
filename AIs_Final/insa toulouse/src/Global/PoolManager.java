package Global;
import Enums.OrderType;
import Enums.Priority;
import Interfaces.toPoolManager;
import java.util.*;

public class PoolManager implements toPoolManager{
	protected PlanePool mAttackPool;
	protected PlanePool mDefensePool;
	protected PlanePool mSupplyPool;
	
	protected PlanePool mNextPool;
	protected model.Plane.Type mNextType;

	
	public PoolManager() {
		mAttackPool = new PlanePool(this,12, 0.1);
		mDefensePool = new PlanePool(this,5, 0.9);
		mSupplyPool = new PlanePool(this,15, 0.5);
		mNextPool = mSupplyPool;
		//mNextPool = mAttackPool;
	}
	
	public PlanePool goalToPool(Goal goal){
		switch(goal.getType())
		{
		case ATTACK:
			return mDefensePool;
		case DEFEND:
			return mDefensePool;
		case MOVING_RESSOURCES:
			return mAttackPool;
		case COUNTRY_CLOSE_RESSOURCES:
			return mSupplyPool;
		case COUNTRY_FOREIGN_RESSOURCES:
			return mSupplyPool;
		default:
			return mSupplyPool;
		}
	}
	

	
	public void updatePools(Plane newPlane, ArrayList<Integer> killedPlanes) {
		if (newPlane != null)
		{
			mNextPool.addPlane(newPlane);
			createNextPlane();
		}
		for(Integer killedPlaneID : killedPlanes)
		{
				mAttackPool.removePlane(killedPlaneID);
				mDefensePool.removePlane(killedPlaneID);
				mSupplyPool.removePlane(killedPlaneID);
				
		}
	}
	public ArrayList<Plane> askForPlanes(Goal goal){
		return this.askForNPlanes(goal, -1);
	}
	
	public ArrayList<Plane> askForNPlanes(Goal goal, int number){
		ArrayList<Plane> allocatedList = new ArrayList<Plane>();
		for(Plane plane : goalToPool(goal).getAsMap().values())
		{
			if(plane.getOrder().getType()==OrderType.NO_ORDER)
			{
				allocatedList.add(plane);
				if(allocatedList.size() == number && number >= -1)
					break;
			}
			
		}
		System.out.println("taille des trucs alloués" + allocatedList.size());
		return  allocatedList ;
	}
	
	public int getNumberOfAvailablePlanes(Goal goal){
		return goalToPool(goal).getNumberOfAvailablePlanes();
	}
	
	public void createNextPlane(){
		//Recherche du plus loin de son ratio id�al
		chooseNextPool();
		mNextType = this.chooseNextType();
		InterfaceAPI.getInstance().buildPlane(mNextType);
	} 
	
	protected void chooseNextPool()
	{
		//On regarde le pool le plus loin de son objectif
		int totalFactor = mAttackPool.getFactor() + mDefensePool.getFactor() + mSupplyPool.getFactor();
		int totalPlanes = mAttackPool.getSize() + mDefensePool.getSize() + mSupplyPool.getSize();
		double gapAttack = Math.abs(((double)mAttackPool.getFactor()/(double)totalFactor)) 
				- ((double)mAttackPool.getSize()/(double)totalPlanes);
		double gapDefense = Math.abs(((double)mDefensePool.getFactor()/(double)totalFactor)) 
				- ((double)mDefensePool.getSize()/(double)totalPlanes);
		double gapSupply = Math.abs(((double)mSupplyPool.getFactor()/(double)totalFactor)) 
				- ((double)mSupplyPool.getSize()/(double)totalPlanes);
		
		if(gapAttack > Math.max(gapDefense, gapSupply))
			mNextPool = mAttackPool;
		else if (gapDefense > gapSupply)
			mNextPool = mDefensePool;
		else
			mNextPool = mSupplyPool;
	}
	
	protected model.Plane.Type chooseNextType()
	{
		//On regarde la proportion de militaire cr�e:
		double effectiveMilitaryRatio = (double)mNextPool.getMilitaryNumber()/(double)mNextPool.getSize();
		//On regarde si on est en sous ou sur effectif
		if(effectiveMilitaryRatio < mNextPool.getMilitaryRatio())
			return model.Plane.Type.MILITARY;
		else
			return model.Plane.Type.COMMERCIAL;
	}
}
