package players;

import java.util.List;
import java.util.Queue;

import model.Base;
import model.Plane;
import command.Command;
import control.DataUpdater;

public interface Player {

	void addCommand(Command c);
	Queue<Command> flushCommands();
	Command getNextCommand();
    int getPlayerID();
    String getTeamName();
    int getFrameNumber();
    List<Base> getBases();
    List<Plane> getPlanes();
	boolean isWaitingDataUpdate();
	void setWaitingDataUpdate(boolean isWaitingDataUpdate);
	Object getWaitData();
	DataUpdater getDataUpdater();
	void setDataUpdater(DataUpdater dataUp);
}
