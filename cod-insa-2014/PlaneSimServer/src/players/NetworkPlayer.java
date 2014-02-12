package players;

import java.util.ArrayList;
import java.util.List;

import model.Base;
import model.Plane;
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
	
	private int frameNumber;
	
	private List<Base> bases;
	private List<Plane> planes;
	
	private boolean isWaitingDataUpdate;
	private Object waitData;
	
	public NetworkPlayer(String name) {
		this.teamName = name;
		this.playerID = name.hashCode();
		this.frameNumber = 0;
		this.bases = new ArrayList<Base>();
		this.planes = new ArrayList<Plane>();
		this.isWaitingDataUpdate = false;
	}
	
	public int getPlayerID() {
		return playerID;
	}

	public String getTeamName() {
		return teamName;
	}

	public int getFrameNumber() {
		frameNumber++;
		return frameNumber;
	}
	
	public boolean isWaitingDataUpdate() {
		return isWaitingDataUpdate;
	}

	public void setWaitingDataUpdate(boolean isWaitingDataUpdate) {
		this.isWaitingDataUpdate = isWaitingDataUpdate;
	}

	public Object getWaitData() {
		return waitData;
	}

	@Override
	public List<Base> getBases() {
		return bases;
	}

	@Override
	public List<Plane> getPlanes() {
		return planes;
	}
	
	// FIXME for debug
	public void addCommand(Command c) {
		commands.add(c);
	}
	
	// FIXME for debug
	@Override
	public List<Command> flushCommands() {
		//System.out.println(commands.size());
		List<Command> coms = commands;
		commands = new ArrayList<Command>();
		return coms;
	}
}

