package utils;

/**
 * Purpose: This class makes keeping track of timing specific functions multiple times easily
 * @param elapsedTime - Stores time that has elapsed in a long
 * @param startTime - Stores the current system time when the start method is called
 * @param isRunning - Toggled on and off when start() and stop() are called
 */
public class StopWatch {
	
	private long elapsedTime;
	private long startTime;
	private boolean isRunning;
	
	/**
	 * Constructor for a stop watch that is stopped and sets elapsedTime to 0
	 */
	public StopWatch(){
		reset();
	}
	
	/**
	 *  Starts the stop watch, stores current time in nanoseconds in startTime
	 */
	public void start(){
		if(isRunning){
			return;
		}
		isRunning = true;
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Stops the stop watch, elapsedTime is calculated by subtracting current time in nanoseconds
	 * by time in nanoseconds when the stop watch was last started and then adding that to whatever
	 * was already stored in elapsedTime
	 */
	public void stop(){
		if (!isRunning){
			return;
		}
		isRunning = false;
		long endTime = System.currentTimeMillis();
		elapsedTime = elapsedTime + endTime - startTime;
	}
	
	/**
	 * Returns the total elapsedTime
	 * @return elapsedTime
	 */
	public long getElapsedTime(){
		if(isRunning){
			long endTime = System.currentTimeMillis();
			return elapsedTime + endTime - startTime;
		}
		else{
			return elapsedTime;
		}
	}
	
	/**
	 * Stops the watch and sets the elapsedTime to 0
	 */
	public void reset(){
		elapsedTime = 0;
		isRunning = false;
	}
}
