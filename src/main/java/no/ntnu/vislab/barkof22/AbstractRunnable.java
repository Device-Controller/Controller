package no.ntnu.vislab.barkof22;

/**
 * An abstract class that ensures all threads will have a stopThread and a conditional for continuous running.
 */
public abstract class AbstractRunnable implements Runnable {
    private boolean running;

    public AbstractRunnable() {
        this.running = true;
    }

    public abstract void run();
    public boolean stopThread(){
        boolean result = running;
        this.running = false;
        return result;
    }
    public boolean getRunning(){
        return running;
    }
}
