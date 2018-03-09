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
        super(MUTE, MUTE_SETTING);
    }

}
