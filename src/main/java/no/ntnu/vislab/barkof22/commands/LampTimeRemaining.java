package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;
import no.ntnu.vislab.barkof22.BarkoF22Exception;

/**
 * @author ThomasSTodal
 */
public class LampTimeRemaining extends BarkoF22Command {
    private static final String LAMP_TIME_REMAINING = "LRM";
    private final int lampNum;

    /**
     *
     * @param integer
     */
    private LampTimeRemaining(Integer integer) {
        super(LAMP_TIME_REMAINING + integer);
        this.lampNum = integer;
    }

    /**
     *
     * @param lampNum
     */
    public LampTimeRemaining(int lampNum) throws BarkoF22Exception {
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
    public int getLampTimeRemaining(){
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
