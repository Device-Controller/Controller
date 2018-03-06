package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class UnMute extends BarkoF22Command {
    private static final String UNMUTE = "PMUT0";

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
        String[] ackArray = getResponse().split(" ");
        int value = Integer.parseInt(ackArray[2]);
        return ackArray[1] == "PMUT" && value == 0;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.getPrefix() + UnMute.UNMUTE + this.getSuffix();
    }
}
