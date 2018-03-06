package no.ntnu.vislab.barkof22;

public class Timer {
    private long time;

    public Timer() {
        reset();
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }

    public boolean hasTimerPassed(long timeout) {
        long currentTime = System.currentTimeMillis();
        return time + timeout < currentTime;
    }
}
