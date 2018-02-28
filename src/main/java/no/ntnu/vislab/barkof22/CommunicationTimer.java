/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristoffer
 */
public class CommunicationTimer extends CustomTimer {

    private static final int POWER_ON_DELAY = 30000;
    private static final int MIN_DELAY = 500;
    private static final int RESEND_DELAY = 2000;
    private static final int TWENTY_COMMNADS_SENT_DELAY = 5000;

    private boolean ready = false;
    int numberOfResets = 0;
    private boolean powerOnCommand = false;
    private boolean acknowledged = false;
    private boolean canReset;

    public CommunicationTimer() {
        super();
        this.running = true;
        this.canReset = true;
    }

    @Override
    public void run() {
        while (running) {
            if (!ready) {
                ready = checkTimings();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CommunicationTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                canReset = true;
                onReady();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CommunicationTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Checks all timing related to the communication protocol for the barkoF22
     * projector and a configurable one.
     *
     * @return true if the timing rules for the communication protocol and the
     * configurable timing has passed
     */
    private boolean checkTimings() {
        boolean power = !(getTime() + POWER_ON_DELAY > System.currentTimeMillis() && powerOnCommand);
        boolean command = numberOfResets < 20 && !(numberOfResets == 19 && getTime() + TWENTY_COMMNADS_SENT_DELAY > System.currentTimeMillis());
        boolean minDelay = (getTime() + MIN_DELAY < System.currentTimeMillis() && acknowledged);
        boolean maxDelay = (getTime() + RESEND_DELAY < System.currentTimeMillis() && !acknowledged);
        return power && command && (minDelay || maxDelay);
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

    /**
     * Returns true if the timer can be reset. Can only be reset if the timings
     * has passed.
     *
     * @param isPowerOn a flag to indicate that the command sent was the powerOn
     * command (Requires longer timeout).
     * @return true if the timer was reset.
     */
    public boolean reset(boolean isPowerOn) {
        boolean result = reset();
        this.powerOnCommand = isPowerOn;
        return result;
    }

    @Override
    public boolean reset() {
        if (canReset) {
            resetTimer();
            this.powerOnCommand = false;
            this.numberOfResets = (numberOfResets + 1) % 20;
            this.acknowledged = false;
            this.canReset = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tells the timer that a condition was met and the maximum delay is not
     * necessary.
     */
    public void stopTimer() {
        this.acknowledged = true;
    }

    /**
     * Tags the thread to finish execution. it will complete its current
     * executions then stop.
     *
     * @return true
     */
    public boolean stopThread() {
        running = false;
        return true;
    }
}
