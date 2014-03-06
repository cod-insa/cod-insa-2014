package proxy;

import genbridge.CommandData;
import genbridge.CommandReceiver;
import genbridge.CoordData;
import genbridge.LandCommandData;
import genbridge.MoveCommandData;
import genbridge.PlaneCommandData;
import genbridge.Response;
import genbridge.TakeOffCommandData;
import genbridge.WaitCommandData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import model.Coord;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import command.Command;
import command.LandCommand;
import command.MoveCommand;
import command.TakeOffCommand;
import command.WaitCommand;

public class CommandSender extends Thread {

	private CommandReceiver.Client client;
	private ArrayList<String> errors;
	private boolean isTimeOut;
	private int idConnection;
	private Proxy proxy;

	private Queue<Command> waitingList;

	public CommandSender(String ip, int port, int idC, Proxy p) {
		errors = new ArrayList<String>();
		waitingList = new LinkedList<Command>();
		isTimeOut = false;
		idConnection = idC;
		proxy = p;

		// Initialize client
		try {
			// Initialize Socket of client
			TTransport transport;
			transport = new TSocket(ip, port);
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			// TProtocol protocol = new TSimpleJSONProtocol(transport);

			client = new CommandReceiver.Client(protocol);

		} catch (Exception e) {
			System.err.println("Error while initializing data retriever : ");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public synchronized void addError(String errorMessage) {
		errors.add(errorMessage);
	}

	@SuppressWarnings("unchecked")
	public synchronized ArrayList<String> getErrors() {
		return (ArrayList<String>) errors.clone();
	}

	public boolean isTimeOut() {
		return isTimeOut;
	}

	public void newFrame() {
		isTimeOut = false;
	}

	@Override
	public void run() {
		Command currentCmd;
		while (true) // FIXME ugly
		{
			synchronized (this) {
				while (waitingList.isEmpty()) {
					try {
						// System.out.println(">> waiting");
						wait();
						// System.out.println(">> notified");
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
				while (!waitingList.isEmpty()) {
					currentCmd = waitingList.remove();
					sendThriftCommand(currentCmd);
					//System.out.println(">> sent");
				}
			}
		}
	}

	public synchronized void sendCommand(Command cmd) {
		waitingList.add(cmd);
		notify();
	}

	private void sendThriftCommand(Command cmd) {
		System.out.println("Sending command...");
		Response r;
		try {
			if (cmd instanceof MoveCommand) {
				// Call server method
				r = client.sendMoveCommand(
						DataMaker.make((MoveCommand) cmd, proxy.getNumFrame()),
						idConnection);
			} else if (cmd instanceof WaitCommand) {
				// Call server method
				r = client.sendWaitCommand(
						DataMaker.make((WaitCommand) cmd, proxy.getNumFrame()),
						idConnection);
			} else if (cmd instanceof LandCommand) {
				// Call server method
				r = client.sendLandCommand(
						DataMaker.make((LandCommand) cmd, proxy.getNumFrame()),
						idConnection);
			} else if (cmd instanceof TakeOffCommand) {
				// Call server method
				r = client.sendTakeOffCommand(
						DataMaker.make((TakeOffCommand) cmd,
								proxy.getNumFrame()), idConnection);
			} else {
				System.err
						.println("Command not recognized or not supported (yet) !");
				r = new Response(Command.ERROR_UNKNOWN, "Commande inconnue");
			}
			treatResult(r);
		} catch (TException e) {
			System.err.println("Error while sending the command");
			e.printStackTrace();
		}
	}

	private void treatResult(Response r) {

		switch (r.code) {
		case Command.SUCCESS:
			System.out.println("Command sent successfully !");
			break;
		case Command.ERROR_TIME_OUT:
			isTimeOut = true;
			System.out.println("command is time out !");
			break;
		default:
			addError(r.message);
			System.out.println("Error on command ! " + r.code + ", message: "
					+ r.message);
		}
	}

	public static class DataMaker {

		static CoordData make(Coord.View c) {
			return new CoordData(c.x(), c.y());
		}

		public static LandCommandData make(LandCommand cmd, int numFrame) {
			return new LandCommandData(new PlaneCommandData(new CommandData(
					numFrame), cmd.planeId), cmd.baseId);
		}

		public static TakeOffCommandData make(TakeOffCommand cmd, int numFrame) {
			return new TakeOffCommandData(new PlaneCommandData(new CommandData(
					numFrame), cmd.planeId));
		}

		static MoveCommandData make(MoveCommand cmd, int numFrame) {
			return new MoveCommandData(new PlaneCommandData(new CommandData(
					numFrame), cmd.planeId), make(cmd.destination));
		}

		static WaitCommandData make(WaitCommand cmd, int numFrame) {
			return new WaitCommandData(new PlaneCommandData(new CommandData(
					numFrame), cmd.planeId));
		}

	}
}
