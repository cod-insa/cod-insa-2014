package Global;
import java.util.ArrayList;

import Enums.OrderType;
import Enums.Priority;
import Enums.GoalType;
import Model.Node;

public class Goal {
	private Enums.GoalState mGoalState;
	private Enums.GoalType mGoalType;
	private Priority mPriority;
	private ArrayList<Plane> mAllocatedPlanes;
	
	private Node mTargetBase;
	// Cet attribut n'est utile que quand on veut faire un transfert d'unités normalement, null le reste du temps.
	private Node mSourceBase;
	
	public Goal(Node sBase, Enums.GoalType sType, Priority sPrior)
	{
		mTargetBase = sBase;
		mPriority = sPrior;
		mGoalType = sType;
		mGoalState = Enums.GoalState.WAITING_FOR_PLANES;
		mAllocatedPlanes = new ArrayList<>();
		mSourceBase = null;
	}
	
	public Goal(Node sBase, Enums.GoalType sType, Priority sPrior, ArrayList<Plane> sPlanes)
	{
		mTargetBase = sBase;
		mPriority = sPrior;
		mGoalType = sType;
		mGoalState = Enums.GoalState.IN_PROGRESS;
		mAllocatedPlanes = sPlanes;
		mSourceBase = null;
	}
	
	public Goal(Node sBase, Node sSource, Enums.GoalType sType, Priority sPrior, ArrayList<Plane> sPlanes)
	{
		mTargetBase = sBase;
		mPriority = sPrior;
		mGoalType = sType;
		mGoalState = Enums.GoalState.IN_PROGRESS;
		mAllocatedPlanes = sPlanes;
		mSourceBase = sSource;
	}
	
	public Goal(Node sBase, Node sSource, Enums.GoalType sType, Priority sPrior)
	{
		mTargetBase = sBase;
		mPriority = sPrior;
		mGoalType = sType;
		mGoalState = Enums.GoalState.IN_PROGRESS;
		mAllocatedPlanes = new ArrayList<>();
		mSourceBase = sSource;
	}

	public void setAllocatedPlanes(ArrayList<Plane> sPlanes)
	{
		mAllocatedPlanes = sPlanes;
		for(Plane p : sPlanes) {
			p.getOrder().setType(OrderType.WAITING_FOR_ORDER);
		}
	}
	
	public void addAllocatedPlane(Plane p) {
		if (!mAllocatedPlanes.contains(p)) {
			mAllocatedPlanes.add(p);
			p.getOrder().setType(OrderType.WAITING_FOR_ORDER);
		}
	}
	
	public void addAllocatedPlanes(ArrayList<Plane> sPlanes) {
		for(Plane s : sPlanes)
		{
			if (!mAllocatedPlanes.contains(s)) {
				mAllocatedPlanes.add(s);
				s.getOrder().setType(OrderType.WAITING_FOR_ORDER);
			}
		}
	}
	public void setTargetBase(Node target) {
		mTargetBase = target;
	}
	public void setSourceBase(Node source) {
		mSourceBase = source;
	}
	public ArrayList<Plane> getAllocatedPlanes()
	{
		return mAllocatedPlanes;
	}
	
	public void removeAllocatedPlane(Plane plane)
	{
		mAllocatedPlanes.remove(plane);
	}
	public Node getBase()
	{
		return mTargetBase;
	}
	
	public Priority getPriority()
	{
		return mPriority;
	}
	
	public GoalType getType()
	{
		return mGoalType;
	}

	public Node getSource() {
		return mSourceBase;
	}
		
}
