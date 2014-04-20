package Global;

import java.util.ArrayList;

import model.Base;
import model.Coord;
import Enums.OrderType;
import Enums.Priority;
import Interfaces.fromPlane;
import Interfaces.toPlane;
import Model.FullNode;
import Model.Model;
import Model.Node;

public class Plane implements fromPlane, toPlane
{
	private model.Plane.FullView mPlane;
	private Order mOrder;
	protected PlanePool mOwner;
	protected boolean mAlive;

	public Plane(model.Plane.FullView sPlane)
	{
		if (sPlane != null) {
			mPlane = sPlane;
		}
		else {
			System.err.println("SPlane NULL");
		}
		mOrder = new Order();
		mAlive = true;
	}

	// Fonctions de possibilitï¿½ 
	@Override
	public boolean canGoTo(double x, double y) {
		return canGoTo(new Coord(x, y));
	}

	@Override
	public boolean canGoTo(Coord destination) {
		//On regarde si on a assez d'essence
		double distanceParcourue = Common.distance(mPlane.position.copied(), destination) 
				+ Common.distance(destination, Model.getInstance().getBaseCloserTo(destination).getPosition());

		return (mPlane.fuelInTank()/mPlane.type.fuelConsumptionPerDistanceUnit >= distanceParcourue);
	}

	@Override
	public boolean canAttackPlane(model.Plane attackedPlane) {
		return (mPlane.type == model.Plane.Type.MILITARY && canGoTo(attackedPlane.position));
	}

	@Override
	public boolean canDropTroop(double nb, Node sNode) {
		return (mPlane.militaryInHold() >= nb && canGoTo(sNode.getBase().position.copied()));
	}

	@Override
	public boolean canLoadTroop(double nb, Node sNode) {
		return ((mPlane.type.holdCapacity - (mPlane.fuelInHold() + mPlane.militaryInHold())) >= nb 
				&& canGoTo(sNode.getBase().position.copied()));
	}

	//////////////////////////////
	// Fonctions de rï¿½alisation //
	//////////////////////////////
	public void goTo(Coord position, Priority sPrior)
	{
		mOrder.goTo(position, sPrior);	
	}

	@Override
	public void attackPlane(model.Plane attackedPlane, Priority sPrior) {
		mOrder.attackPlane(attackedPlane, sPrior);
		InterfaceAPI.getInstance().attack(mPlane, attackedPlane.basicView);
	}

	public void dropTroop(double nb, Node base, Priority sPrior, double ratioMil)
	{
		mOrder.dropTroop(nb, base, sPrior, ratioMil);
		InterfaceAPI.getInstance().dropMilitars(mPlane, base.getBase(), nb);
	}
	
	public void moveResources(Node destination, Node src, Priority sPrior)
	{
		mOrder.moveResource(destination, src.getBase().id(), sPrior);
	}

	public void supplyBase(Node base, Priority sPrior)
	{
		mOrder.supplyBase(base, sPrior);
	}
	
	public void patrol(ArrayList<Node> bases, Priority sPrior, double ratioMil)
	{
		mOrder.patrolling(bases, sPrior, ratioMil);
	}

	/////////////
	// Getters //
	@Override
	public Coord getPosition() {
		return mPlane.position.copied();
	}

	@Override
	public double getTroop() {
		return mPlane.militaryInHold();
	}

	@Override
	public double getEmbeddedFuel() {
		return mPlane.fuelInTank();
	}

	@Override
	public Priority getOrderPriority() {
		return mOrder.getPriority();
	}

	public Order getOrder()
	{
		return mOrder;
	}

	public model.Plane.Type getType()
	{
		return mPlane.type;
	}

	public PlanePool getPool(){
		return mOwner;
	}

	public void _onChangePool(PlanePool pool){
		mOwner=pool;
	}

	protected void cancelOrder()
	{
		if(mOrder.getType() != OrderType.NO_ORDER)
		{
			InterfaceAPI.getInstance().land(mPlane, Model.getInstance().getBaseCloserTo(mPlane.position.copied()).getBase());
			mOrder.cancelOrder();
			
			release();
		}
	}

	public void update()
	{
		if(mPlane.fuelInTank() < mPlane.type.tankCapacity/5)
		{
			mOrder.loadFuel(null, Priority.CRITICAL);
		}
		//Recherche de ressources
		if(mOrder.getType() == OrderType.COUNTRY_RESOURCES)
			updateCountryResources();

		if(mOrder.getType() == OrderType.LOADING_FUEL)
			updateLoadingFuel();
		
		if(mOrder.getType() == OrderType.PATROLLING)
			updatePatrolling();
		
		if(mOrder.getType() == OrderType.MOVING_RESSOURCE)
			updateMoveRessource();
		
		if(mOrder.getType() == OrderType.DROPPING_TROOP)
			updateDroppingTroop();
	}

