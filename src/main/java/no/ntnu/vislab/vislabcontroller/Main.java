package no.ntnu.vislab.vislabcontroller;

import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.vislab.barkoF22.BarkoF22Command;
import no.ntnu.vislab.barkoF22.CommunicationRunnable;

/**
 *
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) {
        
        CommunicationRunnable cr = null;
        try {
            cr = new CommunicationRunnable("158.38.65.45", 1025);
            Thread t1 = new Thread(cr);
            t1.start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String input = sc.nextLine();
            switch (input) {
                case "poweron":
                    cr.sendCommand(BarkoF22Command.powerOn().toString());
                    System.out.println("PÅ");
                    break;
                case "poweroff":
                    cr.sendCommand(BarkoF22Command.powerOff().toString());
                    System.out.println("AV");
                    break;
                case "powerstate":
                    cr.sendCommand(BarkoF22Command.powerState().toString());
                    break;
                case "settings":
                    System.out.println("RIGHTY-O");
                    //c.retrieveSettings();
                    break;
                case "mute":
                    cr.sendCommand(BarkoF22Command.mute().toString());
                    System.out.println("MUTED");
                    break;
                case "unmute":
                    cr.sendCommand(BarkoF22Command.unmute().toString());
                    break;
                case "lamptime1":
                    cr.sendCommand(BarkoF22Command.lamp1Runtime().toString());
                    break;
                case "lamptime2":
                    cr.sendCommand(BarkoF22Command.lamp2Runtime().toString());
                    break;
                case "lampstatus1":
                    cr.sendCommand(BarkoF22Command.lamp1Status().toString());
                    break;
                case "lampstatus2":
                    cr.sendCommand(BarkoF22Command.lamp2Status().toString());
                    break;
                case "quit":
                    System.out.println("HADE");
                    return;
                default:
                    break;
            }
            sc = new Scanner(System.in);
        }
    }
}
