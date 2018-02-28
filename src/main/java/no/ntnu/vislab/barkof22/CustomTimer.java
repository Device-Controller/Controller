/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22;

/**
 *
 * @author Kristoffer
 */
public abstract class CustomTimer extends Thread {
    
    protected boolean running;

    public interface OnReady {

        void onReady();
    }
    protected OnReady listener;
    private long time;

    public CustomTimer() {
        this.running = true;
        resetTimer();
    }

    public long getTime() {
        return time;
    }

    @Override
    public abstract void run();
    public abstract boolean reset();
    
    public final void resetTimer(){
        time = System.currentTimeMillis();
    }

    public boolean setOnReadyListener(OnReady listener) {
        if (this.listener == null) {
            this.listener = listener;
            return true;
        } else {
            return false;
        }
    }
}
