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
public 

class IdleTimer extends CustomTimer{

    private int timing;

    public IdleTimer(int timing) {
        this.timing = timing;
        this.running = true;
        reset();
    }

    @Override
    public void run() {
        while (running) {
            if (getTime() + timing < System.currentTimeMillis()) {
                onReady();
            }
        }
    }

    /**
     * Called when the timings has passed. If no observer is present it will
     * remain in a ready state.
     */
    private void onReady() {
        if (listener != null) {
            OnReady tempListener = listener;
            tempListener.onReady();
            reset();
        }

    }
    
    public void stopThread() {
        this.running = false;
    }

    @Override
    public boolean reset() {
        resetTimer();
        listener = null;
        return true;
    }
}