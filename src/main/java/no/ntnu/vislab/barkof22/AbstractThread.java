package no.ntnu.vislab.barkof22;

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
