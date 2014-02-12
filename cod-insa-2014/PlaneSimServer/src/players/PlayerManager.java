package players;

import game.Sim;

import java.util.ArrayList;
import java.util.List;

/**
 * PlayerManager creates player, receiving this order from the bridge 
 * when a new client is connected
 * @author NV
 *
 */
public class PlayerManager {

	List<Player> players;
	List<String> usedNames;
	Sim planeSimul;

	public PlayerManager(Sim planeSim)
	{
		this.players = new ArrayList<Player>();
		this.usedNames = new ArrayList<String>();
		this.planeSimul = planeSim;
	}

	/**
	 * Add a new network player
	 * called by the BridgeHandler when a new client is connected
	 * @param name, the team name, given by orga at the beginning
	 * @return the new player's ID
	 * @throws UsedNameException, as we accept only one AI by team
	 */
	public int addNewPlayer(String name) 
	{
		if(this.usedNames.contains(name))
			return -1;

		Player p = new NetworkPlayer(name);
		players.add(p);
		this.checkAllHere();
		return p.getPlayerID();

	}

	/**
	 * We check if all players have joined the game
	 * If so, we start the plane simulation!
	 */
	private void checkAllHere() {
		if(players.size() == planeSimul.getNbPlayers())
		{
			
			planeSimul.start();
		}
	}
	
	
	
	public Player getPlayerById(int playerID)
	{
		String res = "Unknown";
		for (Player p : players) 
			if(p.getPlayerID() == playerID)
				return p;
		return null;
	}
}
