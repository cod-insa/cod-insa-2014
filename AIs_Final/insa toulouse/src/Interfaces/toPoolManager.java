package Interfaces;
import java.util.ArrayList;

import Global.Goal;
import Global.Plane;

public interface toPoolManager {
	public ArrayList<Plane> askForPlanes(Goal goal);
	public ArrayList<Plane> askForNPlanes(Goal goal,int number);
}
