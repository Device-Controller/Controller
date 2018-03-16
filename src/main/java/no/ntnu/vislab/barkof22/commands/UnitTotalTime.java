package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class UnitTotalTime extends BarkoF22Command {
    private static final String TOTAL_TIME = "UTOT";

    /**
     *
     */
    public UnitTotalTime() {
        super(TOTAL_TIME);
    }

    /**
     *
     * @return
     */
    public int getTotalRuntime() {
        return getValue();
    }
}
