import network.BridgeJavaServer;
import game.Sim;
import players.NetworkPlayer;
import players.Player;
import control.Controller;
import display.Displayer;
import display.MainWindow;


public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("--- CODINSA 2014 --- Plane simulation server");
		
		BridgeJavaServer server = BridgeJavaServer.startServer(9090);
		
		Player ai = new NetworkPlayer("localhost", 1515);
		
		Displayer d = new Displayer();
		
		Sim s = new Sim(d);
		
		Controller c = new Controller(50);
		
		// new LocalHumanPlayer()
		c.addPlayer(ai);
		
		new MainWindow(d, s, c);
		
	}

}

