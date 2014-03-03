package players;

import java.util.List;
import java.util.Queue;

import model.BaseModel;
import model.PlaneModel;

import command.Command;

import control.DataUpdater;

public interface Player {

	boolean addCommand(Command c);
	Queue<Command> flushCommands();
	Command getNextCommand();
    int getPlayerID();
    String getTeamName();
    int getFrameNumber();
    List<BaseModel> getBases();
    List<PlaneModel> getPlanes();
	boolean isWaitingDataUpdate();
	void setWaitingDataUpdate(boolean isWaitingDataUpdate);
	Object getWaitData();
}
