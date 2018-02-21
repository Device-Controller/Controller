package no.ntnu.vislab.vislabcontroller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.vislab.barkof22.BarkoF22Command;
import no.ntnu.vislab.barkof22.CommunicationRunnable;

/**
 *
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) {
        try {
            int seq1 = 158;
            int seq2 = 38;
            int seq3 = 65;
            int seq4 = 45;

            CommunicationRunnable cr = new CommunicationRunnable(InetAddress.getByName(seq1 + "." + seq2 + "." + seq3 + "." + seq4), 1025);
            Thread t1 = new Thread(cr);
            t1.start();
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {
                String input = sc.nextLine();
                switch (input) {
                    case "poweron":
                        cr.sendCommand(BarkoF22Command.powerOn());
                        System.out.println("PÅ");
                        break;
                    case "poweroff":
                        cr.sendCommand(BarkoF22Command.powerOff());
                        System.out.println("AV");
                        break;
                    case "powerstate":
                        cr.sendCommand(BarkoF22Command.powerState());
                        break;
                    case "settings":
                        System.out.println("RIGHTY-O");
                        //c.retrieveSettings();
                        break;
                    case "mute":
                        cr.sendCommand(BarkoF22Command.mute());
                        System.out.println("MUTED");
                        break;
                    case "unmute":
                        cr.sendCommand(BarkoF22Command.unMute());
                        break;
                    case "lamptime1":
                        cr.sendCommand(BarkoF22Command.lamp1Runtime());
                        break;
                    case "lamptime2":
                        cr.sendCommand(BarkoF22Command.lamp2Runtime());
                        break;
                    case "lampstatus1":
                        cr.sendCommand(BarkoF22Command.lamp1Status());
                        break;
                    case "lampstatus2":
                        cr.sendCommand(BarkoF22Command.lamp2Status());
                        break;
                    case "quit":
                        System.out.println("HADE");
                        return;
                    default:
                        break;
                }
                sc = new Scanner(System.in);
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
