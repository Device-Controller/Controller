/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package som.vislabcontroller;

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
    private final static String HEADER = ":";
    private final static String TERMINATOR = "CR";

    public static void powerON(String address, int port, int powerSetting){
        if(powerSetting == 1 || powerSetting == 0){
        Socket socket;
        try {
            PrintWriter pw = null;
            BufferedReader br = null;
            InetAddress host = InetAddress.getByName(address);
            try{
                socket = new Socket(host, port);
                pw = new PrintWriter(socket.getOutputStream());
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pw.println(HEADER + "POWR" + powerSetting + TERMINATOR);
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
        } else {
            System.out.println("FEIL SETTING DIN TAPER");
        }
        
    }
}
