package players;

import game.Game;
import game.World;
import game.World.Snapshot;
import genbridge.Bridge;
import genbridge.CommandReceiver;
import genbridge.ConnectionData;
import genbridge.Data;
import genbridge.InitData;
import genbridge.LandCommandData;
import genbridge.MoveCommandData;
import genbridge.Response;
import genbridge.WaitCommandData;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

import network.CommandChecker;
import network.DataPreparer;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import command.Command;
import command.LandCommand;
import command.MoveCommand;
import command.WaitCommand;
import common.Nullable;

import control.CommandMaker;

/**
 * @author LP
 * @author NV
 *
 */

/*
 TODO: it's convenient for the IA to be sending a stream of decisions rather than one big packet of decisions all at once
 so if it is too slow, it can still send *some* data rather than nothing
 we will accumulate the decisions here and return them all in a list, following IA's interface
*/

public class NetworkPlayer extends Player {
	
	private Queue<Command> commands = new LinkedList<Command>();
	
//	private int playerID;
//	private String teamName;
	private int lastNumFrame = -1;
//	
//	private List<BaseModel> bases;
//	private List<PlaneModel> planes;
//	
//	private boolean isWaitingDataUpdate;
//	private Object waitData;

	public final String name;
	String nickname = null;
	
	public final int connectionId;
	
	TSimpleServer dataSender, commandsReceiver;
	Thread dataSenderThead, commandsReceiverThread;
	
	NetworkPlayerManager manager;
	
	World world;
	
	volatile boolean disconnected = false;
	
	//boolean connected = false;
	
	
	/**
	 * Assumptions:
	 * 
	 * 
	 * 
	 * 
	 */
	class DataHandler implements Bridge.Iface {
		
