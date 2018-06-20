package runic.engine.util.timers;

/**
 * Created on 17/01/2016.
 */
public class Delay {

    private long length;
    private long endTime;
    private boolean started;

    /**
     * Creates a timer with a delay as milliseconds
     * 
     * @param length is milliseconds
     */
    public Delay(int length) {
        this.length = length;
        started = false;
    }
    
    public Delay(int length, boolean start) {
    	this.length = length;
    	if(start == true) {
    		start();
    	}
    }

    /**
     * Checks if the delay/timer is over
     * 
     * @return if the delay/timer is over
     */
    public boolean isOver() {
        if(!started) return false;
        return Time.getTime() >= endTime;
    }

    /**
     * Starts the delay/timer
     */
    public void start() {
        //reset();
        started = true;
        endTime = (length) + Time.getTime();
        //endTime = (length * 1000 * 1000) + Time.getTime();
    }

    /**
     * Resets the timer. Does not restart the timer.
     */
    public void reset() {
        started = false;
    }

    /**
     * Resets the timer then starts the timer.
     */
    public void restart() {
    	reset();
        start();
    }

    /**
     * @return if the delay/timer has started
     */
    public boolean isActive() {
        return started;
    }

    /**
     * Ends the delay/timer 
     */
    public void end() {
        started = false;
        endTime = 0;
    }

    /**
     * @return the delay/timer length
     */
    public long getLength() {
        return length;
    }

    /**
     * @param length of delay/timer in milliseconds
     */
    public void setLength(int length) {
        this.length = length;
    }
}
