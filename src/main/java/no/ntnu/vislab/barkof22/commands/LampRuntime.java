package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;
import no.ntnu.vislab.barkof22.BarkoF22Exception;

/**
 * @author ThomasSTodal
 */
public class LampRuntime extends BarkoF22Command {
    private static final String LAMP_RUNTIME = "LTR";
    private final int lampNum;

    /**
     *
     * @param integer
     */
    private LampRuntime(Integer integer) {
        super(LAMP_RUNTIME + integer);
        this.lampNum = integer;
    }

    /**
     *
     * @param lampNum
     * @throws BarkoF22Exception
     */
    public LampRuntime(int lampNum) throws BarkoF22Exception {
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
    public int getLampNum() {
        return lampNum;
    }

    /**
     *
     * @return
     */
    public int getLampRuntime() {
        return getValue();
    }
}
