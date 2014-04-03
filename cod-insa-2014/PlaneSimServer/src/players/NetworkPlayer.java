package players;

import common.Couple;
import game.Game;
import game.World;
import game.World.Snapshot;
import genbridge.AttackCommandData;
import genbridge.Bridge;
import genbridge.CommandReceiver;
import genbridge.ConnectionData;
import genbridge.Data;
import genbridge.DropMilitarsCommandData;
import genbridge.FillFuelTankCommandData;
import genbridge.FollowCommandData;
import genbridge.InitData;
import genbridge.LandCommandData;
import genbridge.LoadResourcesCommandData;
import genbridge.MoveCommandData;
import genbridge.Response;
import genbridge.StoreFuelCommandData;
import genbridge.WaitCommandData;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

import network.DataPreparer;
import static network.NetworkSettings.log;

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
	
	private Queue<Command> commands = new LinkedList<>();
	
//	private int playerID;
//	private String teamName;
	private int lastNumFrame = -1;
//	
//	private List<BaseModel> bases;
//	private List<PlaneModel> planes;²²
//	
//	private boolean isWaitingDataUpdate;
//	private Object waitData;

	public final String name;
	String nickname = null;
	
	public final int connectionId;
	
	TSimpleServer dataSender, commandsReceiver;
	Thread dataSenderThread, commandsReceiverThread;

	NetworkPlayerManager manager;
	
	Game game;
	World world;
	
	volatile boolean disconnected = false;
	
	//boolean connected = false;
	
	
	/**
	 * Assumptions:
	 * 
	 * TODO
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
//						e.printStackTrace();
						log.error("Snapshot waiting interrupted!", e);
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
		
		public Response process(Couple<Nullable<Command>,Response> cmd_r) {
			if (cmd_r.first.hasSome())
				addCommand(cmd_r.first.get());
			else
				log.warn("Invalid command. Checking failed with response: " + cmd_r.second);
			return cmd_r.second;
		}
		
		@Override
		public Response sendMoveCommand(MoveCommandData cmdDat, int idConnection) throws TException {
//			MoveCommand mc = (MoveCommand) CommandMaker.make(cmdDat);
//			Response r = CommandChecker.checkMoveCommand(mc,world.getCurrentSnapshot());
//			
//			if (r.code == 0)
//				addCommand(mc);
//			// else
//			// 		log ?
//
//			return r;
			
			return process(CommandMaker.make(cmdDat, world.getCurrentSnapshot()));
			
		}
		
		@Override
		public Response sendWaitCommand(WaitCommandData cmdDat, int idConnection) throws TException {

			return process(CommandMaker.make(cmdDat, world.getCurrentSnapshot()));

		}

		@Override
		public Response sendLandCommand(LandCommandData cmdDat, int idConnection) throws TException {

			return process(CommandMaker.make(cmdDat, world.getCurrentSnapshot()));

		}

		@Override
		public Response sendFollowCommand(FollowCommandData cmdDat, int idConnection) throws TException {

			//CommandMaker.make(cmdDat);

			//Couple<Nullable<Command>,Response> c;
			
			// TODO
			//return process(CommandMaker.make(cmdDat, world.getCurrentSnapshot()));
			return null;
			
//			Either<Command,Response> r = ...;
//			
//			try { r.match(); }
//			catch (Either<Command, ?>.Alt1 alt)
//			{
//				System.out.println(alt.value);
//			}
//			catch (Either<?, Response>.Alt2 alt)
//			{
//				System.out.println(alt.value.code);
//			}
//			
//			if (r.isAlt1())
//			{
//				System.out.println(r.alt1().value);
//			} else if (r.isAlt2())
//			{
//				System.out.println(r.alt2().value);
//			}
			
			//return new Response(Command.ERROR_COMMAND,"Command not implemented yet !");
		}

		@Override
		public Response sendAttackCommand(AttackCommandData cmdDat, int idConnection) throws TException {

			// TODO
			//return process(CommandMaker.make(cmdDat, world.getCurrentSnapshot()));
			return null;

			//return new Response(Command.ERROR_COMMAND,"Command not implemented yet !");
		}

		@Override
		public Response sendDropMilitarsCommand(DropMilitarsCommandData cmd,
				int idConnection) throws TException {

			return new Response(Command.ERROR_COMMAND,"Command not implemented yet !");
		}

		@Override
		public Response sendStoreFuelCommand(StoreFuelCommandData cmd,
				int idConnection) throws TException {
		
			return new Response(Command.ERROR_COMMAND,"Command not implemented yet !");
		}

		@Override
		public Response sendFillFuelTankCommand(FillFuelTankCommandData cmd,
				int idConnection) throws TException {
			
			return new Response(Command.ERROR_COMMAND,"Command not implemented yet !");
		}

		@Override
		public Response sendLoadResourcesCommand(
				LoadResourcesCommandData cmd, int idConnection)
				throws TException {
			
			return new Response(Command.ERROR_COMMAND,"Command not implemented yet !");
		}
		
	}
	
	public NetworkPlayer(NetworkPlayerManager manager, int id, String name, int dataSenderPort,
						 int commandsReceiverPort, Game game)
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
		this.game = game;
		this.world = game.getWorld();
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
		
		dataSenderThread = new Thread() {
			public void run() {
				dataSender.serve();
				log.debug("Player "+name+" data sender terminated");
				disconnect();
			};
		};
		dataSenderThread.start();
		
		commandsReceiverThread = new Thread() {
			public void run() {
				commandsReceiver.serve();
				log.debug("Player "+name+" com receiver terminated");
				disconnect();
			}
		};
		commandsReceiverThread.start();
		
	}

    public boolean isConnected() {
        return !disconnected;
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
		
		while(dataSenderThread.isAlive() || commandsReceiverThread.isAlive())
			try {
				dataSenderThread.join();
				commandsReceiverThread.join();
			} catch (InterruptedException e) {
//				e.printStackTrace();
				log.error("Joining interrupted!", e);
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
		manager.notifyTimeout(this, coms.size() == 0);
		return coms;
	}

	public String getNickname() {
		return nickname;
	}
	 
}




 
