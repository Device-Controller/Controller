/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import no.ntnu.vislab.vislabcontroller.Command;

/**
 *
 * @author Kristoffer
 */
public final class CommunicationDriver extends Thread {

    private CommunicationTimer timer;
    private ArrayList<Command> outgoingBuffer;
    private ArrayList<Command> idleBuffer;
    private int idleSendTimer;
    private boolean running;

    public CommunicationDriver(Socket host, List<Command> idleBuffer) {
        this.timer = new CommunicationTimer();
        this.outgoingBuffer = new ArrayList<>();
        this.idleBuffer = new ArrayList<>(idleBuffer);
        this.idleSendTimer = 1;
        this.running = true;
    }

    public CommunicationDriver(Socket host, Command... cmds) {
        this(host, new ArrayList<>(Arrays.asList(cmds)));
    }

    @Override
    public void run() {
        this.timer.start();
        while(running){
            
        }

    }

    public synchronized boolean addAndSend(Command command) {
        return outgoingBuffer.add(command);
    }
}
