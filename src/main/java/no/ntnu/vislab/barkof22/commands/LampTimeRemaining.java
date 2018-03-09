package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class LampTimeRemaining extends BarkoF22Command {
    private static final String LAMP_TIME_REMAINING = "LRM";

    /**
     *
     */
    private LampTimeRemaining(Integer integer) {
        super(LAMP_TIME_REMAINING + integer);
    }

    /**
     *
     * @param lampNum
     */
    public LampTimeRemaining(int lampNum) throws Exception {
        this(new Integer(checkLampNum(lampNum)));
    }
    private static int checkLampNum(int lampNum) throws Exception {
        if(lampNum >= 1 && lampNum <= 2) {
            return lampNum;
        } else {
            throw new Exception("YOU DID A WRONG");
        }
    }
}
