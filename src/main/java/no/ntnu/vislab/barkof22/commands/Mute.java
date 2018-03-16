package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;
import no.ntnu.vislab.barkof22.BarkoF22Exception;

/**
 *
 * @author ThomasSTodal
 */
public class Mute extends BarkoF22Command {
    private static final String MUTE = "PMUT";
    private static final int MUTE_SETTING = 1;
    public static final int ON = 1;
    public static final int OFF = 0;

    /**
     *
     */
    public Mute(){
        super(MUTE);
    }

    /**
     *
      * @param setting
     * @throws BarkoF22Exception
     */
    public Mute(int setting) throws BarkoF22Exception {
        super(MUTE, checkSetting(setting));
    }

    /**
     *
     * @return
     */
    public int getMuteSetting(){
        return getValue();
    }

    /**
     *
     * @param setting
     * @return
     * @throws BarkoF22Exception
     */
    private static int checkSetting(int setting) throws BarkoF22Exception {
        if(setting != ON && setting != OFF){
            throw new BarkoF22Exception("Power setting must be 0 or 1");
        }
        return setting;
    }
}
