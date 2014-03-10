package proxy;

import genbridge.CommandData;
import genbridge.CommandReceiver;
import genbridge.CoordData;
import genbridge.LandCommandData;
import genbridge.MoveCommandData;
import genbridge.PlaneCommandData;
import genbridge.Response;
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
import command.WaitCommand;

public class CommandSender extends Thread {

	private CommandReceiver.Client client;
	private ArrayList<String> errors;
	private boolean isTimeOut;
	private int idConnection;
	private Proxy proxy;
	private boolean running;

	private Queue<Command> waitingList;

	public CommandSender(String ip, int port, int idC, Proxy p) {
		proxy = p;
		running = false;
		errors = new ArrayList<String>();
		waitingList = new LinkedList<Command>();
		isTimeOut = false;
		idConnection = idC;

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
			System.err
					.println("Error while connecting to the server. Message: "
							+ e.getMessage());
			System.err
					.println("Cause : The server is not running, the game may have ended or, the port or the ip may be not good");
			proxy.quit(1);
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
		running = true;
		Command currentCmd;
		while (running) {
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
					// System.out.println(">> sent");
				}
			}
		}
		if (client.getInputProtocol().getTransport().isOpen())
			client.getInputProtocol().getTransport().close();
		if (client.getOutputProtocol().getTransport().isOpen())
			client.getOutputProtocol().getTransport().close();
	}

	public synchronized void sendCommand(Command cmd) {
		waitingList.add(cmd);
		notify();
	}

	private void sendThriftCommand(Command cmd) {
		System.out.println("Sending command...");
		Response r = null;
		try {
			try { // match the command
				cmd.match();
			} catch (MoveCommand c) {
				// Call server method
				r = client.sendMoveCommand(
						DataMaker.make(c, proxy.getNumFrame()), idConnection);
			} catch (WaitCommand c) {
				// Call server method
				r = client.sendWaitCommand(
						DataMaker.make(c, proxy.getNumFrame()), idConnection);
			} catch (LandCommand c) {
				// Call server method
				r = client.sendLandCommand(
						DataMaker.make(c, proxy.getNumFrame()), idConnection);
			}
			treatResult(r);
		} catch (TException e) {
			System.err
					.println("Unexpected error received while sending a command. Message: "
							+ e.getMessage());
			proxy.quit(1);
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
			System.out.println("WARNING : Error on command ! code:" + r.code + ", message: "
					+ r.message);
		}
	}

	public void terminate() {
		running = false;
	}

	public static class DataMaker {

		static CoordData make(Coord.View c) {
			return new CoordData(c.x(), c.y());
		}

		static LandCommandData make(LandCommand cmd, int numFrame) {
			return new LandCommandData(new PlaneCommandData(new CommandData(
					numFrame), cmd.planeId), cmd.baseId);
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
