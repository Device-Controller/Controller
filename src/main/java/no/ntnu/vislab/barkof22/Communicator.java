/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.vislab.vislabcontroller.Command;

/**
 *
 * @author Kristoffer
 */
public class Communicator extends Thread {

    private PrintWriter out;
    private BufferedReader in;
    private boolean running = false;
    private boolean commandSent;
    private Command command;

    public Communicator(OutputStream out, InputStream in) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new PrintWriter(out);
        this.running = true;
        reset();
    }

    @Override
    public void run() {
        while (this.running) {
            if (command != null) {
                if (!commandSent) {
                    out.println(command.toString());
                    commandSent = true;
                }
                try {
                    if (isMessageAvailable() && commandSent) {
                        System.out.println(in.readLine());
                        //command.setResponse(in.readLine());
                        reset();
                    } else {
                        sleep(10);
                    }
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isMessageAvailable() throws IOException {
        return in.read() == '%';
    }

    /**
     * Stops the outbound writer.
     */
    public void stopOutbound() {
        this.running = false;
    }

    /**
     * Submits the command to be sent out to the receiver. Returns true if the
     * command can be submitted.
     *
     * @param command the command to send
     * @return true if the command can be submitted
     */
    public boolean sendCommand(Command command) {
        if (this.command == null) {
            this.command = command;
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        this.command = null;
        this.commandSent = false;
    }
}
