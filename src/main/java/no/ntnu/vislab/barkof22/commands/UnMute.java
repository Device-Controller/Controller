package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class UnMute extends BarkoF22Command {
    private static final String UNMUTE = "PMUT";
    private static final int UNMUTE_SETTING = 0;

    /**
     *
     */
    public UnMute() {  }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        try {
            String[] ackArray = getResponse().split(" ");
            int value = Integer.parseInt(ackArray[2]);
            return ackArray[1].equals(UNMUTE) && (value == 0);
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
        return this.getPrefix() + UNMUTE  + UNMUTE_SETTING+ this.getSuffix();
    }
}
