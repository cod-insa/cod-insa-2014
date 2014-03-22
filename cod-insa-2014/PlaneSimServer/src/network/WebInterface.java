package network;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONStringer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebInterface extends WebSocketServer {
	
	static final Logger log = LoggerFactory.getLogger(WebInterface.class);
	
	private WebInterface( InetSocketAddress address, Draft d) {
		super(address,Collections.singletonList(d));
	}
	
	/**
	 * Call this method to start the web interface server
	 * Settings are in NetworkSettings.java
	 * It does not like "localhost"
	 */
	public static WebInterface startWebInterface()
	{
		WebInterface wi = null;
		try {
			InetAddress ip = InetAddress.getByName(NetworkSettings.webip);
			int port = NetworkSettings.webport;
			InetSocketAddress isa = new InetSocketAddress(ip, port);
			Draft d = new Draft_17();
			wi = new WebInterface(isa,d);
			wi.start();
			log.info("Server listening");
		} catch (UnknownHostException e) {
			log.error("WebSocketServer cannot start");
		}
		return wi;
	}
	
	
	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {
		log.info("Opening connection for "+conn.getRemoteSocketAddress().toString());
		log.info("Authentication is required! Send me your ID/pass!");
	}
	
	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		log.info("Connection with "+ conn.getRemoteSocketAddress().toString()+" is now closed");
	}
	
	@Override
	public void onError( WebSocket conn, Exception ex ) {
		log.error("Error:" );
		ex.printStackTrace();
	}
	
	@Override
	public void onMessage( WebSocket conn, String message ) {
		conn.send( message );
		log.debug("Message: "+message);
		
		
		 String myString = new JSONStringer()
	     .object()
	         .key("JSON")
	         .value("Hello, World!")
	     .endObject()
	     .toString();
		 
		 
		//parse int on message
		
		//if 10 : connect info is coming
		
		//if 20 : asking for fresh info
		
		//if 30 : disconnect
		
		
		/*
		  int basesCount;
			double center_lat;
			double center_long;
			int web_zoom;
		 */
		
	}
	
	@Override
	public void onMessage( WebSocket conn, ByteBuffer blob ) {
		log.debug("Message: "+blob);
	}
	
	
	
	public static void main(String[] args) {
		WebInterface ww = startWebInterface();
	}
	
	
	
}

