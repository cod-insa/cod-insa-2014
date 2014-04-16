package network;

import game.Game;
import game.GameBase;
import game.GamePlane;
import game.MapLoader.MapInfo;
import game.World;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONStringer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import players.NetworkPlayerManager;
import common.CoordConverter;

public class WebInterface extends WebSocketServer {

	static final Logger log = LoggerFactory.getLogger(WebInterface.class);

	private ArrayList<WebSocket> authorized;
	private MapInfo mapInfo;
	private CoordConverter converter;
	private World world;
	private Game game;
	private NetworkPlayerManager npm;
	private AutoSender autoSender;

	private WebInterface( InetSocketAddress address, Draft d) {
		super(address,Collections.singletonList(d));
		authorized = new ArrayList<WebSocket>();
		autoSender = new AutoSender();
	}

	/**
	 * Call this method to start the web interface server
	 * Settings are in NetworkSettings.java
	 * It does not like "localhost"
	 */
	public static WebInterface startWebInterface(Game game, NetworkPlayerManager npm)
	{
		
		WebInterface wi = null;
		
		//With Ubuntu, Java gets the localhost ip address (ie 127.0.0.1) and we need to register
		//the server with the lan ipaddress. So here is how to do that.
		//We try to find the ip address in network interfaces properties (finally, we get eth0 ip v4 address).
		try {
			boolean found = false;
			InetAddress ip = InetAddress.getLocalHost();
			Enumeration<NetworkInterface> netInterfaces=NetworkInterface.getNetworkInterfaces();
			while(netInterfaces.hasMoreElements()){
				Enumeration<InetAddress> inets = netInterfaces.nextElement().getInetAddresses();
				while(inets.hasMoreElements()){
					ip = inets.nextElement();
					//System.out.println(ip.getHostAddress());
					if( !ip.getHostAddress().equals("127.0.0.1") && ip.getHostAddress().length() < 16){
						//System.out.println(ip.getHostAddress());
						found = true;
						break;
					}else{
						ip=null;
					}
				}
				if(found)
					break;
			}

			if(ip == null)
			{
				log.error("I've not found my IP address... Are you connected to the network?");
				log.error("Continuing with localhost 127.0.0.1");
				ip = InetAddress.getLocalHost();

			}
			//log.error(ip.getHostAddress());
			
			int port = NetworkSettings.webport;
			InetSocketAddress isa = new InetSocketAddress(ip.getHostAddress(), port);
			Draft d = new Draft_17();
			wi = new WebInterface(isa,d);
			log.info("Starting webinterface server on "+ip.getHostAddress());
			wi.start();
			log.info("Server listening");
		} catch (SocketException | UnknownHostException e) {
			log.error("WebSocketServer cannot start");
		}

		wi.mapInfo = game.mapInfo;
		wi.world = game.getWorld();
		wi.converter = game.converter;
		wi.game = game;
		wi.npm = npm;
		
		return wi;
	}

	public void sendMapInfo(WebSocket conn)
	{
		JSONStringer str = new JSONStringer();

		str.object()
		.key("nbp")
		.value(game.getNbPlayers())
		.key("map")
		.object()
		.key("name")
		.value(mapInfo.getName())
		.key("bazc")
		.value(mapInfo.getBasesCount())
		.key("lati")
		.value(mapInfo.getCenter_lat())
		.key("longi")
		.value(mapInfo.getCenter_long())
		.key("zoom")
		.value(mapInfo.getWeb_zoom());

		//Bases
		str.key("bases").array();
		synchronized (world) {
			for (GameBase b : world.bases) {
				str.object();

				str.key("id");
				str.value(b.id());
				str.key("cname");
				str.value(b.cityname);
				str.key("lati");
				str.value(converter.getLatFromY(b.lastPosition.y()));
				str.key("longi");
				str.value(converter.getLongFromX(b.lastPosition.x()));
				str.key("oid");
				str.value(b.modelView().ownerId());

				str.endObject();
			}
		}
		
		str.endArray().endObject().endObject();
		String mapInfoString = str.toString();
		log.debug("Sending map info to client: "+mapInfoString);
		conn.send(mapInfoString);
	}

