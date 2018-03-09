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
    public UnMute() {
        super(UNMUTE, UNMUTE_SETTING);
    }

}
