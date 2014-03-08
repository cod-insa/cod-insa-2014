package ai;

import proxy.Proxy;


public abstract class AbstractAI{

	protected Proxy server;
	
	public AbstractAI(String ip, int port) {
		server = new Proxy(ip, port, this);
	}
	
	/**
	 * Must be implemented. This is the starting point of your AI.
	 */
	public abstract void think();
	
	/**
	 * Called when the client has finish
	 * Can occure when an error occure or the game ends
	 * Use it specifically if you have files to close, else leave it empty
	 */
	public void end() {}
}
