/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.ntnu.vislab.vislabcontroller.Command;

/**
 * @author Kristoffer
 */
public final class CommunicationDriver extends AbstractThread {

    private CommunicationTimer timer;
    private Communicator comm;
    private volatile ArrayList<Command> outgoingBuffer;
    private volatile ArrayList<Command> idleBuffer;
    private int idleIndex;
    private IdleTimer idleTimer;
    private boolean running;

    public CommunicationDriver(Socket host, List<Command> idleBuffer) throws IOException {
        this(host.getOutputStream(), host.getInputStream(), idleBuffer);
    }

    public CommunicationDriver(OutputStream out, InputStream in, List<Command> idleBuffer) {
        setName("CommunicationDriver Thread-" + getId());
        this.timer = new CommunicationTimer();
        this.outgoingBuffer = new ArrayList<>();
        this.idleBuffer = new ArrayList<>(idleBuffer);
        this.idleTimer = new IdleTimer(1000);
        this.running = true;
        this.idleIndex = 0;
        this.comm = new Communicator(out, in);
    }

    public CommunicationDriver(Socket host, Command... cmds) throws IOException {
        this(host, new ArrayList<>(Arrays.asList(cmds)));
    }

    public CommunicationDriver(OutputStream out, InputStream in, Command... cmds) {
        this(out, in, new ArrayList<>(Arrays.asList(cmds)));
    }

    @Override
    public void run() {
        this.timer.start();
        this.comm.start();
        this.idleTimer.start();
        while (this.running) {
            if (isIdle()) {
                if (!idleBuffer.isEmpty())
                    idleTimer.setOnReadyListener(() -> {
                        sendIdleCommand();
                        idleTimer.reset();
                    });
            } else {
                if (!outgoingBuffer.isEmpty()) {
                    timer.setOnReadyListener(() -> {
                        sendCommand();
                        timer.reset();
                    });
                    timer.setOnTimeOutListener(() -> {
                        retry();
                        timer.reset();
                    });
                }
            }
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void retry() {
        System.out.println("retry");
    }

    public synchronized boolean addAndSend(Command command) {
        return this.outgoingBuffer.add(command);
    }

    public int getCurrentQue() {
        return outgoingBuffer.size();
    }

    private boolean isIdle() {
        return this.outgoingBuffer.isEmpty() && this.idleBuffer.size() > 0;
    }

    private void sendCommand() {
        this.comm.sendCommand(outgoingBuffer.get(0));
        this.comm.setCallback(() -> {
            outgoingBuffer.remove(0);
            timer.acknowledge();
        });
    }

    private void sendIdleCommand() {
        this.comm.setCallback(() -> this.idleIndex = (this.idleIndex + 1) % this.idleBuffer.size());
        this.comm.sendCommand(idleBuffer.get(idleIndex));
    }

    /**
     * Returns false if all threads were already stopped
     *
     * @return false if all threads were already stopped
     */
    @Override
    public boolean stopThread() {
        return super.stopThread() || timer.stopThread() || comm.stopThread() || idleTimer.stopThread();

    }


}
