package avions;

import model.Plane.FullView;
import model.Plane.State;
import proxy.Proxy;

import command.DropMilitarsCommand;
import command.ExchangeResourcesCommand;
import command.LandCommand;

public class Transport extends MyPlane {
	public enum Role {
		PONT_PRINCIPAL, PONT_SECONDAIRE, ATTAQUE;
	}

	private Role role;
	private int machineEtat = 0;

	public Transport(FullView vue, Proxy game) {
		super(vue, game);
		this.setRole(Role.ATTAQUE);
	}

	public void reagir() {
		switch (role) {
		case PONT_PRINCIPAL:
			break;
		case PONT_SECONDAIRE:
			break;
		case ATTAQUE:
			if (machineEtat == 0) {
				if(planeServer.state() == State.AT_AIRPORT){
					game.sendCommand(new ExchangeResourcesCommand(planeServer,
							planeServer.type.holdCapacity / 2, planeServer.type.holdCapacity / 2, false));
					this.etat=Etat.IDLE;
					machineEtat++;
				}

			} else if (machineEtat == 1) {
				if(planeServer.state() != State.DROPPING && planeServer.state() != State.LANDING)
					game.sendCommand(new DropMilitarsCommand(planeServer, baseDest,
							planeServer.militaryInHold()));
				if(planeServer.militaryInHold() < 1)
					machineEtat++;
				if(baseDest.ownerId() == planeServer.ownerId()){
					if(planeServer.state() != State.LANDING)
						game.sendCommand(new LandCommand(planeServer, baseDest));
					machineEtat=10;
				}
			} else if(machineEtat == 10){
				if(planeServer.state() == State.AT_AIRPORT){
					game.sendCommand(new ExchangeResourcesCommand(planeServer, - planeServer.type.holdCapacity / 2,0, false));
					machineEtat = 3;
				}
			} else if (machineEtat == 2) {
				if(planeServer.state() != State.LANDING)
					game.sendCommand(new LandCommand(planeServer, baseDest));
				if(planeServer.state() == State.AT_AIRPORT)
					machineEtat++;
			}else if (machineEtat == 3) {
				game.sendCommand(new ExchangeResourcesCommand(planeServer, 0, - planeServer.type.holdCapacity / 2, false));
				machineEtat ++;
			}
			else if (machineEtat == 4) {
				if(planeServer.state() != State.LANDING){
					game.sendCommand(new LandCommand(planeServer, baseOrig));
					machineEtat = 0;
				}
			}
			break;
		}
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}