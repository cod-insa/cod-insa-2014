package Global;
import java.util.ArrayList;

import Enums.Behaviour;
import Enums.GoalState;
import Enums.GoalType;
import Enums.OrderType;
import Enums.Priority;
import Interfaces.fromBehavioral;
import Interfaces.toBehavioral;
import Model.Model;
import Model.Node;


public class Behavioral implements toBehavioral, fromBehavioral {
	private ArrayList<Goal> mGoals = new ArrayList<Goal>();
	private Goal mDefenseGoal;
	private Behaviour mCurrentBehaviour;
	private PoolManager mPoolManager;
	private ArrayList<Node> mBases;
	private double mAttackMilRatio = 0.9, mDefendMilRatio = 0.1;
	
	public Behavioral(Behaviour defaultBehaviour, PoolManager pool) {
		mCurrentBehaviour = defaultBehaviour;
		mPoolManager = pool;
	}
	
	public void update() 
	{
		if (mGoals.isEmpty()) {
			Goal g = new Goal(Model.getInstance().getMap().getFirstBase(), GoalType.DEFEND, Priority.MEDIUM);
			g.setAllocatedPlanes(mPoolManager.askForPlanes(g));
			mGoals.add(g);
		}
//		for (Goal g : mGoals) {
//			boolean removable = true;
//			for (Plane p : g.getAllocatedPlanes()) {
//				if (p.getOrder().getType() != OrderType.WAITING_FOR_ORDER &&
//					p.getOrder().getType() != OrderType.NO_ORDER) {
//					removable = false;
//				}
//			}
//			if (removable) {
//				System.out.println("Remove base");
//				mBases.remove(g.getBase());
//				mGoals.remove(g);
//			}
//		}
		if(mPoolManager.getNumberOfAvailablePlanes(mGoals.get(0)) > 0)
			mGoals.get(0).addAllocatedPlanes(mPoolManager.askForPlanes(mGoals.get(0)));
	}
	
	protected void setBases(ArrayList<Node> sBases)
	{
		mBases = sBases;
	}
	
	protected void processGoals(){
		
		for(Goal g: mGoals)
		{
			ArrayList<Plane> planes = g.getAllocatedPlanes();
			for (Plane p : planes) {
				if (p.isAlive()) {
					if (p.getOrder().getType() == OrderType.WAITING_FOR_ORDER || 
						p.getOrderPriority().ordinal() < g.getPriority().ordinal()) {
						switch (g.getType()) {
							case ATTACK:
								p.dropTroop(p.getTroop(), g.getBase(), g.getPriority(), mAttackMilRatio);
								break;
							case DEFEND:
								System.out.println("Nouveau patrouilleur");
								if(mBases == null)
									mBases = Model.getInstance().getMap().sortBasicBasesByClosestTo(Model.getInstance().getMap().getCountry().getPosition().view().copied());
								p.patrol(mBases, g.getPriority(), mDefendMilRatio);
								break;
							case COUNTRY_CLOSE_RESSOURCES:
							case COUNTRY_FOREIGN_RESSOURCES:
								p.supplyBase(g.getBase(), g.getPriority());
								break;
							case MOVING_RESSOURCES:
								p.moveResources(g.getBase(), g.getSource(), g.getPriority());
								break;
							default:
								break;
						}

					}
					p.update();
				}
			}
		}
	}
	
	@Override
	public GoalState getGoalState(Goal g) {
		return GoalState.INACTIVE;
	}

	@Override
	public void addGoal(Goal g) {
		if(!mGoals.contains(g))
			mGoals.add(g);
	}

	@Override
	public void delGoal(Goal goalReference) {
		mGoals.remove(goalReference);
	}

	@Override
	public void updateGoal(Goal g) {
		for(Plane plane : g.getAllocatedPlanes())
		{
			plane.update();
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void setBehaviour(Behaviour b) {
		mCurrentBehaviour = b;
	}
	
	public PoolManager getPoolManager()
	{
		return this.mPoolManager;
	}
	
	public ArrayList<Goal> getGoalList()
	{
		return this.mGoals;
	}
}
