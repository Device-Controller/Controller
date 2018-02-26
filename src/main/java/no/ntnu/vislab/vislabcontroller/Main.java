package no.ntnu.vislab.vislabcontroller;

import no.ntnu.vislab.barkof22.BarkoF22Command;
import no.ntnu.vislab.barkof22.Communicator;

/**
 *
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Communicator c = new Communicator(System.out, System.in);
        c.start();
        while (true) {
            c.sendCommand(BarkoF22Command.getBrightness());
        }

    }
}
