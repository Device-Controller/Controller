package no.ntnu.vislab.vislabcontroller;


import java.io.IOException;
import java.net.Socket;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.barkof22.commands.LampStatus;

/**
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        CommunicationContext context = new CommunicationContext(new Socket("158.38.101.110", 1025));
        while (true) {
            context.sendCommand(new LampStatus());
        }
    }
}
