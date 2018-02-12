package no.ntnu.vislab.vislabcontroller;

import no.ntnu.vislab.barkoF22.CommunicationProtocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristoffer
 */
public class Connector {
    private static final CommunicationProtocol CP = new CommunicationProtocol();

    private final String address;
    private final int port;
    
    private Long timeSinceOn;
    private Long currentRunTime;

    public Connector(String address, int port) {
        this.address = address;
        this.port = port;
    }

    
    public void powerON(int powerSetting){
        if(powerSetting == 1 || powerSetting == 0){
            String cmd = CP.getPOWER(powerSetting);
            timeSinceOn = System.currentTimeMillis();
            sendCommand(cmd);
        } else {
            System.out.println("FEIL SETTING DIN TAPER");
        }
        
    }
    public void muteImage(int muteSetting){
        if(muteSetting == 1 || muteSetting == 0){
            String cmd = CP.getMUTE(muteSetting);
            sendCommand(cmd);
        } else {
            System.out.println("FEIL SETTING DIN TAPER");
        }
    }
    
    public void retrieveSettings() {
        String cmd = CP.getLAMP_RUNTIME(1);
        currentRunTime = System.currentTimeMillis() - timeSinceOn;
        System.out.println(currentRunTime);
        sendCommand(cmd);
    }
    
    public void lampTime(int lampNumber)
    {
        if(lampNumber == 1 || lampNumber == 2){
            String cmd = CP.getLAMP_RUNTIME(lampNumber);
            sendCommand(cmd);
        } else {
            System.out.println("WRONG!");
        }
    }
    
    public void sendCommand(String command){
        Socket socket;
        try {
            PrintWriter pw = null;
            BufferedReader br = null;
            InetAddress host = InetAddress.getByName(address);
            try{
                socket = new Socket(host, port);
                pw = new PrintWriter(socket.getOutputStream());
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pw.println(command);
                pw.flush();
                System.out.println(br.readLine());
            } catch (IOException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                pw.close();
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
