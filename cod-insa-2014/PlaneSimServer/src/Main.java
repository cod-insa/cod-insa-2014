import game.Sim;

import org.apache.thrift.transport.TTransportException;

import players.NetworkPlayer;
import players.NetworkPlayerManager;

import common.Event;
import common.Function;

import control.Controller;
import display.ConnectionWindow;
import display.Displayer;
import display.MainWindow;

public class Main {
	
	static volatile boolean offline = false;
	
	static void shutOff() {
		offline = true;
		synchronized(Main.class) { Main.class.notify(); };
	}
	
	/**
	 * @param args
	 * @throws TTransportException 
	 * @throws InterruptedException 
	 * 
	 * 
	 */
	public static void main(String[] args) throws TTransportException, InterruptedException {
		
		try {
		
			if (args.length < 1 || args.length%2 != 0)
				printUsageAndExit(-1);
			
			if (args.length == 1 && args[0].equals("help") || args[0].equals("--help") || args[0].equals("-h"))
				printUsageAndExit(0);
			
			System.out.println("--- CODINSA 2014 --- Plane simulation server ---");
			
			new Controller(50);
			
			int nbplay = args.length/2;
			
			final Displayer disp = new Displayer();
			final Sim planeSim = new Sim(disp, nbplay);
			
			final NetworkPlayerManager npm = new NetworkPlayerManager(planeSim.world);
			
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
			
	//		npm.waitForConnections(new Event() {
	//			
	//			public void call() {
	//				System.out.println("++");
	//			}
	//			
	//		},
			
			//planeSim.start();
			
	//		npm.cancelWaitForConnections();
	//		npm.diconnect();
			
			//if(0==0) return;
			
			final MainWindow[] mainWindow = new MainWindow[]{null}; // one single mainWindow
			
			final ConnectionWindow cw = new ConnectionWindow(npm, new Event() {
				public void call() {
					if (mainWindow[0] == null) {
						npm.cancelWaitForConnections();  // call npm.cancelWaitForConnections
						npm.diconnect();
						//planeSim.stop();
						shutOff();
					}
				}
			});
			
			//if(0==0) return;
			
			npm.registerListeners(new Function.Void<NetworkPlayer>() {
				public void exec (NetworkPlayer p) { cw.notifyConnect(p); }
			}, new Function.Void<NetworkPlayer>() {
				public void exec (NetworkPlayer p) { cw.notifyDisconnect(p); }
			});
			
			npm.waitForConnections(new Event() {
				
				public void call() {
					
					cw.notifyGameStarted();
					
					////////////////////////
					//cw.close();
					////////////////////////
					
			//		//TODO passing port and number of players by argument
			//		int nbplay = 1;
					
					/*
					Displayer disp = new Displayer();
					
					//Prepares plane simulation
					final Sim planeSim = new Sim(disp, npm.getNbPlayers());
					*/
					
					
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
					mainWindow[0] = new MainWindow(disp, planeSim, new Event() {
						public void call() {
							planeSim.stop();
							npm.diconnect();
							shutOff();
						}
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
			
			while (!offline)
				synchronized(Main.class) { Main.class.wait(); };
			
			cw.close();
			
			//System.out.println(Thread.getAllStackTraces().size());
			
			//System.out.println(Frame.getFrames().length);
	//		Frame[] frames = Frame.getFrames();
	//		if (frames.length > 0) {
	//			//Window window = SwingUtilities.windowForComponent(
	//			System.err.println("Warning: not all frames were disposed ("+frames.length+"). Disposing them now.");
	//			//System.out.println(frames[0].getState());
	//			//System.out.println(frames[0].isActive());
	//			for (Frame f: frames) {
	//				System.out.println(f.isActive());
	//				f.dispose();
	//				System.out.println(f.isActive());
	//			}
	//			//System.out.println(frames[0].isActive());
	//			//frames = Frame.getFrames();
	//		}
			
	//		for (Frame f: Frame.getFrames()) {
	//			if (f.isActive()) {
	//				System.err.println("Warning: a frame was not disposed: \""+f.getTitle()+"\". Disposing it now.");
	//				System.out.println("Warning: a frame was not disposed: \""+f.getTitle()+"\". Disposing it now.");
	//				f.dispose();
	//				if (f.isActive()) {
	//					System.err.println("Failed to dispose it");
	//					System.out.println("Failed to dispose it");
	//				}
	//			}
	//		}
			
			/////////////////////////////////
			//npm.diconnect();
			/////////////////////////////////
			
	//		System.out.println(Thread.activeCount());
	//		//Thread.dumpStack();
	//		System.out.println(Thread.getAllStackTraces().size());
	//		
	//		for (Thread t: Thread.getAllStackTraces().keySet()) {
	//			
	//			System.out.println(">> "+t.getName());
	//			
	//			if (t.equals(Thread.currentThread()))
	//				continue;
	//			
	//			System.out.println(t.isAlive());
	//			
	//			t.interrupt();
	//			
	////			try {
	////				t.join();
	////			} catch (InterruptedException e) {
	////				e.printStackTrace();
	////			}
	//		}
		
				System.out.println("Gracefully exitting the program...");
		}
		catch (Exception e) {
			System.err.println("Uncaught exception in PlaneSimServer.Main:");
			e.printStackTrace(System.err);
			//throw e;
			System.exit(-1);
		}
		
	}
	
	public static void printUsageAndExit(int exitCode) {
		(exitCode==0? System.out: System.err).println("Usage:\n" +
//				"\tjava Main playerName1 ip:port playerName2 ip:port ..."
				"\tjava Main playerName1 port1 playerName2 port2 ..."
			);
		System.exit(exitCode);
	}
}







