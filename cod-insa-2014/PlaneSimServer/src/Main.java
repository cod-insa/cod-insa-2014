import game.Sim;

import org.apache.thrift.transport.TTransportException;

import players.NetworkPlayerManager;

import common.Event;

import control.Controller;
import display.Displayer;
import display.MainWindow;

public class Main {
	
	/**
	 * @param args
	 * @throws TTransportException 
	 * 
	 * 
	 */
	public static void main(String[] args) throws TTransportException {
		
		if (args.length < 1 || args.length%2 != 0)
			printUsageAndExit(-1);
		
		if (args.length == 1 && args[0].equals("help") || args[0].equals("--help") || args[0].equals("-h"))
			printUsageAndExit(0);
		
		System.out.println("--- CODINSA 2014 --- Plane simulation server ---");
		
		new Controller(50);
		
		final NetworkPlayerManager npm = new NetworkPlayerManager();
		
		for (int i = 0; i < args.length; i+= 2) {
//			String[] ip_port = args[i+1].split(":");
//			try { npm.addPlayer(args[i], ip_port[0], Integer.parseInt(ip_port[1])); }
//			catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
//			{ printUsageAndExit(-1); }
			//System.out.println(args[i]);
			try { npm.addNewPlayer(args[i], Integer.parseInt(args[i+1])); }
			catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
			{ printUsageAndExit(-1); }
		}
		
		npm.waitForConnections(new Event() {
			
			public void call() {
		
		//		//TODO passing port and number of players by argument
		//		int nbplay = 1;
				
				
				Displayer disp = new Displayer();
				
				//Prepares plane simulation
				final Sim planeSim = new Sim(disp, npm.getNbPlayers());
				
		//		//Is ready to manage new players
		//		NetworkPlayerManager pmanager = new NetworkPlayerManager(planeSim);
		//		
		//		//Is ready to manage clients
		//		BridgeHandler handler = new BridgeHandler(planeSim, pmanager);							//synchronous handler
		//		CommandReceiverHandler comHandler = new CommandReceiverHandler(planeSim);		//Asynchronous handler
		//		
		//		//Starting the server to accept players
		//		BridgeJavaServer server = BridgeJavaServer.startServer(handler);						//synchronous server
		//		CommandRJavaServer server2 = CommandRJavaServer.startServer(comHandler);				//Asynchronous server
				
				/* Pourquoi avoir mis ca la ???
				//FIXME
				Displayer disp = new Displayer();
				*/
				new MainWindow(disp, planeSim, new Event() {
					public void call() {
						planeSim.stop();
						npm.diconnect(); }
				}); // TODONE: and the server to stop when clicking on cross?
				
				//System.out.println("Waiting for players to connect...");
				
				//Waiting for the server to terminate
		//		try {
		//			server.join();
		//		} catch (InterruptedException e) {
		//			e.printStackTrace();
		//		}
				
				planeSim.start();
				
			}
			
		});
		
		/////////////////////////////////
		//npm.diconnect();
		/////////////////////////////////
		
	}
	
	public static void printUsageAndExit(int exitCode) {
		(exitCode==0? System.out: System.err).println("Usage:\n" +
//				"\tjava Main playerName1 ip:port playerName2 ip:port ..."
				"\tjava Main playerName1 port1 playerName2 port2 ..."
			);
		System.exit(exitCode);
	}
}







