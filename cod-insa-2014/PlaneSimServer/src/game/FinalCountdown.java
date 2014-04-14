package game;

/**
 * Keep an eye on time
 * After X minutes, the game stops?
 * @author NV
 *
 */
public class FinalCountdown extends Thread{

	private long target = 0;
	private long current = 0;
	private long estimated = 0;
	
	private boolean running = true;
	
	/**
	 * @param time, in seconds
	 */
	public FinalCountdown(long time) {
		target = System.currentTimeMillis() + time*1000;
		current = System.currentTimeMillis() + 1000;
		synchronized (this) {
			estimated = ((target - current) / 1000)+1;
		}
		//start(); //in game now
		
		//////////////////////////
		// FIXME: prevents the server for correctly shutting down
		//start();
		//////////////////////////
	}
	
	@Override
	public void run() {
		super.run();
		
		while(running)
		{
			current = System.currentTimeMillis() + 1000;
			synchronized (this) {
				estimated = ((target - current) / 1000)+1;
			}
			if(target<current)
			{
				System.out.println("End!");
				break;
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		FinalCountdown fc = new FinalCountdown(10);
	}
	
	public long getRemainingTime()
	{
		long value = 0;
		synchronized (this) {
			value = estimated;
		}
		return value;
	}
	
	public void interruptCountdown()
	{
		running = false;
	}
	
}
