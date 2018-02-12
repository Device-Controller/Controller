package no.ntnu.vislab.barkoF22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.vislab.vislabcontroller.Connector;

/**
 * Handles the timing and communication errors with the projectors. Times are in
 * ms.
 *
 * @author Kristoffer
 */
public class CommunicationDriver {

    interface OnAcknowledge {

        void OnAcknowledgeRecieved(String msg);
    }

    private static final int POWER_ON_DELAY = 30000;
    private static final int MIN_DELAY = 500;
    private static final int RESEND_DELAY = 2000;
    private static final int TWENTY_COMMNADS_SENT_DELAY = 5000;
    private int commandsSent = 0;
    private long lastCommandSent = 0;
    private boolean commandAcknowledged = false;
    private boolean lastCommandWasPowerOn = false;
    private Communicator com;

    private Socket socket;
    public CommunicationDriver(String IP, int port) throws UnknownHostException {
        try {
            this.socket = new Socket(InetAddress.getByName(IP), port);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.com = new Communicator(socket);
        this.com.setOnAcknowledgeReceived(ack -> {
            commandAcknowledged = !ack.isEmpty();
            System.out.println(ack);
        });
    }

    public void sendCommand(String command) {
        while (!readyToSend()) {
            System.out.println("WAITING");
        }
        com.setMessage(command);
        com.start();
        lastCommandSent = System.currentTimeMillis();
        lastCommandWasPowerOn = false; //TODO: Compare command with the POWER ON command.
        while (commandAcknowledged == false) {
            if (RESEND_DELAY + lastCommandSent < System.currentTimeMillis()) {
                try {
                    socket.close();
                    socket = new Socket(socket.getInetAddress(), socket.getPort());
                } catch (IOException ex) {
                    Logger.getLogger(CommunicationDriver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private boolean readyToSend() {
        if (MIN_DELAY + lastCommandSent > System.currentTimeMillis()) {
            return false;
        } else if (commandsSent == 20 && TWENTY_COMMNADS_SENT_DELAY + lastCommandSent > System.currentTimeMillis()) {
            return false;
        } else if (lastCommandWasPowerOn && lastCommandSent + POWER_ON_DELAY > System.currentTimeMillis()) {
            return false;
        } else if (com.isAlive()) {
            return false;
        }
        return true;
    }

    class Communicator extends Thread {

        private String msg;
        private final Socket receiver;
        private OnAcknowledge callback;

        public Communicator(Socket receiver) {
            this.receiver = receiver;
        }

        public void setOnAcknowledgeReceived(OnAcknowledge callback) {
            this.callback = callback;
        }

        public boolean setMessage(String msg) {
            if (this.msg == null) {
                this.msg = msg;
                return true;
            }
            return false;
        }

        public void run() {
            if (msg != null) {
                try (PrintWriter pw = new PrintWriter(receiver.getOutputStream());
                        BufferedReader br = new BufferedReader(new InputStreamReader(receiver.getInputStream()))) {
                    pw.println(msg);
                    msg = null;
                    pw.flush();
                    if (callback != null) {
                        callback.OnAcknowledgeRecieved(br.readLine());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CommunicationDriver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