	protected void updateDroppingTroop()
	{
		if(mPlane.militaryInHold() < mOrder.getNumber())
		{
			if(mPlane.curBase() != null && (mPlane.curBase().id() == Model.getInstance().getMap().getCountry().id()))
			{
				System.out.println("On est a la maison, on recharge la patrouille : " + mOrder.getRatio());
				InterfaceAPI.getInstance().exchangeResources(mPlane,  mPlane.type.holdCapacity*mOrder.getRatio(),  mPlane.type.holdCapacity*(1-mOrder.getRatio()), false);
			}
		}
		else
		{
			if(mPlane.curBase() != null && (mPlane.curBase().id() == mOrder.getBase().getBase().id()))
			{
				InterfaceAPI.getInstance().exchangeResources(mPlane, -mPlane.militaryInHold(), -mPlane.fuelInHold(), false);
			}
			else
				InterfaceAPI.getInstance().move(mPlane, mOrder.getBase().getPosition().view());
		}
	}
	
	protected void updatePatrolling()
	{
		//On regarde si on tape un ennemi
		boolean attaque = false;
		model.Plane.BasicView temp;
		if(mPlane.curBase() != null && (mPlane.curBase().id() == Model.getInstance().getMap().getCountry().id()))
		{
			System.out.println("On est a la maison, on recharge la patrouille");
			InterfaceAPI.getInstance().exchangeResources(mPlane, -mPlane.militaryInHold(), -mPlane.fuelInHold(), true);
			InterfaceAPI.getInstance().exchangeResources(mPlane, mPlane.type.holdCapacity*mOrder.getRatio(), mPlane.type.holdCapacity*(1-mOrder.getRatio()), false);
		}
		
		for(int n = 0; n < Logistics.getGame().getEnnemyPlanes().size(); n++)
		{
			temp = Logistics.getGame().getEnnemyPlanes().get(n);
			if(mPlane.canAttack(temp))
			{
				System.out.println("Fight !");
				InterfaceAPI.getInstance().attack(mPlane, temp);
				attaque = true;
				break;
			}
		}
		if(attaque == false)
		{
			//SI on ne se bat pas on patrouille
			InterfaceAPI.getInstance().land(mPlane, mOrder.getBase().getBase());
			
			//On regarde si on est a une etape cle
			if(mPlane.curBase() != null && (mPlane.curBase().id() == mOrder.getBase().getBase().id()))
			{
				int id = mOrder.getBase().getBase().id();
				Base.FullView tempBase = Model.getInstance().getMap().getFullBase(id).getBase();
				if(tempBase.fuelInStock() < 2 && tempBase.isFriend(mPlane))
					InterfaceAPI.getInstance().exchangeResources(mPlane, 0, -mPlane.fuelInHold(), false);
			
				InterfaceAPI.getInstance().exchangeResources(mPlane, -Math.min(2, mPlane.militaryInHold()), 0, false);
					
					
				mOrder.nextPatrolPoint();
				if(mPlane.fuelInTank() <= 0.2*mPlane.type.tankCapacity || !canGoTo(mOrder.getBase().getPosition()))
					mOrder.loadFuel(mOrder.getBase(), Priority.HIGH);
			}
		}
		
	}
	protected void updateLoadingFuel()
	{
		FullNode targetNode = null;
		if(mPlane.type == model.Plane.Type.COMMERCIAL && canGoTo(Model.getInstance().getMap().getCountry().getPosition().view.copied())
				&& Model.getInstance().getMap().getCountry().planes().size() < 2)
		{
			System.out.println("Recharge a la base !");
			InterfaceAPI.getInstance().land(mPlane, Model.getInstance().getMap().getCountry());
			if(mPlane.curBase() != null && (mPlane.curBase().id() == Model.getInstance().getMap().getCountry().id()))
			{
				InterfaceAPI.getInstance().fillFuelTank(mPlane, mPlane.type.tankCapacity);
				if(mOrder.wasPatrolling() == true)
					cancelOrder();
				else
					mOrder.repatrolling();
			}
		}
		else
		{
			System.out.println("Recharge au loin !");
			//On trouve la station non vide la plus proche
			ArrayList<Node> sortesNodes = Model.getInstance().sortBasicBasesByClosestTo(mPlane.position.copied());
			for(Node n : sortesNodes)
			{
				if(n.getIdPlayer() == Common.myId)
				{
					if(Model.getInstance().getMap().getFullBase(n.getBase().id()).getBase().fuelInStock() >= mPlane.type.tankCapacity - mPlane.fuelInTank() - mPlane.fuelInHold())
					{
						targetNode = Model.getInstance().getMap().getFullBase(n.getBase().id());
						break;
					}
					else if (!canGoTo(n.getPosition()))
						break;
				}
			}
		
			//Si on sait ou aller, on y va
			if(targetNode != null)
			{
				InterfaceAPI.getInstance().land(mPlane, targetNode.getBase());
				if(mOrder.isSubDone() && mPlane.curBase() != null && (mPlane.curBase().id() == targetNode.getBase().id()))
				{
					InterfaceAPI.getInstance().exchangeResources(mPlane, 0, -mPlane.fuelInTank(), false);
					InterfaceAPI.getInstance().fillFuelTank(mPlane, mPlane.type.tankCapacity);
					mOrder.subDone();
				}
				else if(mPlane.fuelInTank() == mPlane.type.tankCapacity || targetNode.getBase().fuelInStock() <= 0)
				{
					mOrder.subNotDone();
					cancelOrder();
				}
			}
		}
	}
	
