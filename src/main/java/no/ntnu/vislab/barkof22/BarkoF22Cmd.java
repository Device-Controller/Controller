package no.ntnu.vislab.barkof22;

import no.ntnu.vislab.vislabcontroller.Cmd;

/**
 *
 * @author ThomasSTodal
 */
public class BarkoF22Cmd extends Cmd {

    private static final String GET = "?";

    private static final String POWER = "POWR";
    private static final String POWER_STATE = "POST";
    private static final String MUTE = "PMUT";
    private static final String BRIGHTNESS = "BRIG";
    private static final String CONTRAST = "CNTR";
    private static final String LAMP_RUNTIME = "LTR";
    private static final String LAMP_EST_TIME_REMAINING = "LRM";
    private static final String LAMP_STATUS = "LST";
    private static final String UNIT_TOT_TIME = "UTOT";
    private static final String THERMAL = "THRM";
    private static final String TEST = "TEST";

    public BarkoF22Cmd(String command) {
        super(":", "CR");
        setCmd(getPrefix() + command + getSuffix());
    }
    
    public BarkoF22Cmd() {
        super(":", "CR");
    }

    public static Cmd powerOn() {
        return new BarkoF22Cmd(POWER + 1);
    }

    public static Cmd powerOff() {
        return new BarkoF22Cmd(POWER + 0);
    }

    public static Cmd powerState() {
        return new BarkoF22Cmd(POWER_STATE + GET);
    }

    public static Cmd mute() {
        return new BarkoF22Cmd(MUTE + 1);
    }

    public static Cmd unMute(){
        return new BarkoF22Cmd(MUTE + 0);
    }

    public static Cmd getBrightness() {
        return new BarkoF22Cmd(BRIGHTNESS + GET);
    }

    public static Cmd setBrightness(int value) {
        return new BarkoF22Cmd(BRIGHTNESS + value);
    }

    public static Cmd getContrast() {
        return new BarkoF22Cmd(CONTRAST + GET);
    }

    public static Cmd setContrast(int value) {
        return new BarkoF22Cmd(CONTRAST + value);
    }

    public static Cmd lamp1Runtime() {
        return new BarkoF22Cmd(LAMP_RUNTIME + 1 + GET);
    }

    public static Cmd lamp1EstTimeRemaining() {
        return new BarkoF22Cmd(LAMP_EST_TIME_REMAINING + 1 + GET);
    }

    public static Cmd lamp1Status() {
        return new BarkoF22Cmd(LAMP_STATUS + 1 + GET);
    }

    public static Cmd lamp2Runtime() {
        return new BarkoF22Cmd(LAMP_RUNTIME + 2 + GET);
    }

    public static Cmd lamp2EstTimeRemaining() {
        return new BarkoF22Cmd(LAMP_EST_TIME_REMAINING + 2 + GET);
    }

    public static Cmd lamp2Status() {
        return new BarkoF22Cmd(LAMP_STATUS + 2 + GET);
    }

    public static Cmd totalUnitTime() {
        return new BarkoF22Cmd(UNIT_TOT_TIME + GET);
    }

    public static Cmd thermalStatus() {
        return new BarkoF22Cmd(THERMAL + GET);
    }
    
    public static Cmd testImageOn() {
        return new BarkoF22Cmd(TEST + 2);
    }
    
    public static Cmd testImageOff() {
        return new BarkoF22Cmd(TEST + 0);
    }

    public boolean isPowerOnCommand() {
        return this.equals(BarkoF22Cmd.powerOn());
    }
    
    public String whoIsAwesome() {
        return "Thomas is awesome!";
    }
}
