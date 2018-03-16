package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class PowerOn extends BarkoF22Command {

    private static final String POWER_ON = "POWR";
    private static final int POWER_SETTING = 1;

    /**
     *
     */
    public PowerOn() {
        super(POWER_ON, POWER_SETTING);
    }

    public int getPowerSetting() {
        return getValue();
    }
}
