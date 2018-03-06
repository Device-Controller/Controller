package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class GetContrast extends BarkoF22Command {
    private static final String CONTRAST = "CNTR";

    /**
     *
     */
    public GetContrast() {  }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        try {
            String[] ackArray = getResponse().split(" ");
            int value = Integer.parseInt(ackArray[2]);
            return ackArray[1].equals(GetContrast.CONTRAST) && (value >= 0) && (value <= 100);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.getPrefix() + GetContrast.CONTRAST + this.GET_CURRENT + this.getSuffix();
    }
}
