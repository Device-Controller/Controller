package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class Mute extends BarkoF22Command {
    private static final String MUTE = "PMUT1";

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
        String[] ackArray = getResponse().split(" ");
        int value = Integer.parseInt(ackArray[2]);
        return ackArray[1] == "PMUT" && value == 1;
    }

    /**
     *
     * @return
     */
    @Override
    public String getCmd() {
        return getPrefix() + Mute.MUTE + this.getSuffix();
    }
}
