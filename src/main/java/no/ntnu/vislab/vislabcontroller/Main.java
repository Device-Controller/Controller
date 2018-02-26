package no.ntnu.vislab.vislabcontroller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.vislab.barkof22.BarkoF22Cmd;
import no.ntnu.vislab.barkof22.BarkoF22Projector;
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

            Projector f22 = new BarkoF22Projector(InetAddress.getByName(seq1 + "." + seq2 + "." + seq3 + "." + seq4), 1025);
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {
                String input = sc.nextLine();
                switch (input) {
                    case "poweron":
                        f22.powerOn();
                        break;
                    case "poweroff":
                        f22.powerOff();
                        break;
                    case "powerstate":
                        f22.getPowerState();
                        break;
                    case "settings":
                        System.out.println("RIGHTY-O");
                        //c.retrieveSettings();
                        break;
                    case "mute":
                        f22.mute();
                        break;
                    case "unmute":
                        f22.unMute();
                        break;
                    case "lamptime1":
                        f22.getLampRuntime(1);
                        break;
                    case "lamptime2":
                        f22.getLampRuntime(2);
                        break;
                    case "lampstatus1":
                        f22.getLampStatus(1);
                        break;
                    case "lampstatus2":
                        f22.getLampStatus(2);
                        break;
                    case "teston":
                        f22.testImageOn();
                        break;
                    case "testoff":
                        f22.testImageOff();
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
