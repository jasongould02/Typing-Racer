package runic.engine.util.timers;

/**
 * Created on 17/01/2016.
 */
public class Time {

    private static long curTime;
    private static long lastTime;
    
    /**
     * @return current runtime in milliseconds
     */
    public static long getTimeMilliseconds() {
        return System.currentTimeMillis();
    }

    /**
     * @return current runtime in seconds
     */
    public static long getTimeSeconds() {
        return System.currentTimeMillis() / 1000;
    }
    
    /**
     * Delta Time is the time elapsed between 2 frames in milliseconds.
     * @return delta time as a double
     */
    public static double getDeltad() {
        return (double) (curTime - lastTime) ;
    }
    
    /**
     * Delta Time is the time elapsed between 2 frames in milliseconds.
     * @return delta time as an integer
     */
    public static int getDeltai() {
        return (int) (curTime - lastTime);
    }
    
    /**
     * Delta Time is the time elapsed between 2 frames in milliseconds.
     * @return delta time as a long
     */
    public static long getDelta() {
    	return curTime - lastTime;
    }
    
    public static long getTime() {
    	//return System.nanoTime();
    	return System.currentTimeMillis();
    }
    
    public static long getNanoTime() {
    	return System.nanoTime();
    }

    public static void update() {
        lastTime = curTime;
        curTime = getTimeMilliseconds();
    }

    public static void init() {
        curTime = Time.getTimeMilliseconds();
        lastTime = Time.getTimeMilliseconds();
    }
}
