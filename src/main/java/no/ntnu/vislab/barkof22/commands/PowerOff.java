package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class PowerOff extends BarkoF22Command {
    private static final String POWER_OFF = "POWR0";

    /**
     *
     */
    public PowerOff() {
        this.setCmd(getPrefix() + PowerOff.POWER_OFF + this.getSuffix());
    }

    /**
     * @return
     */
    @Override
    public boolean checkAck() {
        String[] ackArray = getResponse().split(" ");
        int value = Integer.parseInt(ackArray[2]);
        return ackArray[1] == "POWR" && value == 0;
    }
}