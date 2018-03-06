package no.ntnu.vislab.barkof22;

import no.ntnu.vislab.vislabcontroller.Command;

/**
 *
 * @author ThomasSTodal
 */
public abstract class BarkoF22Command extends Command {
    
    protected static final String RELATIVE_VALUE    = "R";

    protected static final String GET_CURRENT       = "?";
    protected static final String GET_MAX           = "?M";
    protected static final String GET_MIN           = "?N";
    protected static final String GET_DEFAULT       = "?D";
    protected static final String GET_DEFAULT_STEP  = "?S";

    /**
     *
     * @param command
     */
    public BarkoF22Command(String command) {
        super(":", "");
        setCmd(getPrefix() + command + getSuffix());
    }

    /**
     *
     */
    public BarkoF22Command() {
        super(":", "");
    }
}
