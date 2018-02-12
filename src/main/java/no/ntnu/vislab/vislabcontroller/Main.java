package no.ntnu.vislab.vislabcontroller;

import java.util.Scanner;

/**
 *
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) {
        Connector c = new Connector("158.38.65." + 45, 1025);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String input = sc.nextLine();
            switch (input) {
                case "poweron":
                    c.powerON(1);
                    System.out.println("PÅ");
                    break;
                case "poweroff":
                    c.powerON(0);
                    System.out.println("AV");
                    break;
                case "powerstate":
                    c.powerState();
                    break;
                case "settings":
                    System.out.println("SETTINGS");
                    c.retrieveSettings();
                    break;
                case "mute":
                    c.muteImage(1);
                    System.out.println("MUTED");
                    break;
                case "unmute":
                    c.muteImage(0);
                    break;
                case "lamptime1":
                    c.lampTime(1);
                    break;
                case "lamptime2":
                    c.lampTime(2);
                    break;
                case "lampstatus1":
                    c.lampStatus(1);
                    break;
                case "lampstatus2":
                    c.lampStatus(2);
                    break;
                case "quit":
                    System.out.println("HADE");
                    return;
                default:
                    break;
            }
            sc = new Scanner(System.in);
        }

        //c.powerON(1);
        //c.retrieveSettings();
    }
}
