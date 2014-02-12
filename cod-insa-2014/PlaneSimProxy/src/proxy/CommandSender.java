package proxy;
import genbridge.Action;
import genbridge.Bridge;
import genbridge.Bridge.AsyncClient.addActionToPerform_call;
import genbridge.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;

import command.Command;
import command.MoveCommand;


public class CommandSender {
	

	private Bridge.AsyncClient client;
	private Proxy proxy;
	private ArrayList<String> errors;
	
	public CommandSender(String ip, int port, Proxy p)
	{
		try {
			client = new Bridge.AsyncClient(
					new TBinaryProtocol.Factory(),
					new TAsyncClientManager(),
			        new TNonblockingSocket(ip, port));

		} catch (IOException e) {
			System.err.println("Error while initializing async client.");
			e.printStackTrace();
		}
		proxy = p;
		errors = new ArrayList<String>();
	}
	
	public synchronized void addError(String errorMessage)
	{
		errors.add(errorMessage);
	}
	
	public synchronized ArrayList<String> getErrors()
	{
		return (ArrayList<String>) errors.clone();
	}
	
	public void sendCommand(Command cmd)
	{
		if (cmd instanceof MoveCommand)
		{
			try {
				MoveCommand c = (MoveCommand)cmd;
				// TODO changer le 0 en id récupéré lors de la connexion
				client.addActionToPerform(0, new Action(1,genbridge.Command.findByValue(1),c.planeId,(int)c.destination.x(), (int)c.destination.y()), new CommandSenderCallback());
			} catch (TException e) {
				System.err.println("Error while sending the command");
				e.printStackTrace();
			}
		}
		else
			System.err.println("Command not recognized !");
		
	}
	
	class CommandSenderCallback implements AsyncMethodCallback<Bridge.AsyncClient.addActionToPerform_call> 
	{

		@Override
		public void onComplete(addActionToPerform_call aatp) {
			Response r;
			try {
				r = aatp.getResult();
			
					if (r.code == 1) // Timeout
						addError(r.message);
			
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
