package avions;

import model.AbstractBase;
import model.Base;
import model.Coord;
import model.Plane;
import model.Plane.FullView;
import proxy.Proxy;


public class MyPlane {
    public enum Etat {
	IDLE, ACTIVE;
    }
    
    protected Proxy game;
    protected Plane.FullView planeServer;
    protected Etat etat;
    protected Coord.View destination;
    protected Base.BasicView baseDest;
    protected AbstractBase.View baseOrig;

    public MyPlane(Plane.FullView vue, Proxy game) {
	this.vue = vue;
	this.planeServer = vue;
	this.game = game;
	this.etat = Etat.IDLE;
    }
    
    public Plane.FullView getPlaneServer() {
	return planeServer;
    }
    
    public void setPlaneServer(Plane.FullView planeServer) {
	this.planeServer = planeServer;
    }

    public Coord.View getDestination() {
	return destination;
    }

    public void setDestination(Coord.View destination) {
	this.destination = destination;
    }

    public Etat getEtat() {
	return etat;
    }

    public void setEtat(Etat etat) {
	this.etat = etat;
    }

    public Base.BasicView getBaseDest() {
	return baseDest;
    }

    public void setBaseDest(Base.BasicView baseDest) {
	this.baseDest = baseDest;
    }

    public AbstractBase.View getBaseOrig() {
	return baseOrig;
    }

    public void setBaseOrig(AbstractBase.View baseOrig) {
	this.baseOrig = baseOrig;
    }
    
	protected AbstractBase.View objectif;
	protected FullView vue;
	
	public void setView(FullView vue) {
		this.vue = vue;
	}
	
	public FullView getView() {
		return vue;
	}
	
	public model.Coord.View getObjectif() {
		return objectif.position();
	}
	
	public AbstractBase.View getBaseObjectif() {
		return objectif;
	}
}
