package players;

import game.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.transport.TTransportException;

import common.Event;
import common.Function;
import common.ListView;
import common.Util;

import control.Controller;

/**
 * PlayerManager creates player, receiving this order from the bridge 
 * when a new client is connected
 * @author NV
 *
 */
public class NetworkPlayerManager {
	
	private List<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
	
//	private List<String> usedNames;
//	private ArrayList<String> authorizedNames;
//	private ArrayList<Integer> authorizedIds;
	
	//private List<TSimpleServer> playerServers;
	
	private volatile boolean waiting_for_cons = false;
	
	private Map<Integer, NetworkPlayer> connectedPlayersById = new HashMap<>();
	private Set<Integer> usedIds = new HashSet<>();
	private final World world;
	private Function<NetworkPlayer, Void> onConnect = null, onDisconnect = null;
	
	public NetworkPlayerManager (World world)
	{
		/*
		this.usedNames = new ArrayList<String>();
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
		*/
		this.world = world;
	}

	/**
	 * Add a new network player
	 * called by the BridgeHandler when a new client is connected
	 * @param name, the team name, given by orga at the beginning
	 * @return the new player's ID
	 * @throws TTransportException 
	 * @throws UsedNameException, as we accept only one AI by team
	 */
	public void addNewPlayer(String name, int port) throws TTransportException 
	{
//		if (this.usedNames.contains(name) || this.players.size()>planeSimul.getNbPlayers() /*|| !this.authorizedNames.contains(name)*/)	//FIXME Enable here security on name
//			return -1;
		
		
		int id;
		do id = Math.abs(Util.rand.nextInt());
		while (usedIds.contains(id));
		
		usedIds.add(id);
		
		NetworkPlayer p = new NetworkPlayer(this, id, name, port, port+1, world);
//		players.add(p);
//		usedNames.add(name);
//		authorizedIds.add(p.getPlayerID());
//		this.checkAllHere(p);
//		return p.getPlayerID();
		
		players.add(p);
		
		Controller.get().addPlayer(p);
		
	}
	
	public void registerListeners(Function<NetworkPlayer, Void> onConnect, Function<NetworkPlayer, Void> onDisconnect) {
		this.onConnect = onConnect;
		this.onDisconnect = onDisconnect;
	}
	
	public void waitForConnections(Event onDone) {
		System.out.println("Waiting for "+players.size()+" player(s) to connect...");
		
		this.onConnect = onConnect;
		
		waiting_for_cons = true;
		
		while (connectedPlayersById.size() != players.size()) {
			//if (connectedPlayersById.size() > players.size()) throw new Error("Unexpected");
			assert connectedPlayersById.size() < players.size();
			try {
				synchronized(this){ wait(); }
			} catch (InterruptedException e) {
				System.err.println("Interrupted while waiting for player connections, resuming waiting. Exception was:");
				e.printStackTrace(System.err);
			}
			if (!waiting_for_cons) System.out.println("NetworkManager exiting");
			if (!waiting_for_cons)
				return;
		}
		
		onDone.call();
	}
	
	public synchronized void cancelWaitForConnections() {
		waiting_for_cons = false;
		System.out.println("HEY!!");
		notify(); // FIXME working?
	}
	
	public void diconnect() {

		System.out.println("Disconnecting players...");
		
		for (NetworkPlayer p: players)
			p.disconnect();
		
		synchronized(world) { world.notifyAll(); } // unblock network players still waiting for a frame

		for (NetworkPlayer p: players)
			p.join();
		
		System.out.println("Players disconnected.");
		
		
	}
	
	synchronized void notifyDisconnect (NetworkPlayer p) {
		System.out.println("Player "+p.name+" (id "+p.connectionId+") disconnected.");
		
		if (onDisconnect != null)
			onDisconnect.apply(p);
	}
	
	synchronized void notifyConnect (NetworkPlayer p) {
		
		System.out.println("Player "+p.name+" (id "+p.connectionId+") connected.");
		
		if (connectedPlayersById.containsKey(p.connectionId))
			System.err.println("Warning: player of id "+p.connectionId+" tried to connect more than once.");
		
		connectedPlayersById.put(p.connectionId, p);
		
		notify();
		
		if (onConnect != null)
			onConnect.apply(p);
		
	}

	public int getNbPlayers() {
		return players.size();
	}

	public ListView<NetworkPlayer> getPlayers() {
		return Util.shallowView(players);
	}
	
	

//	/**
//	 * We check if all players have joined the game
//	 * If so, we start the plane simulation!
//	 */
//	private void checkAllHere(Player p) {
//		if(players.size() == planeSimul.getNbPlayers())
//		{
//			System.out.println("All players are connected, plane simulation starts now!");
//			planeSimul.start();
//			for (Player pp : players) {
//				synchronized (pp) {
//					pp.notify();
//				}
//			}
//		}
//		else
//		{
//			System.out.println("Waiting for more players... Only "+players.size()+" player(s) is(are) connected, out of "+planeSimul.getNbPlayers());
//			synchronized (p) {
//				try {
//					//this player waits everybody's ready
//					p.wait();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	public Player getPlayerById(int playerID)
//	{
//		String res = "Unknown";
//		for (Player p : players) 
//			if(p.getPlayerID() == playerID)
//				return p;
//		return null;
//	}
//	
//	public boolean isAuthorized(int id)
//	{
//		return this.authorizedIds.contains(id);
//	}
//
//	public void dataReady() {
//		// TODO Auto-generated method stub
//	}
}
