package Interfaces;

import java.util.ArrayList;

import model.Coord;
import Enums.Priority;
import Model.Node;

public interface toPlane {
	//Asking if possible
    public boolean canGoTo(double x, double y);
    public boolean canGoTo(Coord destination);
    public boolean canAttackPlane(model.Plane attackedPlane);
    public boolean canDropTroop(double nb, Node Base);
    public boolean canLoadTroop(double nb, Node Base);
    
    //Do it !
    public void goTo(Coord destination, Priority sPrior);
    public void attackPlane(model.Plane attackedPlane, Priority sPrior);
    public void dropTroop(double nb, Node base, Priority sPrior, double ratioMil);
    public void supplyBase(Node base, Priority sPrior);
    public void patrol(ArrayList<Node> bases, Priority sPrior, double ratioMil);
    
    public void update();
}
