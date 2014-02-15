import network.BridgeHandler;
import network.BridgeJavaServer;
import game.Sim;
import players.PlayerManager;
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
				
		System.out.println("--- CODINSA 2014 --- Plane simulation server");
				
		//Prepares plane simulation
		Sim planeSim = new Sim(2);
		
		//Is ready to manage new players
		PlayerManager pmanager = new PlayerManager(planeSim);
		
		//Is ready to manage clients
		BridgeHandler handler = new BridgeHandler(planeSim,pmanager);
		
		//Starting the server to accept players
		BridgeJavaServer server = BridgeJavaServer.startServer(port,handler);
				
		//FIXME
		Displayer disp = new Displayer();
		new MainWindow(disp, planeSim); //and the server to stop when clicking on cross?
		
		//Waiting for the server to terminate
		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

