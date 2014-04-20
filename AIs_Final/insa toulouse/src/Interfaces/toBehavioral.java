package Interfaces;

import Enums.Behaviour;
import Global.Goal;

public interface toBehavioral {
	public void addGoal(Goal g);
	public void delGoal(Goal goalReference);
	public void updateGoal(Goal g);
	
	public void setBehaviour(Behaviour b);
	
}
