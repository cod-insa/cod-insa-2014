import network.BridgeHandler;
import network.BridgeJavaServer;
import network.CommandRJavaServer;
import network.CommandReceiverHandler;
import game.Sim;
import genbridge.CommandReceiver;
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
		int nbplay = 1;
				
		System.out.println("--- CODINSA 2014 --- Plane simulation server");
				
		//Prepares plane simulation
		Sim planeSim = new Sim(nbplay);
		
		//Is ready to manage new players
		PlayerManager pmanager = new PlayerManager(planeSim);
		
		//Is ready to manage clients
		BridgeHandler handler = new BridgeHandler(planeSim,pmanager);
		
		CommandReceiverHandler comHandler = new CommandReceiverHandler(planeSim, pmanager);
		
		//Starting the server to accept players
		BridgeJavaServer server = BridgeJavaServer.startServer(port,handler);
		CommandRJavaServer server2 = CommandRJavaServer.startServer(port, comHandler);
				
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

