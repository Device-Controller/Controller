/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.vislab.vislabcontroller.Command;

/**
 *
 * @author Kristoffer
 */
public class CommunicationRunnable implements Runnable {

    private static final int POWER_ON_DELAY = 30000;
    private static final int MIN_DELAY = 500;
    private static final int RESEND_DELAY = 2000;
    private static final int TWENTY_COMMNADS_SENT_DELAY = 5000;
    private int commandsSent = 0;
    private long lastCommandSent = 0;
    private volatile long acknowledgeTime = -1;
    private boolean lastCommandWasPowerOn = false;
    private boolean running = false;
    private Socket socket;
    private Timer timer;

    private volatile ArrayList<Command> commands;
    private volatile ArrayList<Command> currentlySending;
    private volatile ArrayList<Acknowledge> acknowledgeses;

    public CommunicationRunnable(InetAddress address, int port) throws UnknownHostException {
        timer = new Timer();
        try {
            socket = new Socket(address, port);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.commands = new ArrayList<>();
        this.currentlySending = new ArrayList<>();
        this.acknowledgeses = new ArrayList<>();
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            while (!readyToSend()) {
            }
            sendCommand();

        }
    }

    private synchronized void commandSent(Command command) {
        if (command instanceof BarkoF22Command) {
            BarkoF22Command barkoCommand = (BarkoF22Command) command;
            lastCommandSent = System.currentTimeMillis();
            lastCommandWasPowerOn = barkoCommand.isPowerOnCommand();      //TODO: Fix command power on check instead of isEmpty();
            commands.remove(command);
            commandsSent++;
            acknowledgeTime = -1;
        }
    }

    private synchronized void acknowledgeReceived(String ack) {
        acknowledgeTime = System.currentTimeMillis();
        Acknowledge acknowledge = new Acknowledge(ack);
        acknowledgeses.add(acknowledge);
        System.out.println(new Acknowledge(ack).getExplaination());
        System.out.println(System.currentTimeMillis());
        notifyAll();
        System.out.println(Thread.currentThread().getId());
    }

    public synchronized void sendCommand(Command command) {
        commands.add(command);
        notifyAll();
    }

    private synchronized void sendCommand() {
        boolean resend = true;
        int tries = 0;
        int maxTries = 3;
        while (resend && tries < maxTries) {
            tries++;
            if (commands.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(CommunicationRunnable.class.getName()).log(Level.SEVERE, null, ex);
                    Thread.currentThread().interrupt();
                }
            }
            Command command = commands.get(0);
            if (!currentlySending.contains(command)) {
                currentlySending.add(command);
                CommunicationTask com = new CommunicationTask(command.toString(), socket);
                com.setOnAcknowledge(ack -> acknowledgeReceived(ack));
                timer.schedule(com, 0);
                lastCommandSent = System.currentTimeMillis();
                boolean acknowledged = false;
                while (!acknowledged && RESEND_DELAY + lastCommandSent > System.currentTimeMillis()) {
                    if (acknowledgeTime != -1) {
                        resend = false;
                        commandSent(command);
                        acknowledged = true;
                    }
                }
            } else {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(CommunicationRunnable.class.getName()).log(Level.SEVERE, null, ex);
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private synchronized boolean readyToSend() {
        boolean power = false;
        boolean twentyCommands = false;
        boolean minDelay = false;
        power = !(lastCommandWasPowerOn && POWER_ON_DELAY + lastCommandSent > System.currentTimeMillis());
        twentyCommands = commandsSent < 20;
        if (commandsSent >= 20 && TWENTY_COMMNADS_SENT_DELAY + lastCommandSent < System.currentTimeMillis()) {
            twentyCommands = true;
            commandsSent = 0;
        }
        minDelay = MIN_DELAY + lastCommandSent < System.currentTimeMillis();
        return power && twentyCommands && minDelay;
    }

    public synchronized String getResponse() {
        while (acknowledgeses.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(CommunicationRunnable.class.getName()).log(Level.SEVERE, null, ex);
                Thread.currentThread().interrupt();
            }
        }
        return acknowledgeses.remove(0).getExplaination();
    }
}
