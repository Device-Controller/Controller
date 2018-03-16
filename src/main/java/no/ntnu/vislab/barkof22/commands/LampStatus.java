package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;
import no.ntnu.vislab.barkof22.BarkoF22Exception;

/**
 * @author ThomasSTodal
 */
public class LampStatus extends BarkoF22Command {
    private static final String LAMP_STATUS = "LST";
    private static final int MAX_VALUE = 5;
    private static final int MIN_VALUE = 0;
    private final int lampNum;

    /**
     *
     * @param integer
     */
    private LampStatus(Integer integer) {
        super(LAMP_STATUS + integer, MAX_VALUE, MIN_VALUE);
        lampNum = integer;
    }

    /**
     * @param lampNum
     * @throws BarkoF22Exception
     */
    public LampStatus(int lampNum) throws BarkoF22Exception {
        this(new Integer(checkLampNum(lampNum)));
    }

    /**
     *
     * @param lampNum
     * @return
     * @throws BarkoF22Exception
     */
    private static int checkLampNum(int lampNum) throws BarkoF22Exception {
        if(lampNum >= 1 && lampNum <= 2) {
            return lampNum;
        } else {
            throw new BarkoF22Exception("Value is out of bounds!");
        }
    }

    /**
     *
     * @return
     */
    public int getLampStatus(){
        return getValue();
    }

    /**
     *
     * @return
     */
    public int getLampNum() {
        return lampNum;
    }
}
