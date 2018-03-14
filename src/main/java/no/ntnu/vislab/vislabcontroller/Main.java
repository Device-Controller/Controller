package no.ntnu.vislab.vislabcontroller;


import java.net.Socket;

import no.ntnu.vislab.barkof22.CommunicationDriver;
import no.ntnu.vislab.barkof22.Timer;
import no.ntnu.vislab.barkof22.commands.LampStatus;
import no.ntnu.vislab.barkof22.commands.PowerState;

/**
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Socket host = new Socket("158.38.101.110", 1025);
        CommunicationDriver cd = new CommunicationDriver(host, new LampStatus(1), new PowerState());
        cd.start();
        Timer t = new Timer();
        while(!t.hasTimerPassed(35000)){
        }
        cd.stopThread();
    }
}
