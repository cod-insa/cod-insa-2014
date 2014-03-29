package command;


public abstract class Command extends Throwable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int
		SUCCESS = 0,
		ERROR_COMMAND = -1,
		ERROR_TIME_OUT = -2,
		ERROR_FORBIDDEN = -3,
		ERROR_UNKNOWN = -4
	;
	
	public abstract void match() throws
		MoveCommand,
		LandCommand,
		WaitCommand, 
		AttackCommand,
		FollowCommand, 
		DropMilitarsCommand, 
		StoreFuelCommand, 
		FillFuelTankCommand, 
		LoadResourcesCommand
	;
	
}

/*
 * Reste des commandes � impl�menter :
 * D�charger Carburant
 * Remplir r�servoir
 * Charger ressources (militaires/carburant)
 * Cr�er avion
 * Mode expansion On/Off
 */


