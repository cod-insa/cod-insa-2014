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
	private ArrayList<String> errors;
	private boolean isTimeOut;
	private int idConnection;
	
	public CommandSender(String ip, int port, int idC)
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
		errors = new ArrayList<String>();
		isTimeOut = false;
		idConnection = idC;
	}
	
	public synchronized void addError(String errorMessage)
	{
		errors.add(errorMessage);
	}
	
	public synchronized ArrayList<String> getErrors()
	{
		return (ArrayList<String>) errors.clone();
	}
	
	public boolean isTimeOut() {
		return isTimeOut;
	}

	public void sendCommand(Command cmd)
	{
		if (cmd instanceof MoveCommand)
		{
			try {
				MoveCommand c = (MoveCommand)cmd;
				client.addActionToPerform(0, new Action(idConnection,genbridge.Command.findByValue(1),c.planeId,(int)c.destination.x(), (int)c.destination.y()), new CommandSenderCallback());
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
						isTimeOut = true;
					else if (r.code == 2) // other error
						addError(r.message);
					// 0 => no error
			
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
