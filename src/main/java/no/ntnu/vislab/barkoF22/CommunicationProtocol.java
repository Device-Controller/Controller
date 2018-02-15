package no.ntnu.vislab.barkoF22;

/**
 * This class contains all of the commands that the application uses to
 * communicate with the projectors.
 *
 * @author Thomas
 */
public class CommunicationProtocol {
    private static final String HEADER = ":";
    private static final String TERMINATOR = "CR";
    private static final String POWER = "POWR";
    private static final String POWER_STATE = "POST?";
    private static final String MUTE = "PMUT";
    private static final String BRIGHTNESS_GET = "BRIG?";
    private static final String BRIGHTNESS_SET = "BRIG";
    private static final String CONTRAST_GET = "CNTR?";
    private static final String CONTRAST_SET = "CNTR";
    private static final String LAMP_RUNTIME = "LTR";
    private static final String LAMP_EST_TIME_REMAINING = "LRM";
    private static final String LAMP_STATUS = "LST";
    private static final String UNIT_TOT_TIME = "UTOT?";
    private static final String THERMAL =  "THRM?";
    
    private static String assembleCommand(String cmd)
    {
        return HEADER + cmd + TERMINATOR;
    }

    public static String getPower(int pwrState)
    {
        return assembleCommand(POWER + pwrState);
    }

    public static String getPowerState()
    {
        return assembleCommand(POWER_STATE);
    }

    public static String getMute(int muteState)
    {
        return assembleCommand(MUTE + muteState);
    }

    public static String getBrightnessGet()
    {
        return assembleCommand(BRIGHTNESS_GET);
    }

    public static String getBrightnessSet(int brightnessVal)
    {
        return assembleCommand(BRIGHTNESS_SET + brightnessVal);
    }

    public static String getContrastGet()
    {
        return assembleCommand(CONTRAST_GET + TERMINATOR);
    }

    public static String getContrastSet(int contrastVal)
    {
        return assembleCommand(CONTRAST_SET + contrastVal);
    }

    public static String getLampRuntime(int lampNo)
    {
        return assembleCommand(LAMP_RUNTIME + lampNo + "?");
    }

    public static String getLampEstTimeRemaining(int lampNo)
    {
        return assembleCommand(LAMP_EST_TIME_REMAINING + lampNo + "?");
    }

    public static String getLampStatus(int lampNo)
    {
        return assembleCommand(LAMP_STATUS + lampNo + "?");
    }

    public static String getUnitTotTime()
    {
        return assembleCommand(UNIT_TOT_TIME);
    }

    public static String getThermal()
    {
        return assembleCommand(THERMAL);
    }
    
    
}
