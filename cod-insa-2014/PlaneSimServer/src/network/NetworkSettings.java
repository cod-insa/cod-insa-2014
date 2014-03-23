package network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkSettings {

	//Port on which one the data updater is connected (synchronous server)
	public static int port = 9090;
	//port+1 for the asynchronous server (the command receiver)
	
	public static String ip_address = "localhost";
	
	public static final Logger log = LoggerFactory.getLogger("Network");
	
	//Settings for WebInterface connection
	public final static String webip = "192.168.1.89";	//localhost address but not "localhost"
	public final static int webport = 14588;
	public final static int maxConnection = 40;	//clients simultaneously connected
	public final static int timeUpdate = 1000;	//ms
	
}
