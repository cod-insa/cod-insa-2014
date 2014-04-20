package avions;
import model.Plane;
import proxy.Proxy;

import command.DropMilitarsCommand;
import command.ExchangeResourcesCommand;
import command.FollowCommand;

public class Chasseur extends MyPlane {
	public enum Role {
		ATTAQUE, DEFENSE;
	}

	private Role role;
	private MyPlane cible;
	private int machineEtat = 0;

	public Chasseur(Plane.FullView view, Proxy game) {
		super(view, game);
		this.setRole(Role.ATTAQUE);
	}

	public void reagir() {
		switch (this.getRole()) {
		case ATTAQUE:
			if(machineEtat == 0) {
				if(planeServer.fuelInTank() == planeServer.type.tankCapacity){
					game.sendCommand(new ExchangeResourcesCommand(planeServer,
							planeServer.type.holdCapacity, 0, false));
					game.sendCommand(new FollowCommand(planeServer, getCible()
							.getPlaneServer()));
					machineEtat++;
				}
			}
			else if(machineEtat == 1) {
				game.sendCommand(new DropMilitarsCommand(planeServer, getBaseDest(),
						planeServer.militaryInHold()));
				machineEtat++;
			}

			break;
		case DEFENSE:
			break;
		}
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public MyPlane getCible() {
		return cible;
	}

	public void setCible(MyPlane cible) {
		this.cible = cible;
	}
}
