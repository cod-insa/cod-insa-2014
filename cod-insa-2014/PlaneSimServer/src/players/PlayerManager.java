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

	private List<Player> players;
	private List<String> usedNames;
	private Sim planeSimul;
	private ArrayList<String> authorizedNames;
	private ArrayList<Integer> authorizedIds;

	public PlayerManager(Sim planeSim)
	{
		this.players = new ArrayList<Player>();
		this.usedNames = new ArrayList<String>();
		this.planeSimul = planeSim;
		
		//Security	//FIXME if does not satisfy us
		this.authorizedIds = new ArrayList<Integer>();
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
		if (this.usedNames.contains(name) || this.players.size()>planeSimul.getNbPlayers() /*|| !this.authorizedNames.contains(name)*/)	//FIXME Enable here security on name
			return -1;

		Player p = new NetworkPlayer(name);
		players.add(p);
		usedNames.add(name);
		authorizedIds.add(p.getPlayerID());
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
			System.out.println("All players are connected, plane simulation starts now!");
			planeSimul.start();
			for (Player pp : players) {
				synchronized (pp) {
					pp.notify();
				}
			}
		}
		else
		{
			System.out.println("Waiting for more players... Only "+players.size()+" player(s) is(are) connected, out of "+planeSimul.getNbPlayers());
			synchronized (p) {
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
	
	public boolean isAuthorized(int id)
	{
		return this.authorizedIds.contains(id);
	}

	public void dataReady() {
		// TODO Auto-generated method stub
	}
}
