package players;

import java.util.ArrayList;
import java.util.List;

import command.Command;

/**
 * @author LP
 * @author NV
 *
 */

/*
 TODO: it's convenient for the IA to be sending a stream of decisions rather than one big packet of decisions all at once
 so if it is too slow, it can still send *some* data rather than nothing
 we will accumulate the decisions here and return them all in a list, following IA's interface
*/

public class NetworkPlayer implements Player {
	
	private List<Command> commands = new ArrayList<Command>();
	private int playerID;
	private String teamName;
	
	
	public NetworkPlayer(String name) {
		this.teamName = name;
		this.playerID = name.hashCode();
	}
	
	// FIXME for debug
	public void addCommand(Command c) {
		commands.add(c);
	}

	public int getPlayerID() {
		return playerID;
	}

	public String getTeamName() {
		return teamName;
	}

	@Override
	public List<Command> flushCommands() {
		//System.out.println(commands.size());
		// TODO
		List<Command> coms = commands;
		commands = new ArrayList<Command>();
		return coms;
	}
	
}

