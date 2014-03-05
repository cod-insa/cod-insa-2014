package players;

import game.World;
import game.World.Snapshot;
import genbridge.Bridge;
import genbridge.CommandReceiver;
import genbridge.Data;
import genbridge.MoveCommandData;
import genbridge.Response;
import genbridge.WaitCommandData;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import command.Command;
import control.CommandMaker;
import control.DataUpdater;

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

public class NetworkPlayer implements Player {
	
	private Queue<Command> commands = new LinkedList<Command>();
	
//	private int playerID;
//	private String teamName;
	private int lastNumFrame;
//	
//	private List<BaseModel> bases;
//	private List<PlaneModel> planes;
//	
//	private boolean isWaitingDataUpdate;
//	private Object waitData;
	
	public final String name;
	public final int id;
	
	TServer dataSender, commandsReceiver;
	
	NetworkPlayerManager manager;
	
	World world;
	
	//boolean connected = false;
	
	
	class DataHandler implements Bridge.Iface {
		
		@Override
		public int connect(String nom) throws TException {
			
//			if (connected)
//				System.err.println("");
//			else
//			synchronized (manager)
//			{
//				manager.setConnected(this);
//			}
			manager.connect(NetworkPlayer.this);
			
			return id;
		}
		
		@Override
		public Data retrieveData(int idConnection) throws TException {
			
			Snapshot s = world.getCurrentSnapshot();
			
			while (lastNumFrame >= s.id)
			{
				synchronized(world) { // Something different for each server, maybe in parameter
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				s = world.getCurrentSnapshot();
			}
			System.out.println("Player "+ name+" has been served");
			return DataUpdater.prepareData(s);
			
		}
		
	}
	
	//<C extends CommandData> treat(C cmd)
	
	class CommandsHandler implements CommandReceiver.Iface {

		@Override
		public Response sendMoveCommand(MoveCommandData cmd, int idConnection) throws TException {
			addCommand(CommandMaker.make(cmd));
			// TODO verify command
			return new Response(Command.SUCCESS, null);
		}

		@Override
		public Response sendWaitCommand(WaitCommandData cmd, int idConnection) throws TException {
			addCommand(CommandMaker.make(cmd));
			// TODO verify command
			return new Response(Command.SUCCESS, null);
		}
		
	}
	
	public NetworkPlayer(NetworkPlayerManager manager, int id, String name, int dataSenderPort, int commandsReceiverPort)
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
		//this.id = Util.rand.nextInt();
		this.id = id;
		
		dataSender = new TSimpleServer(
				new TServer.Args(new TServerSocket(dataSenderPort)).processor(
						new Bridge.Processor<>(new DataHandler())
					)
			);
		//System.out.println("A");
		
		commandsReceiver = new TSimpleServer(
				new TServer.Args(new TNonblockingServerSocket(commandsReceiverPort)).processor(
						new CommandReceiver.Processor<>(new CommandsHandler())
					)
			);
		
		serve();
		
	}
	
	void serve() {
		
		new Thread() {
			public void run() { dataSender.serve(); };
		}.start();
		
		new Thread() {
			public void run() { commandsReceiver.serve(); }
		}.start();
		
	}
	
	void disconnect() {
		
		dataSender.stop();
		commandsReceiver.stop();
		
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
	 
}




 
