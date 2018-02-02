/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
            if (sc.next().equals("poweron")) {
                c.powerON(1);
                System.out.println("PÅ");
            } else if (sc.next().equals("poweroff")) {
                c.powerON(0);
                System.out.println("AV");
            } else if (sc.next().equals("settings")) {
                System.out.println("SETTINGS");
                c.retrieveSettings();
            } else if (sc.next().equals("quit")) {
                System.out.println("HADE");
                return;
            }
        }

        //c.powerON(1);
        //c.retrieveSettings();
    }
}
