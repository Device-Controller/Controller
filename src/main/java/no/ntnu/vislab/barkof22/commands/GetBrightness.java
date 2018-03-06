package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class GetBrightness extends BarkoF22Command {
    private static final String BRIGHTNESS = "BRIG";

    /**
     *
     */
    public GetBrightness() {
    }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        String[] ackArray = getResponse().split(" ");
        int value = Integer.parseInt(ackArray[2]);
        return ackArray[1] == BRIGHTNESS && value >= 0 && value <= 100;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.getPrefix() + GetBrightness.BRIGHTNESS + this.GET_CURRENT + this.getSuffix();
    }
}
