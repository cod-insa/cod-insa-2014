package Runnable;
import command.Command;

import Global.InterfaceAPI;
import Global.Logistics;
import ai.AbstractAI;

public class SimpleAI extends AbstractAI {
	private Logistics logistics;

	public SimpleAI(String ip, int port) {
		super(ip, port);
		this.logistics = new Logistics();
		Model.Model.createInstance();
	}

	@Override
	public void think() {

		while (true) {
			game.updateSimFrame();
			try {
				// Update data and do stuff
				logistics.update(game);
				
				// Send commands
				for (Command c : InterfaceAPI.getInstance().getCommands()) {
					if (!game.isTimeOut()) {
						game.sendCommand(c);
					}
					else  {
						System.err.println("Erreur : TIMEOUT");
					}
				}
				
				// Reset commands
				InterfaceAPI.getInstance().resetCommands();
			}
			catch (Exception e) {
				System.err.println("Ceci n'est pas une erreur :)");
			}
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage : java SimpleAI ip port");
			System.exit(1);
		}
		String ip = args[0];
		int port = Integer.parseInt(args[1]);

		AbstractAI ai = new SimpleAI(ip, port);
		ai.think();
	}
}
