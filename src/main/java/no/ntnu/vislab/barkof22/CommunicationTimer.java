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
public class CommunicationTimer extends Thread {

    private static final int POWER_ON_DELAY = 30000;
    private static final int MIN_DELAY = 500;
    private static final int RESEND_DELAY = 2000;
    private static final int TWENTY_COMMNADS_SENT_DELAY = 5000;
    private long lastReset = 0;

    private boolean ready = false;
    private boolean running;
    int numberOfResets = 0;
    private boolean powerOnCommand = false;
    private boolean acknowledged = false;
    private boolean canReset;

    public interface OnReady {

        void onReady(boolean ready);
    }
    private OnReady listener;

    public CommunicationTimer() {
        this.running = true;
        this.canReset = true;
        lastReset = System.currentTimeMillis();
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
                try {
                    if (listener != null) {
                        OnReady tempListener = listener;
                        listener = null;
                        tempListener.onReady(ready);
                        ready = false;
                    }
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CommunicationTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private boolean checkTimings() {
        boolean power = !(lastReset + POWER_ON_DELAY > System.currentTimeMillis() && powerOnCommand);
        boolean command = numberOfResets < 20 && !(numberOfResets == 19 && lastReset + TWENTY_COMMNADS_SENT_DELAY > System.currentTimeMillis());
        boolean minDelay = (lastReset + MIN_DELAY < System.currentTimeMillis() && acknowledged);
        boolean maxDelay = (lastReset + RESEND_DELAY < System.currentTimeMillis() && !acknowledged);
        return power && command && (minDelay || maxDelay);
    }

    public boolean reset(boolean isPowerOn) {
        if (canReset) {
            this.lastReset = System.currentTimeMillis();
            this.numberOfResets = (numberOfResets + 1) % 20;
            System.out.println(numberOfResets);
            this.powerOnCommand = isPowerOn;
            this.acknowledged = false;
            this.canReset = false;
            return true;
        } else {
            return false;
        }
    }

    public void stopTimer() {
        this.acknowledged = true;
    }

    public boolean stopThread() {
        running = false;
        return true;
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
