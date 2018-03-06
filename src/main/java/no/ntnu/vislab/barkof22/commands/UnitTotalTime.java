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
    }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        String[] ackArray = getResponse().split(" ");
        return ackArray[1] == "UTOT";
    }

    @Override
    public String toString() {
        return this.getPrefix() + UnitTotalTime.TOTAL_TIME + this.GET_CURRENT
                + this.getSuffix();
    }
}
