package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class PowerState extends BarkoF22Command {
    private static final String POWER_STATE = "POST";

    /**
     *
     */
    public PowerState() {
    }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        String[] ackArray = this.getResponse().split(" ");
        int value = Integer.parseInt(ackArray[2]);
        return ackArray[1] == "POST" && value >= 0 && value <= 6;
    }

    @Override
    public String toString() {
        return this.getPrefix() + PowerState.POWER_STATE + this.GET_CURRENT + this.getSuffix();
    }
}
