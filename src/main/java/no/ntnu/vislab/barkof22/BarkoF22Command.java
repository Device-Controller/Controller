package no.ntnu.vislab.barkof22;

import no.ntnu.vislab.vislabcontroller.Command;

/**
 *
 * @author ThomasSTodal
 */
public class BarkoF22Command extends Command {
    
    protected static final String RELATIVE_VALUE    = "R";

    protected static final String GET_CURRENT       = "?";
    protected static final String GET_MAX           = "?M";
    protected static final String GET_MIN           = "?N";
    protected static final String GET_DEFAULT       = "?D";
    protected static final String GET_DEFAULT_STEP  = "?S";

    private static final String POWER                   = "POWR";
    private static final String POWER_STATE             = "POST";
    private static final String MUTE                    = "PMUT";
    private static final String BRIGHTNESS              = "BRIG";
    private static final String CONTRAST                = "CNTR";
    private static final String LAMP_RUNTIME            = "LTR";
    private static final String LAMP_EST_TIME_REMAINING = "LRM";
    private static final String LAMP_STATUS             = "LST";
    private static final String UNIT_TOT_TIME           = "UTOT";
    private static final String THERMAL                 = "THRM";
    private static final String TEST                    = "TEST";

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

    public static Command powerOn() {
        return new BarkoF22Command(POWER + 1);
    }

    public static Command powerOff() {
        return new BarkoF22Command(POWER + 0);
    }

    public static Command powerState() {
        return new BarkoF22Command(POWER_STATE + GET_CURRENT);
    }

    public static Command mute() {
        return new BarkoF22Command(MUTE + 1);
    }

    public static Command unMute(){
        return new BarkoF22Command(MUTE + 0);
    }

    public static Command getBrightness() {
        return new BarkoF22Command(BRIGHTNESS + GET_CURRENT);
    }

    public static Command setBrightness(int value) {
        return new BarkoF22Command(BRIGHTNESS + value);
    }

    public static Command getContrast() {
        return new BarkoF22Command(CONTRAST + GET_CURRENT);
    }

    public static Command setContrast(int value) {
        return new BarkoF22Command(CONTRAST + value);
    }

    public static Command lamp1Runtime() {
        return new BarkoF22Command(LAMP_RUNTIME + 1 + GET_CURRENT);
    }

    public static Command lamp1EstTimeRemaining() {
        return new BarkoF22Command(LAMP_EST_TIME_REMAINING + 1 + GET_CURRENT);
    }

    public static Command lamp1Status() {
        return new BarkoF22Command(LAMP_STATUS + 1 + GET_CURRENT);
    }

    public static Command lamp2Runtime() {
        return new BarkoF22Command(LAMP_RUNTIME + 2 + GET_CURRENT);
    }

    public static Command lamp2EstTimeRemaining() {
        return new BarkoF22Command(LAMP_EST_TIME_REMAINING + 2 + GET_CURRENT);
    }

    public static Command lamp2Status() {
        return new BarkoF22Command(LAMP_STATUS + 2 + GET_CURRENT);
    }

    public static Command totalUnitTime() {
        return new BarkoF22Command(UNIT_TOT_TIME + GET_CURRENT);
    }

    public static Command thermalStatus() {
        return new BarkoF22Command(THERMAL + GET_CURRENT);
    }
    
    public static Command testImageOn() {
        return new BarkoF22Command(TEST + 2); // 0-7
    }
    
    public static Command testImageOff() { return new BarkoF22Command(TEST + 0); }

    public boolean isPowerOnCommand() {
        return this.equals(BarkoF22Command.powerOn());
    }
    
    public String whoIsAwesome() {
        return "Thomas is awesome!";
    }

    @Override
    public boolean checkAck() { return false; }

    @Override
    public String getCmd() {
        return this.toString();
    }
}
