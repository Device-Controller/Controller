package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class GetContrast extends BarkoF22Command {
    private static final String CONTRAST = "CNTR";
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = 0;

    /**
     *
     */
    public GetContrast() {
        super(CONTRAST, MAX_VALUE, MIN_VALUE);
    }
}
