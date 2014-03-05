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

public class WebInterface extends WebSocketServer {
	
	public WebInterface( InetSocketAddress address, Draft d) {
		super(address,Collections.singletonList(d));
	}
	
	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {
		System.out.println( "Opened connection");
	}
	
	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		System.out.println( "closed" );
	}
	
	@Override
	public void onError( WebSocket conn, Exception ex ) {
		System.out.println( "Error:" );
		ex.printStackTrace();
	}
	
	@Override
	public void onMessage( WebSocket conn, String message ) {
		conn.send( message );
		System.out.println("Message: "+message);
	}
	
	@Override
	public void onMessage( WebSocket conn, ByteBuffer blob ) {
		conn.send( blob );
		System.out.println("Message: "+blob);
	}
	
	
	public static void main(String[] args) {
		try {
			InetAddress ip = InetAddress.getByName("192.168.1.89");
			int port = 14588;
			InetSocketAddress isa = new InetSocketAddress(ip, port);
			Draft d = new Draft_17();
			System.out.println(isa);
			WebInterface wi = new WebInterface(isa,d);
			wi.start();
			System.out.println("Server listening");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
}
