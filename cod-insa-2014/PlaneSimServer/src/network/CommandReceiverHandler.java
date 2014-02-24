package network;

import game.Sim;
import genbridge.CommandReceiver;
import genbridge.MoveCommandData;
import genbridge.Response;
import genbridge.WaitCommandData;
import model.Coord;

import org.apache.thrift.TException;

import players.Player;
import players.PlayerManager;
import command.Command;
import command.MoveCommand;
import command.WaitCommand;
import common.NotSupportedException;

/**
 * CommandReveiverHandler
 *
 */
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

	public Response acceptCommand(Command cmd, int idConnection)
			throws TException {

		//ID verification
		if(!playerManager.isAuthorized(idConnection))
		{
			System.out.println("ID Player "+idConnection+" action refused. Cause: Client not connected");
			return new Response(ERROR_FORBIDDEN, "The server does not know you. Please use connect method");
		}

		Player p = playerManager.getPlayerById(idConnection);
		System.out.println("Player "+p.getTeamName()+" wants to perform action");

		//Action verification
		if(!actChecker.isValid(cmd))
		{
			System.out.println("Player "+p.getTeamName()+" action refused. Cause: illegal action");
			return new Response(ERROR_COMMAND, "This command is not legal");	//FIXME message more explicit?
		}
/*
		// 1) Create a Common.command.Command with information from Action
		command.Command com;

		switch (act.cmd) {
		case MOVE_PLANE:
			com = new MoveCommand(idConnection, new Coord(0,0).view);  //FIXME coordinate
			break;

		case BUILD_PLANE:
			//TODO
			throw new NotSupportedException();
			//break;

		case WAIT_PLANE:
			//TODO
			throw new NotSupportedException();
			//break;

		default:
			System.err.println("Error: Unknown action, cannot be processed");
			return new Response(ERROR_UNKNOWN, "");
		}*/
		
		//if (com != null)

		p.addCommand(cmd); 
		System.out.println("Player "+p.getTeamName()+" action has been accepted");
		return new Response(SUCCESS,"");
	}
	
	
	@Override
	public Response sendMoveCommand(MoveCommandData cmd, int idConnection)
			throws TException {

		// 1) Create a Common.command.Command with information from MoveCommandData
		Command com = new MoveCommand(cmd.pc.idPlane, new Coord(cmd.posDest.latid,cmd.posDest.longit).view);
		
		// 2) Verify and send the command to the NetworkPlayer
		return acceptCommand(com, idConnection); // then send the result back to the client
	}
	
	
	@Override
	public Response sendWaitCommand(WaitCommandData cmd, int idConnection)
			throws TException {
		// 1) Create a Common.command.Command with information from WaitCommandData
		Command com = new WaitCommand(cmd.pc.idPlane);
		
		// 2) Verify and send the command to the NetworkPlayer
		return acceptCommand(com, idConnection); // then send the result back to the client
	}

}



