/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.vislab.vislabcontroller.Command;

/**
 *
 * @author Kristoffer
 */
public final class CommunicationDriver extends Thread {

    private CommunicationTimer timer;
    private Communicator comm;
    private ArrayList<Command> outgoingBuffer;
    private ArrayList<Command> idleBuffer;
    private int idleIndex;
    private IdleTimer idleTimer;
    private boolean running;

    public CommunicationDriver(Socket host, List<Command> idleBuffer) {
        this.timer = new CommunicationTimer();
        this.outgoingBuffer = new ArrayList<>();
        this.idleBuffer = new ArrayList<>(idleBuffer);
        this.idleTimer = new IdleTimer(1000);
        this.running = true;
        this.idleIndex = 0;
        try {
            this.comm = new Communicator(host.getOutputStream(), host.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(CommunicationDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CommunicationDriver(Socket host, Command... cmds) {
        this(host, new ArrayList<>(Arrays.asList(cmds)));
    }

    @Override
    public void run() {
        this.timer.start();
        this.comm.start();
        this.idleTimer.start();
        while (this.running) {
            try {
                if (isIdle()) {
                    this.timer.setOnReadyListener(() -> sendCommand());
                } else {
                    this.idleTimer.setOnReadyListener(() -> sendIdleCommand());
                }
                sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(CommunicationDriver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public synchronized boolean addAndSend(Command command) {
        return this.outgoingBuffer.add(command);
    }

    private boolean isIdle() {
        return this.outgoingBuffer.isEmpty() && this.idleBuffer.size() > 0;
    }

    private void sendCommand() {
        this.comm.sendCommand(this.outgoingBuffer.get(0));
        this.outgoingBuffer.remove(0);
    }

    private void sendIdleCommand() {
        this.comm.sendCommand(this.idleBuffer.get(this.idleIndex));
        this.idleIndex = this.idleIndex + 1 % this.idleBuffer.size();
    }
}
