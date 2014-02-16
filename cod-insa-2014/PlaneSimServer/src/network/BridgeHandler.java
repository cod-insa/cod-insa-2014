package network;

import game.Sim;
import genbridge.*;

import org.apache.thrift.TException;
import players.Player;
import players.PlayerManager;

/**
 * Bridge handler is the handler for connection and data sender.
 * @author NVA
 *
 */
public class BridgeHandler implements Bridge.Iface{


	private PlayerManager playerManager;

	public BridgeHandler(Sim sim, PlayerManager pmanager) {
		this.playerManager = pmanager;
	}

	@Override
	public int connect(String nom) throws TException {
		System.out.println("Player "+nom+" is trying to connect to the server");
		int id = playerManager.addNewPlayer(nom);
		if(id != -1)
		{
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

		//ID verification
		if(!playerManager.isAuthorized(idConnection))
		{
			System.out.println("ID Player "+idConnection+" wants data but is not connected");
			return new Data();
		}

		Player p = playerManager.getPlayerById(idConnection);
		System.out.println("Player "+p.getTeamName()+" wants data");

		//Wait for data update
		synchronized (p.getWaitData()) {
			try {
				p.setWaitingDataUpdate(true);
				p.getWaitData().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}

		//When we create a new player we create a DataUpdater object
		//it's a thread that starts when all players are connected
		//it updates data every second and notify the handling thread to send data when ready

		System.out.println("Player "+p.getTeamName()+" has been served");
		return p.getDataUpdater().getTobeSent();
	}


	

}

