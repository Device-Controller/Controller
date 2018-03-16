package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class PowerState extends BarkoF22Command {
    private static final String POWER_STATE = "POST";
    private static final int MAX_VALUE = 6;
    private static final int MIN_VALUE = 0;

    /**
     *
     */
    public PowerState() {
        super(POWER_STATE, MAX_VALUE, MIN_VALUE);
    }

    /**
     *
     * @return
     */
    public int getPowerState() {
        return getValue();
    }
}
