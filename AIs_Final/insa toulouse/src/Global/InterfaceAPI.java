package Global;
import java.util.ArrayList;

import model.AbstractBase;
import model.Coord;
import command.AttackCommand;
import command.BuildPlaneCommand;
import command.Command;
import command.DropMilitarsCommand;
import command.ExchangeResourcesCommand;
import command.FillFuelTankCommand;
import command.FollowCommand;
import command.LandCommand;
import command.MoveCommand;
import command.WaitCommand;

public class InterfaceAPI {
	private static InterfaceAPI instance = null;
	private ArrayList<Command> coms;
	
	/**
	 * Singleton, créé lors du premier appel à la méthode
	 */
	public static InterfaceAPI getInstance() {
		if (instance == null) {
			instance = new InterfaceAPI();
		}
		return instance;
	}

	/**
	 * Constructeur privé : instancie les objets dont l'interface aura besoin
	 */
	private InterfaceAPI() {
		this.coms = new ArrayList<>();
	}
	
	/**
	 * Les méthodes ci-dessous permettent d'ajouter des commandes à envoyer au serveur
	 */
	
	public void attack(model.Plane.FullView planeSrc, model.Plane.BasicView planeTarget) {
		this.coms.add(new AttackCommand(planeSrc, planeTarget));
	}
	
	public void buildPlane(model.Plane.Type type) {
		this.coms.add(new BuildPlaneCommand(type));
	}
	
//	// Apparemment non implémenté
//	public void cancelRequest(model.Country.Request.View request) {
//		this.coms.add(new )
//	}
	
	public void dropMilitars(model.Plane.FullView planeSrc, AbstractBase.View baseTarget, double quantity) {
		this.coms.add(new DropMilitarsCommand(planeSrc, baseTarget, quantity));
	}
	
	// Permet de remplir ou de vider la soute :
	// Ex : militar = 10.0, fuel = 10.0, false => remplit la soute avec 10 et 10
	//      militar = -10.0, fuel = -1.0, false => vide la soute de 10 et 1
	//      militar = 10.0, true => enlève 10 aux ressources militaires (même en vol)
	public void exchangeResources(model.Plane.FullView planeSrc, double militar, double fuel, boolean delete) {
		this.coms.add(new ExchangeResourcesCommand(planeSrc, militar, fuel, delete));
	}
	
	public void fillFuelTank(model.Plane.FullView plane, double quantity) {
		this.coms.add(new FillFuelTankCommand(plane, quantity));
	}
	
	public void follow(model.Plane.FullView planeSrc, model.Plane.BasicView planeTarget) {
		this.coms.add(new FollowCommand(planeSrc, planeTarget));
	}
	
	public void land(model.Plane.FullView plane, AbstractBase.View base) {
		this.coms.add(new LandCommand(plane, base));
	}
	
	public void move(model.Plane.FullView plane, Coord.View coord) {
		this.coms.add(new MoveCommand(plane, coord));
	}
	
	public void wait(model.Plane.FullView plane) {
		this.coms.add(new WaitCommand(plane));
	}

	/**
	 * Permet d'envoyer toutes les commandes "pending" au serveur
	 */
	public ArrayList<Command> getCommands() {
		return this.coms;
	}
	
	/**
	 * Permet de réinitialiser les commandes
	 */
	public void resetCommands() {
		this.coms.clear();
	}
}
