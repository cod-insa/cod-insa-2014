import network.BridgeHandler;
import network.BridgeJavaServer;
import game.Sim;
import players.NetworkPlayer;
import players.Player;
import players.PlayerManager;
import control.Controller;
import display.Displayer;
import display.MainWindow;


public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//TODO passing port and number of players by argument
		int port = 9090;
		int nbplay = 2;

		//FIXME
		//Displayer d = new Displayer();
		Controller c = new Controller(50);
		//Player ai = new NetworkPlayer("localhost", 1515);
		// new LocalHumanPlayer()
		//c.addPlayer(ai);
		//new MainWindow(d, s, c); //and the server to stop it when clicking on cross?
		
		
		
		System.out.println("--- CODINSA 2014 --- Plane simulation server");
		
		//Prepares plane simulation
		Sim planeSim = new Sim(2);
		
		//Is ready to manage new players
		PlayerManager pmanager = new PlayerManager(planeSim);
		
		//Is ready to manage clients
		BridgeHandler handler = new BridgeHandler(pmanager);
		
		//Starting the server to accept players
		BridgeJavaServer server = BridgeJavaServer.startServer(port,handler);
		
		//Shuts down the server
		//server.stopServer();
		
	}
}

