package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;
import no.ntnu.vislab.barkof22.BarkoF22Exception;

/**
 * @author ThomasSTodal
 */
public class Brightness extends BarkoF22Command {
    private static final String BRIGHTNESS = "BRIG";
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = -100;

    private static final String RELATIVE_MODIFIER = "R";

    /**
     *
     */
    public Brightness() {
        super(BRIGHTNESS, MAX_VALUE, MIN_VALUE);
    }
    /**
     *
     * @param integer
     * @param isAbsoluteValue
     */
    private Brightness(Integer integer, boolean isAbsoluteValue) {
        super((!isAbsoluteValue) ? BRIGHTNESS + " " + RELATIVE_MODIFIER : BRIGHTNESS + " ", integer);
    }

    /**
     * @param value
     * @param isAbsoluteValue
     */
    public Brightness(int value, boolean isAbsoluteValue) throws BarkoF22Exception {
        this(new Integer(checkValue(value)), isAbsoluteValue);
    }

    /**
     *
     * @param value
     * @return
     * @throws BarkoF22Exception
     */
    private static int checkValue(int value) throws BarkoF22Exception {
        if (value >= MIN_VALUE && value <= MAX_VALUE) {
            return value;
        } else {
            throw new BarkoF22Exception("Value is out of bounds!");
        }
    }

    /**
     *
     * @return
     */
    public int getBrightness() {
        return getValue();
    }

    /**
     *
     * @param brightness
     * @throws BarkoF22Exception
     */
    public void setBrightness(int brightness) throws BarkoF22Exception {
        setValue(checkValue(brightness));
    }
}
