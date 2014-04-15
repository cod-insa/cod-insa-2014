import game.Game;
import game.World;
import network.WebInterface;

import org.apache.thrift.transport.TTransportException;

import players.NetworkPlayer;
import players.NetworkPlayerManager;
import common.Event;
import control.Controller;
import display.ConnectionWindow;
import display.Displayer;
import display.MainWindow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	static volatile boolean offline = false;
	
	static void shutOff() {
		offline = true;
		synchronized(Main.class) { Main.class.notify(); };
	}
	
	static final Logger log = LoggerFactory.getLogger(Main.class);
	
	/**
	 * @param args
	 * @throws TTransportException 
	 * @throws InterruptedException 
	 * 
	 * 
	 */
	public static void main(String[] args) throws TTransportException, InterruptedException {
		
		try {
		
			if (args.length < 1 || args.length%2 == 1)
				printUsageAndExit(-1);
			
			if (args.length == 1 && args[0].equals("help") || args[0].equals("--help") || args[0].equals("-h"))
				printUsageAndExit(0);
			
//			System.out.println("--- CODINSA 2014 --- Plane simulation server ---");
			log.info("--- CODINSA 2014 --- Plane simulation server ---");
			
			new Controller(50);
			
			int nbplay = (args.length-2)/2;
			
			long seconds = 0;
			try {
				seconds = Long.parseLong(args[1]);
			}
			catch(Exception e)
			{
				printUsageAndExit(-1);
			}
			
			final Displayer disp = new Displayer();
			final Game planeSim = new Game(disp, nbplay, args[0], seconds);
			
//			final NetworkPlayerManager npm = new NetworkPlayerManager(planeSim.getWorld());
			final NetworkPlayerManager npm = new NetworkPlayerManager(planeSim);
			
			for (int i = 2; i < args.length; i+= 2) {
				try { npm.addNewPlayer(args[i], Integer.parseInt(args[i+1])); }
				catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
				{ printUsageAndExit(-1); }
			}
			
			final WebInterface wi = WebInterface.startWebInterface(planeSim, npm);
			
			final MainWindow[] mainWindow = new MainWindow[]{null}; // one single mainWindow
			
			final ConnectionWindow cw = new ConnectionWindow(npm, new Event() {
				public void call() {
					if (mainWindow[0] == null) {
						npm.cancelWaitForConnections();  // call npm.cancelWaitForConnections
						npm.disconnect();
						//planeSim.stop();
						shutOff();
					}
				}
			});
			
			npm.setListener(new NetworkPlayerManager.Listener() {
				@Override
				public void onConnect(NetworkPlayer player) {
					cw.notifyConnect(player);
				}
				@Override
				public void onDisconnect(NetworkPlayer player) {
					cw.notifyDisconnect(player);
				}
				@Override
				public void onTimeoutStatusUpdate(NetworkPlayer player, boolean timingOut) {
					cw.notifyTimeoutStatus(player, timingOut);
				}
			});
			
			npm.waitForConnections(new Event() {
				
				public void call() {
					
					cw.notifyGameStarted();
					
					////////////////////////
					//cw.close();
					////////////////////////
					
			//		//TODO passing port and number of players by argument
			//		int nbplay = 1;
					
					mainWindow[0] = new MainWindow(disp, planeSim, new Event() {
						public void call() {
							planeSim.stop();
							npm.disconnect();
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
			wi.stopWebInterface();
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
			//npm.disconnect();
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
			
			log.info("Gracefully exiting the program...");
		}
		catch (Exception e) {
//			System.err.println("Uncaught exception in PlaneSimServer.Main:");
			log.error("Uncaught exception in PlaneSimServer.Main", e);
//			e.printStackTrace(System.err);
			//throw e;
			System.exit(-1);
		}
		
	}
	
	public static void printUsageAndExit(int exitCode) {
		(exitCode==0? System.out: System.err).println("Usage:\n" +
//				"\tjava Main playerName1 ip:port playerName2 ip:port ..."
				"\tjava Main mapName timeinseconds playerName1 port1 playerName2 port2 ..."
			);
		System.exit(exitCode);
	}
}







