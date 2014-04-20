package Global;

import java.util.*;

import Model.Node;

public class PlanePool {
	protected int mFactor; //Importance total
	protected int mMilitaryNumber;
	protected double mMilitaryRatio; //Proportion Militaire/Commerciaux
	private int mNumberOfAvailablePlanes;
	//ratio : 1/3 <=> 1 attaque pour 3 approvisionnement

	protected PoolManager mOwner; 
	
	protected Map<Integer,Plane> mPlanes;
	
	public PlanePool(PoolManager owner,int factor, double ratio){
		mOwner=owner;
		mFactor = factor;
		mMilitaryRatio = ratio;
		mPlanes = new HashMap<>();
		mNumberOfAvailablePlanes=0;
	}
	public int getNumberOfAvailablePlanes()
	{
		return mNumberOfAvailablePlanes;
	}
	public Plane getNonCriticalPlaneNear(Node node){
		double minDist=-1;
		Plane bestPlane = null;
		double currDist=0;
		for(Plane plane : mPlanes.values())
		{
			currDist = Common.distance(plane.getPosition(), node.getPosition());
			
			if(currDist<minDist)
			{
				bestPlane = plane;
				currDist=minDist;
			}
		}
		return bestPlane;
	}
	public Map<Integer,Plane> getAsMap()
	{
		return mPlanes;
	}
	
	public void addPlane(Plane plane){
		if (plane != null) {
			mPlanes.put(plane.id(), plane);
			plane._onChangePool(this);
			mNumberOfAvailablePlanes++;
			if(plane.getType() == model.Plane.Type.MILITARY)
				mMilitaryNumber++;
		}
		else {
			System.err.println("Ajout d'un plane NULL :(");
		}

	}
	public void _onPlaneReleased(Plane plane)
	{
		mNumberOfAvailablePlanes++;
	}
	public Plane removePlane(Integer planeID){
		if(mPlanes.get(planeID) != null && mPlanes.get(planeID).getType() == model.Plane.Type.MILITARY)
			mMilitaryNumber--;
		Plane removed = mPlanes.remove(planeID);
		if(removed != null)
		{
			removed._kill();
			mNumberOfAvailablePlanes--;
		}
		return removed;
	}
	
	public int getFactor()
	{
		return mFactor;
	}
	
	public double getMilitaryRatio()
	{
		return mMilitaryRatio;
	}
	
	public int getMilitaryNumber()
	{
		return mMilitaryNumber;
	}
	
	public int getSize()
	{
		return mPlanes.size();
	}
}
