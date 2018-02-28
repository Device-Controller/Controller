package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class PowerOn extends BarkoF22Command {

    private static final String POWER_ON = "POWR1";

    /**
     *
     */
    public PowerOn() {
        this.setCmd(getPrefix() + PowerOn.POWER_ON + this.getSuffix());
    }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        String[] ackArray = getResponse().split(" ");
        int value = Integer.parseInt(ackArray[2]);
        return ackArray[1] == "POWR" && value == 1;
    }
}
