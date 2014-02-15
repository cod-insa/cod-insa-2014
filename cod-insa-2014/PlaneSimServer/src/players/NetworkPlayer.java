package players;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import command.Command;
import model.Base;
import model.Plane;
import control.DataUpdater;

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
	
	private Queue<Command> commands = new LinkedList<Command>();
	
	private int playerID;
	private String teamName;
	private int frameNumber;
	
	private List<Base> bases;
	private List<Plane> planes;
	
	private boolean isWaitingDataUpdate;
	private Object waitData;
	
	private DataUpdater dataUpdater;
	
	public NetworkPlayer(String name) {
		this.teamName = name;
		this.playerID = name.hashCode();
		this.frameNumber = 0;
		this.bases = new ArrayList<Base>();
		this.planes = new ArrayList<Plane>();
		this.isWaitingDataUpdate = false;
		this.waitData = new Object();
		this.dataUpdater = null;			//FIXME is set by PlayerManager
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
	
	@Override
	public DataUpdater getDataUpdater() {
		return dataUpdater;
	}
	
	public void setDataUpdater(DataUpdater dataUpdater) {
		this.dataUpdater = dataUpdater;
	}
	
	/**
	 * Add a command to the FIFO
	 */
	 public void addCommand(Command c) {
		 synchronized (commands) {
			 commands.offer(c);
		}
	 }
	 
	 /**
	  * Remove and return the head of the FIFO ueue
	  */
	 public Command getNextCommand() {
		 Command c = null;
		 synchronized (commands) {
			 c = commands.remove();
		}
		 return c;
	 }

	 @Override
	 /**
	  * Return a copy of all commands in the FIFO queue
	  * The queue is flushed
	  */
	 public Queue<Command> flushCommands() {
		 Queue<Command> coms = null;
	  synchronized (commands) {
		  coms = new LinkedList<Command>(commands);
		  commands.clear();
	}
	  return coms;
	 }
}




 
