package players;

import java.util.List;

import command.Command;

public interface Player {

	List<Command> flushCommands();
	void addCommand(Command c);
    int getPlayerID();
    String getTeamName();

	
}
