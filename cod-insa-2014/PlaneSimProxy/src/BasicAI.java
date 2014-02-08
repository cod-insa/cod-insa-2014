import org.apache.thrift.TException;

import genbridge.Bridge;


public class BasicAI{

	Bridge.Client client;
	
	public BasicAI(Bridge.Client client) {
		this.client = client;
	}
	
	/**
	 * Teams work starts here
	 */
	public void thinkAboutWhatToDo()
	{
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
	}
}
