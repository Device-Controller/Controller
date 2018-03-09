package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class LampStatus extends BarkoF22Command {
    private static final String LAMP_STATUS = "LST";
    private static final int MAX_VALUE = 5;
    private static final int MIN_VALUE = 0;

    /**
     *
     */
    private LampStatus(Integer integer) {
        super(LAMP_STATUS + integer, MAX_VALUE, MIN_VALUE);
    }

    /**
     * @param lampNum
     * @throws Exception
     */
    public LampStatus(int lampNum) throws Exception {
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
