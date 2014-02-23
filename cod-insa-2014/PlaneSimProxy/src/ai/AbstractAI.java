package ai;

import proxy.Proxy;


public abstract class AbstractAI{

	protected Proxy proxy;
	
	public AbstractAI(Proxy p) {
		proxy = p;
	}
	
	/**
	 * To implement ! This is what the AI will execute
	 */
	public abstract void think();
}
