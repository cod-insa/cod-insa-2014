package Global;

import java.util.ArrayList;

import model.Coord;
import Enums.OrderType;
import Enums.Priority;
import Model.Node;

public class Order {
	OrderType mOrderType;
	Priority mPriority;
	Node mTargetBase;
	//Node mTargetBase;
	model.Plane mTargetPlane;
	Coord mTargetCoord;
	double mTargetNumber; //Utile pour charger/dï¿½charger les troupes
	double mRatioMilitary;
	boolean mIsSubDone;
	boolean mWasPatrolling;
	ArrayList<Node> mPatrolBases = null;
	int mPatrolActu;
	
	public Order()
	{
		mOrderType = OrderType.NO_ORDER;
		mIsSubDone = false;
	}
	
	public void goTo(Coord sCoord, Priority sPrior)
	{
		mTargetCoord = sCoord;
		mPriority = sPrior;
		mOrderType = OrderType.GOING_TO;
	}
	
	public void dropTroop(double nb, Node sBase, Priority sPrior, double militaryRatio)
	{
		mTargetNumber = nb;
		mTargetBase = sBase;
		mRatioMilitary = militaryRatio;
		mPriority = sPrior;
		mOrderType = OrderType.DROPPING_TROOP;
	}
	
	public void attackPlane(model.Plane sPlane, Priority sPrior)
	{
		mTargetPlane = sPlane;
		mPriority = sPrior;
		mOrderType = OrderType.ATTACKING_PLANE;
	}
	
	public void supplyBase(Node sBase, Priority sPrior)
	{
		mTargetBase = sBase;
		mRatioMilitary = 0.5;
		mPriority = sPrior;
		mOrderType = OrderType.COUNTRY_RESOURCES;
	}
	
	//Envoyer la rï¿½partition de militaires
	public void supplyBase(Node sBase, double militaryRatio, Priority sPrior)
	{
		mTargetBase = sBase;
		mRatioMilitary = militaryRatio;
		mPriority = sPrior;
		mOrderType = OrderType.COUNTRY_RESOURCES;
	}
	
	public void loadFuel(Node sBase, Priority sPrior)
	{
		mPriority = sPrior;
		mOrderType = OrderType.LOADING_FUEL;
	}
	
	public void moveResource(Node destination, int id, Priority sPrior) {	
		mPriority = sPrior;
		mTargetNumber = (double) id;
		mTargetBase = destination;
	}
	
	public void patrolling(ArrayList<Node> sBases, Priority sPrior, double ratioMil)
	{
		mPatrolBases = sBases;
		mTargetBase = mPatrolBases.get(0);
		mPatrolActu = 0;
		mRatioMilitary = ratioMil;
		mOrderType = OrderType.PATROLLING;
		mPriority = sPrior;
		mWasPatrolling = true;
	}
	
	public void addPatrolPoint(Node sAddedBase)
	{
		mPatrolBases.add(sAddedBase);
	}
	
	public void nextPatrolPoint()
	{
		System.out.println("Changement de cible ...");
		mPatrolActu++;
		mTargetBase = mPatrolBases.get(mPatrolActu%mPatrolBases.size());
		System.out.println("...done !");
	}
	
	public void cancelOrder()
	{
		mOrderType = OrderType.NO_ORDER;
	}
	
	public Priority getPriority()
	{
		if(mOrderType == OrderType.NO_ORDER)
			return Priority.NOTHING;
		else
			return mPriority;
	}
	
	public OrderType getType()
	{
		return mOrderType;
	}
	
	public double getNumber()
	{
		return mTargetNumber;
	}
	
	public double getRatio()
	{
		return mRatioMilitary;
	}
	
	public Node getBase()
	{
		return mTargetBase;
	}
	
	public void setType(OrderType sType)
	{
		mOrderType = sType;
	}
	
	public boolean isSubDone()
	{
		return mIsSubDone;
	}
	
	public boolean wasPatrolling()
	{
		return mWasPatrolling;
	}
	
	public void repatrolling()
	{
		mOrderType = OrderType.PATROLLING;
	}
	
	public void subDone()
	{
		mIsSubDone = true;
	}
	
	public void subNotDone()
	{
		mIsSubDone = false;
	}
}
