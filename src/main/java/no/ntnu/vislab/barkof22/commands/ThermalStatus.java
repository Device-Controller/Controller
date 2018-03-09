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
    }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        try {
            String[] ackArray = getResponse().split(" ");
            int value = Integer.parseInt(ackArray[2]);
            return ackArray[1].equals(ThermalStatus.THERMAL);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.getPrefix() + ThermalStatus.THERMAL + this.GET_CURRENT
                + this.getSuffix();
    }
}
