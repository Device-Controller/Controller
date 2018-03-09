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
        try {
            String[] ackArray = getResponse().split(" ");
            int value = Integer.parseInt(ackArray[2]);
            return ackArray[1].equals(GetBrightness.BRIGHTNESS) && (value >= 0) && (value <= 100);
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
        return this.getPrefix() + GetBrightness.BRIGHTNESS + this.GET_CURRENT + this.getSuffix();
    }
}