	// TODO : pour l'instant, bÃªte cccv de updateCOuntryRessources
	protected void updateMoveRessource()
	{
		if(mPlane.militaryInHold() + mPlane.fuelInHold() == mPlane.type.holdCapacity)
		{
			this.supplyBase(mOrder.getBase(), mOrder.getPriority());
		}

		// On n'a pas de ressources, on va en chercher
		// On est en route vers la maison
		else if (!mOrder.isSubDone() && mPlane.curBase() != null && (mPlane.curBase().id() != (int)mOrder.getNumber())) 
		{
			System.out.println("On est pas aï¿½ destination");
			InterfaceAPI.getInstance().land(mPlane, Model.getInstance().getMap().getCountry());
		}
		// On est Ã  la maison
		else if(mPlane.curBase() != null && (mPlane.curBase().id() == (int)mOrder.getNumber()))
		{
			System.out.println("On est a la maison, on se recharge");
			InterfaceAPI.getInstance().exchangeResources(mPlane, mPlane.type.holdCapacity*mOrder.getRatio(), mPlane.type.holdCapacity*(1-mOrder.getRatio()), false);
			mOrder.subNotDone();
			if(mPlane.militaryInHold() == mPlane.type.holdCapacity*mOrder.getRatio() || 
					Model.getInstance().getMap().getFullBase((int)mOrder.getNumber()).getBase().militaryGarrison() <= 0);
			{
				mOrder.subDone();
				cancelOrder();
			}
		}
		

	}
	
	protected void updateCountryResources()
	{
		if(mPlane.militaryInHold() + mPlane.fuelInHold() == mPlane.type.holdCapacity)
		{
			//L'approvisionnement est plein
			// On est aï¿½ destination
			if (mPlane.curBase() != null && (mPlane.curBase().id() == mOrder.getBase().getBase().id()))
			{System.out.println("111111111111111111111111111111111111111111111111111");
			
				System.out.println("On est aï¿½ la base, on decharge !");
				InterfaceAPI.getInstance().exchangeResources(mPlane, -mPlane.militaryInHold(), -mPlane.fuelInHold(), false);
				cancelOrder();
			}
			// L'objectif n'est pas atteignable
			else if(!this.canGoTo(mOrder.getBase().getPosition()))
			{System.out.println("2222222222222222222222222222222222222222");
			
				System.out.println("On annule l'ordre car impossible Ã  atteindre");
				cancelOrder();
			}
			// On est pas encore arrivÃ©s
			else if(!mOrder.isSubDone()) 
			{System.out.println("333333333333333333333333333333333333333333333333");
			
				System.out.println("On va Ã  la base distante");
				InterfaceAPI.getInstance().land(mPlane, mOrder.getBase().getBase());
				mOrder.subDone();
			}

		}

		// On n'a pas de ressources, on va en chercher
		// On est en route vers la maison
		else if (mPlane.curBase() != null && (mPlane.curBase().id() != Model.getInstance().getMap().getCountry().id())) 
		{System.out.println("44444444444444444444444444444444444444444444444444");
		
			System.out.println("On est pas aï¿½ destination");
			InterfaceAPI.getInstance().land(mPlane, Model.getInstance().getMap().getCountry());
			mOrder.subDone();
		}
		// On est Ã  la maison
		else if(mPlane.curBase() != null && (mPlane.curBase().id() == Model.getInstance().getMap().getCountry().id()))
		{System.out.println("55555555555555555555555555555555555555555555");
		
			System.out.println("On est a la maison, on se recharge");
			InterfaceAPI.getInstance().exchangeResources(mPlane, mPlane.type.holdCapacity*mOrder.getRatio(), mPlane.type.holdCapacity*(1-mOrder.getRatio()), false);
			mOrder.subNotDone();
		}

	}
	
	public boolean isAlive()
	{
		return mAlive;
	}
	

	public int id()
	{
		return mPlane.id();
	}
	public void _kill()
	{
		mAlive=false;
	}
	
	public void release()
	{
		mOrder = new Order();
		mOwner._onPlaneReleased(this);
	}
}
