package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class PowerOff extends BarkoF22Command {
    private static final String POWER_OFF = "POWR0";

    /**
     *
     */
    public PowerOff() {
    }

    /**
     * @return
     */
    @Override
    public boolean checkAck() {
        try {
            String[] ackArray = getResponse().split(" ");
            int value = Integer.parseInt(ackArray[2]);
            return ackArray[1].equals(PowerOff.POWER_OFF) && (value == 0);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            return false;
        }
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return getPrefix() + PowerOff.POWER_OFF + this.getSuffix();
    }
}