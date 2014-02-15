package players;

import game.Sim;

import java.util.ArrayList;
import java.util.List;

import control.DataUpdater;

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
	private ArrayList<String> authorizedNames;

	public PlayerManager(Sim planeSim)
	{
		this.players = new ArrayList<Player>();
		this.usedNames = new ArrayList<String>();
		this.planeSimul = planeSim;
		
		//Security	//FIXME if does not satisfy us
		this.authorizedNames = new ArrayList<String>();
		authorizedNames.add("team_rennes_495");
		authorizedNames.add("team_rouen_274");
		authorizedNames.add("team_lyon_993");
		authorizedNames.add("team_toulouse_781");
		authorizedNames.add("team_strasbourg_816");
		authorizedNames.add("team_valdeloire_631");
		authorizedNames.add("team_admin_218");
		authorizedNames.add("team_admin_964");
		authorizedNames.add("team_admin_729");
		authorizedNames.add("team_admin_554");
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
		if(this.usedNames.contains(name) /*|| !this.authorizedNames.contains(name)*/)	//FIXME Enable here security on name
			return -1;

		Player p = new NetworkPlayer(name);
		p.setDataUpdater(new DataUpdater(planeSimul, p));
		players.add(p);
		this.checkAllHere(p);
		return p.getPlayerID();

	}

	/**
	 * We check if all players have joined the game
	 * If so, we start the plane simulation!
	 */
	private void checkAllHere(Player p) {
		if(players.size() == planeSimul.getNbPlayers())
		{
			planeSimul.start();
			for (Player pp : players) {
				pp.notify();
				p.getDataUpdater().start();
			}
		}
		else
		{
			synchronized (planeSimul) {
				try {
					//this player waits everybody's ready
					p.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
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