		@Override
		public ConnectionData connect(String nickname) throws TException {
			
//			if (connected)
//				System.err.println("");
//			else
//			synchronized (manager)
//			{
//				manager.setConnected(this);
//			}
			NetworkPlayer.this.nickname = nickname;
			
			manager.notifyConnect(NetworkPlayer.this);
			
			return new ConnectionData(connectionId,id);
		}
		
		
		
		
		/**
		 * Returning null means we are disconnected
		 * 
		 */
		public Nullable<Snapshot> waitForNextSnapshot() {
			
			Snapshot s = world.getCurrentSnapshot();
			
			while (s == null || lastNumFrame >= s.id)
			{
//				System.out.println("Player "+ name+" is waiting");
				
				synchronized(world) {
					try {
						world.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
//				System.out.println("Player "+ name+" is unblocked");
				
				if (disconnected)
					//return DataUpdater.prepareEndofGame();
					return new Nullable<>(null);
				
				s = world.getCurrentSnapshot();
			}
			
			lastNumFrame = s.id;
			
//			System.out.println("Player "+ name+" is being served");
			
			return new Nullable<>(s);
		}
		
		@Override
		public Data retrieveData(int idConnection) throws TException {
			Nullable<Snapshot> s = waitForNextSnapshot();
			if (s.none()) {
				assert disconnected;
				return DataPreparer.prepareEndofGame();
			}
			return DataPreparer.prepareData(s.get(),id);
		}

		@Override
		public InitData retrieveInitData(int idConnection) throws TException {
			return DataPreparer.prepareInitData(waitForNextSnapshot());
		}
		
	}
	
	//<C extends CommandData> treat(C cmd)
	
	class CommandsHandler implements CommandReceiver.Iface {
		
		@Override
		public Response sendMoveCommand(MoveCommandData cmd, int idConnection) throws TException {
			
			MoveCommand mc = (MoveCommand) CommandMaker.make(cmd);
			Response r = CommandChecker.checkMoveCommand(mc,world.getCurrentSnapshot());
			
			if (r.code == 0)
				addCommand(mc);
			// else
			// 		log ?

			return r;
		}
		
		@Override
		public Response sendWaitCommand(WaitCommandData cmd, int idConnection) throws TException {
			WaitCommand wc = (WaitCommand) CommandMaker.make(cmd);
			Response r = CommandChecker.checkWaitCommand(wc,world.getCurrentSnapshot());
			
			if (r.code == 0)
				addCommand(wc);
			// else
			// 		log ?

			return r;
		}

		@Override
		public Response sendLandCommand(LandCommandData cmd, int idConnection)
				throws TException {
			LandCommand lc = (LandCommand) CommandMaker.make(cmd);
			Response r = CommandChecker.checkLandCommand(lc,world.getCurrentSnapshot());
			
			if (r.code == 0)
				addCommand(lc);
			// else
			// 		log ?

			return r;
		}
		
	}
	
	public NetworkPlayer(NetworkPlayerManager manager, int id, String name, int dataSenderPort, int commandsReceiverPort, World world)
			throws TTransportException
	{
//		this.teamName = name;
//		this.playerID = name.hashCode();
//		this.frameNumber = 0;
//		this.bases = new ArrayList<BaseModel>();
//		this.planes = new ArrayList<PlaneModel>();
//		this.isWaitingDataUpdate = false;
//		this.waitData = new Object();
		
		this.manager = manager;
		this.name = name;
		this.world = world;
		//this.id = Util.rand.nextInt();
		this.connectionId = id;
		
		dataSender = new TSimpleServer(
				new TServer.Args(new TServerSocket(dataSenderPort)).processor(
						new Bridge.Processor<>(new DataHandler())
					)
			);
		//System.out.println("A");
		
		commandsReceiver = new TSimpleServer(
				new TServer.Args(new TServerSocket(commandsReceiverPort)).processor(
						new CommandReceiver.Processor<>(new CommandsHandler())
					)
			);
		
		
		dataSender.setServerEventHandler(new TServerEventHandler() {
			@Override
			public ServerContext createContext(TProtocol arg0, TProtocol arg1) {
				//System.err.println("A");
				return null;
			}
			@Override
			public void deleteContext(ServerContext arg0, TProtocol arg1, TProtocol arg2) {
				//System.err.println("B");
				
				disconnect();
				
			}
			@Override
			public void preServe() {
				//System.err.println("C");
				
			}
			@Override
			public void processContext(ServerContext arg0, TTransport arg1, TTransport arg2) {
				//System.err.println("D");
			}
		});
		
		// Note: use the same handler for commandsReceiver? (useful?)
		
		
		serve();
		
	}
	
	void serve() {
		
		dataSenderThead = new Thread() {
			public void run() {
				dataSender.serve();
				System.out.println("Player "+name+" data sender terminated");
				disconnect();
			};
		};
		dataSenderThead.start();
		
		commandsReceiverThread = new Thread() {
			public void run() {
				commandsReceiver.serve();
				System.out.println("Player "+name+" com receiver terminated");
				disconnect();
			}
		};
		commandsReceiverThread.start();
		
	}
	
	void disconnect() {
		
//		boolean wasDisconnected = disconnected;
//		
//		//System.out.println(">>> DISCO");
//		
//		disconnected = true;
//		
//		if (dataSender.isServing())
//			dataSender.stop();
//		
//		if (commandsReceiver.isServing())
//			commandsReceiver.stop();
//		
//		if (!wasDisconnected)
//			manager.notifyDisconnect(this);
		
		if (!disconnected) {
			
			disconnected = true;
			
			if (dataSender.isServing())
				dataSender.stop();
			
			if (commandsReceiver.isServing())
				commandsReceiver.stop();
			
			manager.notifyDisconnect(this);
			
		}
		
	}
	void join() {
		
		while(dataSenderThead.isAlive() || commandsReceiverThread.isAlive())
			try {
				dataSenderThead.join();
				commandsReceiverThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
	}
	
	
	
//	/**
//	 * Add a command to the FIFO
//	 */
//	 public boolean addCommand(Command c) {
//		 synchronized (commands) {
//			 return commands.offer(c);
//			 //commands.add(c);
//		}
//	 }
//	 
//	 /**
//	  * Remove and return the head of the FIFO ueue
//	  */
//	 public Command getNextCommand() {
//		 Command c = null;
//		 synchronized (commands) {
//			 c = commands.remove();
//		}
//		 return c;
//	 }
	
	void addCommand (Command c) {
		synchronized (commands)
		{
			
			//System.out.println("receiving cmd: "+c);
			
			commands.add(c);
		}
	}

	 @Override
	 /**
	  * Returns all the commands in the FIFO queue and flushes it.
	  */
	public Queue<Command> flushCommands() {
		Queue<Command> coms = commands;
		synchronized (commands)
		{
//			coms = new LinkedList<Command>(commands);
//			commands.clear();
			commands = new ArrayDeque<>();
		}
		return coms;
	}

	public String getNickname() {
		return nickname;
	}
	 
}




 
