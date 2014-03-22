package network;

import java.util.ArrayList;
import java.util.HashMap;

import org.java_websocket.WebSocket;

/**
 * Manage spectator authentication and opened websockets 
 * @author nicolas
 *
 */
public class SpectatorManager {

	private HashMap<String,String> authorizedNames;
	private ArrayList<Spectator> authorized;

	public SpectatorManager()
	{
		this.authorized = new ArrayList<SpectatorManager.Spectator>();
		this.authorizedNames = new HashMap<String,String>();
		authorizedNames.put("rennes","r495!");
		authorizedNames.put("rouen","r993@");
		authorizedNames.put("lyon","l781$");
		authorizedNames.put("toulouse","t816&");
		authorizedNames.put("cvloire","c631#");
		authorizedNames.put("strasbourg","s274=");
		authorizedNames.put("admin","chuck666*");
	}

	protected class Spectator
	{
		WebSocket ws;
		String name;
		
		Spectator(String name, String pass, WebSocket ws)
		{
			this.name = name;
			this.ws = ws;
		}

	}



}
