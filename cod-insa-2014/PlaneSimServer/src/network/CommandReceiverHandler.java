package network;

import java.util.ArrayList;

import game.Sim;
import genbridge.Action;
import genbridge.Response;
import genbridge.CommandReceiver;

import org.apache.thrift.TException;

import players.Player;
import players.PlayerManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import command.MoveCommand;

public class CommandReceiverHandler implements CommandReceiver.Iface {


	//Error codes
	private static int SUCCESS = 0;
	private static int ERROR_COMMAND = -1;
	private static int ERROR_TIME_OUT = -2;
	private static int ERROR_FORBIDDEN = -3;
	private static int ERROR_UNKNOWN = -4;
	
	private PlayerManager playerManager;
	private ActionChecker actChecker;
	
	public CommandReceiverHandler(Sim sim, PlayerManager pmanager) {
		this.playerManager = pmanager;
		this.actChecker = new ActionChecker(sim);
	}
	@Override
	public Response addActionToPerform(Action act, int idConnection)
			throws TException {

		//ID verification
		if(!BridgeJavaServer.getAuthorizedIDs().contains(idConnection))
		{
			System.out.println("ID Player "+idConnection+" action refused. Cause: Client not connected");
			return new Response(ERROR_FORBIDDEN, "The server does not know you. Please use connect method");
		}

		Player p = playerManager.getPlayerById(idConnection);
		System.out.println("Player "+p.getTeamName()+" wants to perform action");

		//Action verification
		if(!actChecker.isValid(act))
		{
			System.out.println("Player "+p.getTeamName()+" action refused. Cause: illegal action");
			return new Response(ERROR_COMMAND, "This command is not legal");	//FIXME message more explicit?
		}

		// 1) Create a Common.command.Command with information from Action
		command.Command com = null;

		switch (act.cmd) {
		case MOVE_PLANE:
			com = new MoveCommand(idConnection, null);	//FIXME
			break;

		case BUILD_PLANE:
			//TODO
			throw new NotImplementedException();
			//break;

		case WAIT_PLANE:
			//TODO
			throw new NotImplementedException();
			//break;

		default:
			System.err.println("Error: Unknown action, cannot be processed");
			break;
		}

		// 2) Send it to the NetworkPlayer
		p.addCommand(com);

		// OK
		System.out.println("Player "+p.getTeamName()+" action has been accepted");
		return new Response(SUCCESS,"");
	}

}
