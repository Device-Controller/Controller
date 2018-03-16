package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;
import no.ntnu.vislab.barkof22.BarkoF22Exception;

/**
 * @author ThomasSTodal
 */
public class Contrast extends BarkoF22Command {
    private static final String CONTRAST = "CNTR ";
    private static final int MIN_VALUE = -100;
    private static final int MAX_VALUE = 100;

    public Contrast() {
        super(CONTRAST, MAX_VALUE, MIN_VALUE);
    }
    /**
     *
     */
    private Contrast(Integer integer, boolean isAbsoluteValue) {
        super((!isAbsoluteValue) ? CONTRAST + RELATIVE_MODIFIER : CONTRAST, integer);
    }

    /**
     * @param value
     * @param isAbsoluteValue
     */
    public Contrast(int value, boolean isAbsoluteValue) throws BarkoF22Exception {
        this(new Integer(checkValue(value)), isAbsoluteValue);
    }

    private static int checkValue(int value) throws BarkoF22Exception {
        if (value >= MIN_VALUE && value <= MAX_VALUE) {
            return value;
        } else {
            throw new BarkoF22Exception("Value is out of bounds!");
        }
    }
    public int getContrast(){
        return getValue();
    }
    public void setContrast(int contrast) throws BarkoF22Exception {
        setValue(checkValue(contrast));
    }
}
