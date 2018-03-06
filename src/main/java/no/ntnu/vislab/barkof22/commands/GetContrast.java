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
        String[] ackArray = getResponse().split(" ");
        int value = Integer.parseInt(ackArray[2]);
        return ackArray[1] == "CNTR" && value >= 0 && value <= 100;
    }

    @Override
    public String toString() {
        return this.getPrefix() + GetContrast.CONTRAST + this.GET_CURRENT + this.getSuffix();
    }
}
