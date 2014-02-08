package control;

import game.Sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import players.Player;

import command.Command;
import command.MoveCommand;

public class Controller {
	
	static Controller single;
	public static Controller get() { return single; }
	
	int period;
	List<Player> players = new ArrayList<Player>();
	
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}
	
	// period: the number of game frames between asking the players their commands
	public Controller(int period) {
		this.period = period;
		if (single != null)
			throw new Error("Can only create one controller!");
		single = this;
	}
	
	public void update (Sim s) {
		if (s.getCurrentFrame()%period == 0)
			for (Player p: players)
				for (Command c: p.flushCommands())
					apply(c, s);
	}
	
	public void apply (Command c, Sim s) {
		if (c instanceof MoveCommand){
			MoveCommand mc = (MoveCommand) c;
			s.getPlane(mc.planeId).autoPilot.goTo(mc.destination);
		} else {
			throw new Error("Unrecognized command!");
		}
	}
	
	public void addPlayer (Player p) {
		players.add(p);
	}
	
}







