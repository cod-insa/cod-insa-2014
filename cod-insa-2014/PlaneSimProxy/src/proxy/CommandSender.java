package proxy;
import genbridge.Action;
import genbridge.CommandReceiver;
import genbridge.CommandReceiver.AsyncClient.addActionToPerform_call;
import genbridge.Response;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;

import command.Command;
import command.MoveCommand;


public class CommandSender {
	
	private CommandReceiver.AsyncClient client;
	private ArrayList<String> errors;
	private boolean isTimeOut;
	private int idConnection;
	private Proxy proxy;
	private String ip_client;
	private int port_client;
	
	public CommandSender(String ip, int port, int idC, Proxy p)
	{
		ip_client = ip;
		port_client = port+1;
		errors = new ArrayList<String>();
		isTimeOut = false;
		idConnection = idC;
		proxy = p;
	}
	
	public synchronized void addError(String errorMessage)
	{
		errors.add(errorMessage);
	}
	
	@SuppressWarnings("unchecked") // lol
	public synchronized ArrayList<String> getErrors()
	{
		return (ArrayList<String>) errors.clone();
	}
	
	public boolean isTimeOut() {
		return isTimeOut;
	}
	
	public void newFrame() {
		isTimeOut = false;
	}

	public void sendCommand(Command cmd)
	{
		System.out.println("Trying to send command...");
		if (cmd instanceof MoveCommand)
		{
			// Convert the command
			MoveCommand c = (MoveCommand)cmd;
			try {
				// Initialize client (exception if we reuse the same instance of the client everytime)
				client = new CommandReceiver.AsyncClient(
						new TBinaryProtocol.Factory(),
						new TAsyncClientManager(),
				        new TNonblockingSocket(ip_client, port_client));
				
				// Call async method
				client.addActionToPerform(
						new Action(
								proxy.getNumFrame(),
								genbridge.Command.findByValue(1), // MOVE_COMMAND
								c.planeId,
								c.destination.x(), 
								c.destination.y()),
						idConnection, 
						new CommandSenderCallback());
				System.out.println(idConnection);
			}catch (IOException e) {
				System.err.println("Error while initializing async client.");
				e.printStackTrace();
			} catch (TException e) {
				System.err.println("Error while sending the command");
				e.printStackTrace();
			}
		}
		else
			System.err.println("Command not recognized !");
		
	}
	
	class CommandSenderCallback implements AsyncMethodCallback<CommandReceiver.AsyncClient.addActionToPerform_call> 
	{
		@Override
		public void onComplete(addActionToPerform_call aatp) {
			Response r;
			try {
				r = aatp.getResult();
				
				switch (r.code)
				{
				case 0:
					System.out.println("Command sent successfully !");
					break;
				case -2:
					isTimeOut = true;
					System.out.println("command is time out !");
					break;
				default:
					addError(r.message);
					System.out.println("Error on command ! " + r.code + ", message: " + r.message);
				}
					
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
