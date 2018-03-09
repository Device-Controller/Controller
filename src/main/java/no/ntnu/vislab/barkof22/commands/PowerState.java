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
        try {
            String[] ackArray = this.getResponse().split(" ");
            int value = Integer.parseInt(ackArray[2]);
            return ackArray[1].equals(PowerState.POWER_STATE) && (value >= 0) && (value <= 6);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.getPrefix() + PowerState.POWER_STATE + this.GET_CURRENT + this.getSuffix();
    }
}
