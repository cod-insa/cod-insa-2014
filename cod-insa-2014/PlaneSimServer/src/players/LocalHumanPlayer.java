package players;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import model.Base;
import model.Plane;
import command.Command;
import control.DataUpdater;

public class LocalHumanPlayer implements Player {

	@Override
	public int getPlayerID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTeamName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFrameNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Base> getBases() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Plane> getPlanes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWaitingDataUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWaitingDataUpdate(boolean isWaitingDataUpdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getWaitData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataUpdater getDataUpdater() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataUpdater(DataUpdater dataUp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCommand(Command c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Queue<Command> flushCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command getNextCommand() {
		// TODO Auto-generated method stub
		return null;
	}

}
