package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class GetBrightness extends BarkoF22Command {
    private static final String BRIGHTNESS = "BRIG";
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = 0;

    /**
     *
     */
    public GetBrightness() {
        super(BRIGHTNESS, MAX_VALUE, MIN_VALUE);
    }
}
