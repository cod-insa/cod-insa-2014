package control;


/*
 * A data updater by player
 * Every second, it updates data for this team
 * and then, allows the server handle to sent it to the client
 */
public class DataUpdater extends Thread{
	
	int period = 1000;	//milliseconds
		
	/**
	 * 
	 * @param period, by default a second
	 */
	public DataUpdater(int period) {
		this.period = period;
	}
	
	public void update()
	{
		// player.getWaitData().notify();
	}
	
	
}


