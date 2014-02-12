package players;

import java.util.List;

import model.Base;
import model.Plane;
import command.Command;

public interface Player {

	List<Command> flushCommands();
	void addCommand(Command c);
    int getPlayerID();
    String getTeamName();
    int getFrameNumber();
    List<Base> getBases();
    List<Plane> getPlanes();
	boolean isWaitingDataUpdate();
	void setWaitingDataUpdate(boolean isWaitingDataUpdate);
	Object getWaitData();
}
