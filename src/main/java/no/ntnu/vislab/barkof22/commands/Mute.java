package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class Mute extends BarkoF22Command {
    private static final String MUTE = "PMUT";
    private static final int MUTE_SETTING = 1;

    /**
     *
     */
    public Mute() {
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
            return ackArray[1].equals(MUTE) && (value == 1);
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
        return getPrefix() + Mute.MUTE  + MUTE_SETTING + this.getSuffix();
    }
}