	public void sendGameInfoToAll()
	{
		JSONStringer stringer = new JSONStringer();
		stringer.object().key("snap");
		stringer.object();
		
		stringer.key("time");
		stringer.value(game.getTimeLeft());
		
		//Scores
		stringer.key("players").array();
		for (int i = 0 ; i < game.getNbPlayers() ; i++) {			
			stringer.object();
			stringer.key("name");
			stringer.value(npm.getPlayer(i).getNickname());
			stringer.key("score");
			stringer.value(game.getScores().getScore(i+1));
			stringer.endObject();
		}
		stringer.endArray();

		synchronized (world) {
			//Bases
			stringer.key("bases").array();
			for (GameBase b : world.bases) {
				stringer.object();
				stringer.key("id");
				stringer.value(b.id());
				stringer.key("ownerid");
				stringer.value(b.modelView().ownerId());
				stringer.endObject();
			}
			stringer.endArray();

			//Planes
			stringer.key("planes").array();
			for (GamePlane p : world.planes) {
				stringer.object();

				stringer.key("id");
				stringer.value(p.id());

				stringer.key("latitude");
				stringer.value(converter.getLatFromY(p.lastPosition.y()));

				stringer.key("longitude");
				stringer.value(converter.getLongFromX(p.lastPosition.x()));

				stringer.key("ownerid");
				stringer.value(p.modelView().ownerId());

				stringer.key("health");
				stringer.value(p.modelView().health());
				
				stringer.key("fuel");
				stringer.value(p.modelView().fuelInTank());

				//stringer.key("radar");
				//stringer.value(p.modelView().radarRange());

				stringer.key("rotation");
				stringer.value(p.modelView().rotation());

				//stringer.key("speed");
				//stringer.value(p.modelView().speed());

				//stringer.key("state");
				//stringer.value(p.modelView().state());

				stringer.endObject();
			}
		}
		
		stringer.endArray().endObject().endObject();

		String gameInfoString = stringer.toString();
		//log.debug("Sending snapshot to clients: "+gameInfoString);
		for (WebSocket ws : authorized) 
			ws.send(gameInfoString);
	}

	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {

		if(authorized.size() > NetworkSettings.maxConnection)
		{
			log.error("Connection refused for "+conn.getRemoteSocketAddress().toString()+" : too many clients");
			conn.send(new JSONStringer()
			.object()
			.key("error")
			.value("Too many clients connected to the server")
			.endObject()
			.toString());
			conn.close();
		}
		else
		{
			if(!authorized.contains(conn))
			{
				log.info("Opening web connection for "+conn.getRemoteSocketAddress().toString());
				authorized.add(conn);
				conn.send(new JSONStringer()
				.object()
				.key("error")
				.value("You are connected")
				.endObject()
				.toString());
				log.debug("Sending map info to client "+conn.getRemoteSocketAddress().toString());
				sendMapInfo(conn);
				sendGameInfoToAll();
			}
		}
	}

	private class AutoSender extends Thread
	{
		boolean running = true;

		public AutoSender()
		{
			start();
		}

		@Override
		public void run() {
			super.run();
			while(running)
			{
				if(authorized.size() > 0)
					sendGameInfoToAll();
				try {
					sleep(NetworkSettings.timeUpdate);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		public void stopAS()
		{
			log.debug("Shutting down auto sender thread...");
			running = false;
		}
	}

	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		authorized.remove(conn);
		log.debug("Connection with "+ conn.getRemoteSocketAddress().toString()+" is now closed");
	}

	@Override
	public void onError( WebSocket conn, Exception ex ) {
		autoSender.stopAS();
		log.error("Error:" );
		ex.printStackTrace();
	}

	@Override
	public void onMessage( WebSocket conn, String message ) {
		log.debug("Message from "+conn.getRemoteSocketAddress()+" : "+message);
	}

	@Override
	public void onMessage( WebSocket conn, ByteBuffer blob ) {
		log.debug("Message from "+conn.getRemoteSocketAddress()+" : "+blob);
	}

	public void stopWebInterface()
	{
		log.debug("Shutting down web interface...");
		autoSender.stopAS();
		try {
			this.stop();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*public static void main(String[] args) {
		Game mapInfo = null;
		World world = null;
		WebInterface ww = startWebInterface(mapInfo);
	}*/

}

