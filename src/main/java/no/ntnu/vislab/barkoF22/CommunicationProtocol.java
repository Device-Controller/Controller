package no.ntnu.vislab.barkoF22;

/**
 * This class contains all of the commands that the application uses to
 * communicate with the projectors.
 *
 * @author Thomas
 */
public class CommunicationProtocol {
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

    public static String getPower(int pwrState)
    {
        BarkoF22Command power = new BarkoF22Command(POWER + pwrState);
        return power.toString();
    }

    public static String getPowerState()
    {
        BarkoF22Command powerState = new BarkoF22Command(POWER_STATE);
        return powerState.toString();
    }

    public static String getMute(int muteState)
    {
        BarkoF22Command mute = new BarkoF22Command(MUTE + muteState);
        return mute.toString();
    }

    public static String getBrightnessGet()
    {
        BarkoF22Command brightnessGet = new BarkoF22Command(BRIGHTNESS_GET);
        return brightnessGet.toString();
    }

    public static String getBrightnessSet(int brightnessVal)
    {
        BarkoF22Command brightnessSet;
        brightnessSet = new BarkoF22Command(BRIGHTNESS_SET + brightnessVal);
        return brightnessSet.toString();
    }

    public static String getContrastGet()
    {
        BarkoF22Command contrastGet = new BarkoF22Command(CONTRAST_GET);
        return contrastGet.toString();
    }

    public static String getContrastSet(int contrastVal)
    {
        BarkoF22Command contrastSet;
        contrastSet = new BarkoF22Command(CONTRAST_SET + contrastVal);
        return contrastSet.toString();
    }

    public static String getLampRuntime(int lampNo)
    {
        BarkoF22Command lampRuntime;
        lampRuntime = new BarkoF22Command(LAMP_RUNTIME + lampNo + "?");
        return lampRuntime.toString();
    }

    public static String getLampEstTimeRemaining(int lampNo)
    {
        BarkoF22Command lampTimeRemaining;
        lampTimeRemaining = new BarkoF22Command(LAMP_EST_TIME_REMAINING
                + lampNo + "?");
        return lampTimeRemaining.toString();
    }

    public static String getLampStatus(int lampNo)
    {
        BarkoF22Command lampStatus;
        lampStatus = new BarkoF22Command(LAMP_STATUS + lampNo + "?");
        return lampStatus.toString();
    }

    public static String getUnitTotTime()
    {
        BarkoF22Command unitTotTime = new BarkoF22Command(UNIT_TOT_TIME);
        return unitTotTime.toString();
    }

    public static String getThermal()
    {
        BarkoF22Command thermal = new BarkoF22Command(THERMAL);
        return thermal.toString();
    }
    
    
}
