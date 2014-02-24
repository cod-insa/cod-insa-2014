import game.Sim;
import network.BridgeHandler;
import network.BridgeJavaServer;
import network.CommandRJavaServer;
import network.CommandReceiverHandler;
import players.PlayerManager;
import display.Displayer;
import display.MainWindow;

public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//TODO passing port and number of players by argument
		int nbplay = 1;
				
		System.out.println("--- CODINSA 2014 --- Plane simulation server ---");
		
		Displayer disp = new Displayer();
		
		//Prepares plane simulation
		Sim planeSim = new Sim(disp, nbplay);
		
		//Is ready to manage new players
		PlayerManager pmanager = new PlayerManager(planeSim);
		
		//Is ready to manage clients
		BridgeHandler handler = new BridgeHandler(planeSim, pmanager);							//synchronous handler
		CommandReceiverHandler comHandler = new CommandReceiverHandler(planeSim, pmanager);		//Asynchronous handler
		
		//Starting the server to accept players
		BridgeJavaServer server = BridgeJavaServer.startServer(handler);						//synchronous server
		CommandRJavaServer server2 = CommandRJavaServer.startServer(comHandler);				//Asynchronous server
		
		/* Pourquoi avoir mis ca la ???
		//FIXME
		Displayer disp = new Displayer();
		*/
		new MainWindow(disp, planeSim); //and the server to stop when clicking on cross?
		
		System.out.println("Waiting for players to connect...");
		
		//Waiting for the server to terminate
		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

