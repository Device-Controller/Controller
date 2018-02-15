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
import no.ntnu.vislab.barkoF22.BarkoF22Command;

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

    public Connector(String address, int port){
        this.address = address;
        this.port = port;
    }

    
    public void power(int powerSetting){
        if(powerSetting == 1){
            timeSinceOn = System.currentTimeMillis();
            sendCommand(BarkoF22Command.powerOn().toString());
        } else if(powerSetting == 0){
            sendCommand(BarkoF22Command.powerOff().toString());
        } else {
            System.out.println("FEIL SETTING DIN TAPER");
        }
        
    }
    
    public void powerState()
    {
        sendCommand(BarkoF22Command.powerState().toString());
    }
    
    public void muteImage(int muteSetting){
        if(muteSetting == 1 || muteSetting == 0){
            sendCommand(CP.getMute(muteSetting));
        } else {
            System.out.println("FEIL SETTING DIN TAPER");
        }
    }
    
    public void retrieveSettings() {
        currentRunTime = System.currentTimeMillis() - timeSinceOn;
        System.out.println(currentRunTime);
        sendCommand(CP.getLampRuntime(1));
    }
    
    public void lampTime(int lampNumber)
    {
        if(lampNumber == 1 || lampNumber == 2){
            sendCommand(CP.getLampRuntime(lampNumber));
        } else {
            System.out.println("WRONG!");
        }
    }
    
    public void lampStatus(int lampNumber)
    {
        if(lampNumber == 1 || lampNumber == 2){
            sendCommand(CP.getLampStatus(lampNumber));
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
