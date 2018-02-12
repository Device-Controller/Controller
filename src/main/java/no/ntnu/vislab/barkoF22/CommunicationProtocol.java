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

    public static String getPOWER(int pwrState)
    {
        return HEADER + POWER + pwrState + TERMINATOR;
    }

    public static String getPOWER_STATE()
    {
        return HEADER + POWER_STATE + TERMINATOR;
    }

    public static String getMUTE(int muteState)
    {
        return HEADER + MUTE + muteState + TERMINATOR;
    }

    public static String getBRIGHTNESS_GET()
    {
        return HEADER + BRIGHTNESS_GET + TERMINATOR;
    }

    public static String getBRIGHTNESS_SET(int brightnessVal)
    {
        return HEADER + BRIGHTNESS_SET + brightnessVal + TERMINATOR;
    }

    public static String getCONTRAST_GET()
    {
        return HEADER + CONTRAST_GET + TERMINATOR;
    }

    public static String getCONTRAST_SET(int contrastVal)
    {
        return HEADER + CONTRAST_SET + contrastVal + TERMINATOR;
    }

    public static String getLAMP_RUNTIME(int lampNo)
    {
        return HEADER + LAMP_RUNTIME + lampNo + "?" + TERMINATOR;
    }

    public static String getLAMP_EST_TIME_REMAINING(int lampNo)
    {
        return HEADER + LAMP_EST_TIME_REMAINING + lampNo + "?" + TERMINATOR;
    }

    public static String getLAMP_STATUS(int lampNo)
    {
        return HEADER + LAMP_STATUS + lampNo + "?" + TERMINATOR;
    }

    public static String getUNIT_TOT_TIME()
    {
        return HEADER + UNIT_TOT_TIME + TERMINATOR;
    }

    public static String getTHERMAL()
    {
        return HEADER + THERMAL + TERMINATOR;
    }
    
    
}
