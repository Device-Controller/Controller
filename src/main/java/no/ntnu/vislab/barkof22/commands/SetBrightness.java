package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class SetBrightness extends BarkoF22Command {
    private static final String BRIGHTNESS = "BRIG ";
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = -100;

    /**
     *
     */
    private SetBrightness(Integer integer, boolean isAbsoluteValue) {
        super((isAbsoluteValue) ? BRIGHTNESS + RELATIVE_MODIFIER : BRIGHTNESS, integer);
    }

    /**
     *
     * @param value
     * @param isAbsoluteValue
     */
    public SetBrightness(int value, boolean isAbsoluteValue) throws Exception {
        this(new Integer(checkValue(value)), isAbsoluteValue);
    }
    private static int checkValue(int value) throws Exception {
        if (value >= MIN_VALUE && value <= MAX_VALUE) {
            return value;
        } else {
            throw new Exception("THAT DIDN'T WORK");
        }
    }
}
