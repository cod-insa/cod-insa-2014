package players;

import java.util.Queue;

import command.Command;

public interface Player {

	Queue<Command> flushCommands();
	
//	boolean addCommand(Command c);
//	Command getNextCommand();
//    int getPlayerID();
//    String getTeamName();
//    int getFrameNumber();
//    List<BaseModel> getBases();
//    List<PlaneModel> getPlanes();
//	boolean isWaitingDataUpdate();
//	void setWaitingDataUpdate(boolean isWaitingDataUpdate);
//	Object getWaitData();
	
}
