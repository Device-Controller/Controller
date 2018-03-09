package no.ntnu.vislab.vislabcontroller;


import java.util.ArrayList;

import no.ntnu.vislab.barkof22.commands.GetBrightness;
import no.ntnu.vislab.barkof22.commands.GetContrast;
import no.ntnu.vislab.barkof22.commands.LampRuntime;
import no.ntnu.vislab.barkof22.commands.LampStatus;
import no.ntnu.vislab.barkof22.commands.LampTimeRemaining;
import no.ntnu.vislab.barkof22.commands.Mute;
import no.ntnu.vislab.barkof22.commands.PowerOff;
import no.ntnu.vislab.barkof22.commands.PowerOn;
import no.ntnu.vislab.barkof22.commands.PowerState;
import no.ntnu.vislab.barkof22.commands.SetBrightness;
import no.ntnu.vislab.barkof22.commands.SetContrast;
import no.ntnu.vislab.barkof22.commands.TestImage;
import no.ntnu.vislab.barkof22.commands.ThermalStatus;
import no.ntnu.vislab.barkof22.commands.UnMute;
import no.ntnu.vislab.barkof22.commands.UnitTotalTime;

/**
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ArrayList<Command> cmds = new ArrayList<>();
        cmds.add(new GetBrightness());
        cmds.add(new GetContrast());
        cmds.add(new LampRuntime(1));
        cmds.add(new LampRuntime(2));
        cmds.add(new LampStatus(1));
        cmds.add(new LampStatus(2));
        cmds.add(new LampTimeRemaining(1));
        cmds.add(new LampTimeRemaining(2));
        cmds.add(new Mute());
        cmds.add(new UnMute());
        cmds.add(new PowerOn());
        cmds.add(new PowerOff());
        cmds.add(new PowerState());
        cmds.add(new SetBrightness(0,false));
        cmds.add(new SetBrightness(100,false));
        cmds.add(new SetBrightness(0,true));
        cmds.add(new SetBrightness(100,true));
        cmds.add(new SetContrast(-100,false));
        cmds.add(new SetContrast(0,false));
        cmds.add(new SetContrast(100,false));
        cmds.add(new SetContrast(-100,true));
        cmds.add(new SetContrast(0,true));
        cmds.add(new SetContrast(100,true));
        cmds.add(new TestImage(0));
        cmds.add(new TestImage(7));
        cmds.add(new ThermalStatus());
        cmds.add(new UnitTotalTime());
        cmds.forEach(e-> System.out.println(e.toString()));
    }

}
