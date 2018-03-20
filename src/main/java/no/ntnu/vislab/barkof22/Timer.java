package no.ntnu.vislab.barkof22;

/**
 * Simple timer that stores the last time it was reset.
 */
public class Timer {
    private long time;

    public Timer() {
        reset();
    }

    /**
     * Resets teh timer by storing current time.
     */
    public void reset() {
        this.time = System.currentTimeMillis();
    }

    /**
     * Checks if current time has passed last the last reset time + the timeout.
     * @param timeout how long it should be for the timer to pass.
     * @return true if last reset time + timeout is less than current time.
     */
    public boolean hasTimerPassed(long timeout) {
        long currentTime = System.currentTimeMillis();
        return time + timeout < currentTime;
    }
}
