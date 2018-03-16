package no.ntnu.vislab.vislabcontroller;


import java.net.Socket;

import no.ntnu.vislab.barkof22.CommunicationDriver;
import no.ntnu.vislab.barkof22.commands.Brightness;
import no.ntnu.vislab.barkof22.commands.Contrast;
import no.ntnu.vislab.barkof22.commands.LampRuntime;
import no.ntnu.vislab.barkof22.commands.LampStatus;
import no.ntnu.vislab.barkof22.commands.LampTimeRemaining;
import no.ntnu.vislab.barkof22.commands.Mute;
import no.ntnu.vislab.barkof22.commands.Power;
import no.ntnu.vislab.barkof22.commands.PowerState;
import no.ntnu.vislab.barkof22.commands.TestImage;
import no.ntnu.vislab.barkof22.commands.ThermalStatus;
import no.ntnu.vislab.barkof22.commands.UnitTotalTime;

/**
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 12; i ++) {
            Socket host = new Socket("158.38.101.110", 1025);
            CommunicationDriver cd = new CommunicationDriver(host, new LampStatus(1), new PowerState());
            cd.start();
            cd.queueCommand(new PowerState());
            cd.queueCommand(new LampStatus(1));
            cd.queueCommand(new LampStatus(2));
            cd.queueCommand(new LampRuntime(1));
            cd.queueCommand(new LampRuntime(2));
            cd.queueCommand(new LampTimeRemaining(1));
            cd.queueCommand(new LampTimeRemaining(2));
            cd.queueCommand(new Mute(Mute.ON));
            cd.queueCommand(new Mute());
            cd.queueCommand(new Mute(Mute.OFF));
            cd.queueCommand(new Brightness());
            cd.queueCommand(new Brightness(100, false));
            cd.queueCommand(new Brightness(0, true));
            cd.queueCommand(new Contrast());
            cd.queueCommand(new Contrast(100, false));
            cd.queueCommand(new Contrast(0, true));
            cd.queueCommand(new TestImage(4));
            cd.queueCommand(new ThermalStatus());
            cd.queueCommand(new UnitTotalTime());
            cd.queueCommand(new Power(Power.ON));
            cd.queueCommand(new Power());
            cd.queueCommand(new Power(Power.OFF));
        }
    }
}
