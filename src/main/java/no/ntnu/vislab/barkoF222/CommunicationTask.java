/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkoF222;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristoffer
 */
public class CommunicationTask extends TimerTask {

    private final String msg;
    private final Socket receiver;

    interface OnAcknowledge {

        void OnAcknowledgeReceived(String ack);
    }

    public CommunicationTask(String msg, Socket receiver) {
        this.msg = msg;
        this.receiver = receiver;
    }

    private OnAcknowledge callback; // asda s

    public void setOnAcknowledge(OnAcknowledge callback) {
        this.callback = callback;
    }

    @Override
    public void run() {

        if (!(msg == null || callback == null)) {
            try {
                PrintWriter pw = new PrintWriter(receiver.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(receiver.getInputStream()));
                pw.println(msg);
                pw.flush();
                String response = br.readLine();
                callback.OnAcknowledgeReceived(response);
            } catch (IOException ex) {
                Logger.getLogger(CommunicationTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new IllegalStateException("Incorrectly setup of Communication Task");
//            callback.OnAcknowledgeReceived("TEST");
        }
    }
}
