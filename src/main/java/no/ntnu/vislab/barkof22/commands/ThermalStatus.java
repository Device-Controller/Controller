package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class ThermalStatus extends BarkoF22Command {
    private static final String THERMAL = "THRM";

    /**
     *
     */
    public ThermalStatus() {
        super(THERMAL);
    }

    /**
     *
     * @return
     */
    public int getThermal() {
        return getValue();
    }
}
