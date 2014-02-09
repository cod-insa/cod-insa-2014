package ai;

import proxy.Proxy;


public abstract class AbstractAI{

	Proxy proxy;
	
	
	public AbstractAI(Proxy p) {
		proxy = p;
	}
	
	/**
	 * 
	 */
	public abstract void think();
	/*
	{
		// Testing
		try {
			  client.getMyBases();
			  System.out.println("Call getMyPlanes");
			  client.getMyPlanes();
			  System.out.println("Call getMyBases");
			  client.getMyBases();
			  System.out.println("Call build_plane");
			  client.build_plane(0);
			  System.out.println("Call getActionsListStatus");
			  client.getActionsListStatus();
			  System.out.println("Call addActionToPerform");
			  client.addActionToPerform(null);
			  
		} catch (TException e) {
			e.printStackTrace();
		}
	}*/
}
