package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class PowerOff extends BarkoF22Command {
    private static final String POWER_OFF = "POWR";
    private static final int POWER_SETTING = 0;

    /**
     *
     */
    public PowerOff() {
        super(POWER_OFF, POWER_SETTING);
    }
}