package network;

import genbridge.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import control.DataUpdater;
import players.Player;
import players.PlayerManager;

public class BridgeHandler implements Bridge.Iface{

	//Error codes
	private static int SUCCESS = 0;
	private static int ERROR_COMMAND = -1;
	private static int ERROR_TIME_OUT = -2;
	private static int ERROR_FORBIDDEN = -3;
	private static int ERROR_UNKNOWN = -4;

	private PlayerManager playerManager;
	private List<Integer> authorizedIDs;
	private ActionChecker actChecker;
	private DataUpdater control;

	public BridgeHandler(PlayerManager pmanager, DataUpdater control) {
		this.playerManager = pmanager;
		this.authorizedIDs = new ArrayList<Integer>();
		this.actChecker = new ActionChecker(null);		//FIXME
		this.control = control;
	}

	@Override
	public int connect(String nom) throws TException {
		System.out.println("Player "+nom+" is trying to connect to the server");
		int id = playerManager.addNewPlayer(nom);
		if(id != -1)
		{
			authorizedIDs.add(id);
			System.out.println("Player "+nom+" is now connected");
		}
		else
		{
			System.out.println("Player "+nom+" has been banned");
		}
		return id;
	}

	@Override
	public Data retrieveData(int idConnection) throws TException {

		Data toBeReturned = new Data();
		toBeReturned.bases = new ArrayList<Base>();
		toBeReturned.planes = new ArrayList<Plane>();
		toBeReturned.numFrame = -1;

		//ID verification
		if(!authorizedIDs.contains(idConnection))
		{
			System.out.println("ID Player "+idConnection+" wants data but is not connected");
			return toBeReturned;
		}

		Player p = playerManager.getPlayerById(idConnection);
		System.out.println("Player "+p.getTeamName()+" wants data");
		toBeReturned.numFrame = p.getFrameNumber();

		//Wait for data update
		synchronized (p.getWaitData()) {
			try {
				p.setWaitingDataUpdate(true);
				p.getWaitData().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}

	//FIXME when we create a new player we create a DataUpdater object
		//it's a thread that starts when all players are connected
		//upadte data every second and notify the handling thread to send data when ready
	// TODO fill attributes with information

	System.out.println("Player "+p.getTeamName()+" has been served");
	return toBeReturned;
}

@Override
public Response addActionToPerform(int idConnection, Action act)
		throws TException {

	//ID verification
	if(!authorizedIDs.contains(idConnection))
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

	// TODO Send command to concerned NetworkPlayer?
	// 1) Create a Common.command.Command with information from Action
	// 2) Send it to the NetworkPlayer

	// TODO Auto-generated method stub
	System.out.println("Player "+p.getTeamName()+" action has been accepted");
	return new Response(SUCCESS,"");
}

}

