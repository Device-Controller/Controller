package no.ntnu.vislab.barkof22;

/**
 * An abstract class that ensures all threads will have a stopThread and a conditional for continuous running.
 */
public abstract class AbstractThread extends Thread {
    private boolean running;

    public AbstractThread() {
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
