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
    public PowerOn() {  }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        try {
            String[] ackArray = getResponse().split(" ");
            int value = Integer.parseInt(ackArray[2]);
            return ackArray[1].equals(PowerOn.POWER_ON) && (value == 1);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            return false;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getPrefix() + PowerOn.POWER_ON + this.getSuffix();
    }
}
