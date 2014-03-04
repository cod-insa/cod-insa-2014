package proxy;

import genbridge.CommandData;
import genbridge.CommandReceiver;
import genbridge.CommandReceiver.AsyncClient.sendMoveCommand_call;
import genbridge.CommandReceiver.AsyncClient.sendWaitCommand_call;
import genbridge.CoordData;
import genbridge.MoveCommandData;
import genbridge.PlaneCommandData;
import genbridge.Response;
import genbridge.WaitCommandData;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;

import command.Command;
import command.MoveCommand;
import command.WaitCommand;

public class CommandSender {

	private CommandReceiver.AsyncClient client;
	private ArrayList<String> errors;
	private boolean isTimeOut;
	private int idConnection;
	private Proxy proxy;
	private String ip_client;
	private int port_client;

	public CommandSender(String ip, int port, int idC, Proxy p) {
		ip_client = ip;
		port_client = port + 1;
		errors = new ArrayList<String>();
		isTimeOut = false;
		idConnection = idC;
		proxy = p;
	}

	public synchronized void addError(String errorMessage) {
		errors.add(errorMessage);
	}

	@SuppressWarnings("unchecked")
	// lol
	public synchronized ArrayList<String> getErrors() {
		return (ArrayList<String>) errors.clone();
	}

	public boolean isTimeOut() {
		return isTimeOut;
	}

	public void newFrame() {
		isTimeOut = false;
	}

	public void sendCommand(Command cmd) {
		System.out.println("Trying to send command...");
		try {

			// Initialize client (exception if we reuse the same instance of the
			// client everytime)
			client = new CommandReceiver.AsyncClient(
					new TBinaryProtocol.Factory(), new TAsyncClientManager(),
					new TNonblockingSocket(ip_client, port_client));
			
			if (cmd instanceof MoveCommand) {
				// Convert the command
				MoveCommand mc = (MoveCommand) cmd;

				// Call async method
				client.sendMoveCommand(
						new MoveCommandData(
								new PlaneCommandData(
										new CommandData(proxy.getNumFrame()), mc.planeId),
								new CoordData(mc.destination.x(), mc.destination.y())), 
						idConnection,
						new MoveCommandSenderCallback());
			} 
			else if (cmd instanceof WaitCommand)
			{
				// Convert the command
				WaitCommand wc = (WaitCommand) cmd;
				// Call async method
				client.sendWaitCommand(
						new WaitCommandData(
								new PlaneCommandData(
										new CommandData(proxy.getNumFrame()), wc.planeId)), 
						idConnection,
						new MoveCommandSenderCallback());
			}
			else
				System.err.println("Command not recognized !");

		} catch (IOException e) {
			System.err.println("Error while initializing async client.");
			e.printStackTrace();
		} catch (TException e) {
			System.err.println("Error while sending the command");
			e.printStackTrace();
		}
	}
	
	private void treatResult(Response r)
	{

		switch (r.code) 
		{
		case Command.SUCCESS:
			System.out.println("Command sent successfully !");
			break;
		case Command.ERROR_TIME_OUT:
			isTimeOut = true;
			System.out.println("command is time out !");
			break;
		default:
			addError(r.message);
			System.out.println("Error on command ! " + r.code
					+ ", message: " + r.message);
		}
	}
	
	class MoveCommandSenderCallback
			implements
			AsyncMethodCallback<CommandReceiver.AsyncClient.sendMoveCommand_call> {
		@Override
		public void onComplete(sendMoveCommand_call aatp) 
		{
			Response r;
			try {
				r = aatp.getResult();
				treatResult(r);
				

			} catch (TException e) {
				System.err.println("Unexpected error occured while reading result data from command");
				e.printStackTrace();
			}
		}
		

		@Override
		public void onError(Exception arg0) {
			System.err.println("Unexpected error occured on callback");
			arg0.printStackTrace();
		}
		
	}
	class WaitCommandSenderCallback
	implements
	AsyncMethodCallback<CommandReceiver.AsyncClient.sendWaitCommand_call> {

		@Override
		public void onComplete(sendWaitCommand_call aatp) {
			Response r;
			try {
				r = aatp.getResult();
				treatResult(r);
				

			} catch (TException e) {
				System.err.println("Unexpected error occured while reading result data from command");
				e.printStackTrace();
			}			
		}

		@Override
		public void onError(Exception arg0) {
			System.err.println("Unexpected error occured on callback");
			arg0.printStackTrace();
		}
	}
	
}
