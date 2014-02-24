package control;

import game.Sim;
import players.Player;
import players.PlayerManager;

import command.Command;
import command.MoveCommand;

public class SimStepUpdater {

	static SimStepUpdater single;
	public static SimStepUpdater get() { return single; }

	long period;
	//List<Player> players = new ArrayList<Player>();
	public final PlayerManager pm;
	
	/*
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}
	*/

	// period: the number of game frames between asking the players their commands
	public SimStepUpdater(PlayerManager pm, long update_period) {
		this.pm = pm;
		this.period = update_period;
		if (single != null)
			throw new Error("Can only create one controller!");
		single = this;
	}

	public void update (Sim s) {/* FIXME
		if (s.getCurrentFrame()%period == 0)
			for (Player p: pm.getPlayers())
				for (Command c: p.flushCommands())
					apply(c, s);*/
	}

	public void apply (Command c, Sim s) { // TODO actually call this
		if (c instanceof MoveCommand) {
			MoveCommand mc = (MoveCommand) c;
			s.getPlane(mc.planeId).autoPilot.goTo(mc.destination);
		} else {
			throw new Error("Unrecognized command!");
		}
	}

	public void addPlayer (Player p) {
		//players.add(p);
		/* FIXME
		 * pm.addNewPlayer(name)
		 */
	}

}
