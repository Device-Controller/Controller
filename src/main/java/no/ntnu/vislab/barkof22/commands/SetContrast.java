package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class SetContrast extends BarkoF22Command {
    private static final String CONTRAST = "CNTR ";
    private static final int MIN_VALUE = -100;
    private static final int MAX_VALUE = 100;

    /**
     *
     */
    private SetContrast(Integer integer, boolean isAbsoluteValue) {
        super((isAbsoluteValue) ? CONTRAST + RELATIVE_MODIFIER : CONTRAST, integer);
    }

    /**
     * @param value
     * @param isAbsoluteValue
     */
    public SetContrast(int value, boolean isAbsoluteValue) throws Exception {
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
