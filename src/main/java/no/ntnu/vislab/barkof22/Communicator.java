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
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.vislab.vislabcontroller.Command;

/**
 *
 * @author Kristoffer
 */
public class Communicator extends AbstractThread {

    public interface OnResponse{
        void onResponse();
    }
    private OnResponse callback;
    private PrintStream out;
    private BufferedReader in;
    private boolean commandSent;
    private Command command;

    public Communicator(OutputStream out, InputStream in) {
        super();
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new PrintStream(out);
        reset();
    }

    @Override
    public void run() {
        while (getRunning()) {
            if (command != null) {
                if (!commandSent) {
                    out.println(command.toString());
                    commandSent = true;
                }
                try {
                    if (in.ready() && commandSent) {
                        command.setResponse(in.readLine());
                        System.out.println(command.getResponse());
                        onResponse();
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

    private void onResponse() {
        if(callback != null){
            callback.onResponse();
            callback = null;
        }
    }

    public boolean setCallback(OnResponse callback){
        if(this.callback == null){
            this.callback = callback;
            return true;
        }
        return false;
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
