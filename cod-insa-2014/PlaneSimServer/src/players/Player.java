package players;

import java.util.Queue;

import command.Command;

public abstract class Player {

	public abstract Queue<Command> flushCommands();
	
	//int getPlayerID();
	
	private static int nbPlayers = 0;
	public final int id = ++nbPlayers;
	
//	boolean addCommand(Command c);
//	Command getNextCommand();
//    int getPlayerID();
//    String getTeamName();
//    int getFrameNumber();
//    List<BaseModel> getBases();
//    List<PlaneModel> planes();
//	boolean isWaitingDataUpdate();
//	void setWaitingDataUpdate(boolean isWaitingDataUpdate);
//	Object getWaitData();
	
}
