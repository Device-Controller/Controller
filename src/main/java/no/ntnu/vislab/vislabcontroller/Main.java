package no.ntnu.vislab.vislabcontroller;


import java.net.Socket;

import no.ntnu.vislab.barkof22.CommunicationDriver;
import no.ntnu.vislab.barkof22.Timer;
import no.ntnu.vislab.barkof22.commands.LampStatus;
import no.ntnu.vislab.barkof22.commands.Mute;
import no.ntnu.vislab.barkof22.commands.PowerState;
import no.ntnu.vislab.barkof22.commands.UnMute;

/**
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Socket host = new Socket("158.38.101.110", 1025);
        CommunicationDriver cd = new CommunicationDriver(host, new LampStatus(1), new PowerState());
        cd.start();
        Timer t = new Timer();
        cd.queCommand(new Mute());
        cd.queCommand(new Mute());
        cd.queCommand(new Mute());
        cd.queCommand(new Mute());
        cd.queCommand(new Mute());
        cd.queCommand(new Mute());
        while(!t.hasTimerPassed(20000)){

        }
        t.reset();
        cd.queCommand(new UnMute());
        while(!t.hasTimerPassed(8000)){

        }
        cd.stopThread();
    }
}
