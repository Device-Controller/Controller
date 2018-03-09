package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class TestImage extends BarkoF22Command {
    private static final String TEST_IMAGE = "TEST";
    private static final int MAX_VALUE = 7;
    private static final int MIN_VALUE = 0;

    /**
     *
     */
    private TestImage(Integer integer) {
        super(TEST_IMAGE, integer, MAX_VALUE, MIN_VALUE);
    }

    /**
     *
     * @param patternNum
     */
    public TestImage(int patternNum) throws Exception {
        this(new Integer(checkValue(patternNum)));
    }

    private static int checkValue(int value) throws Exception {
        if (value >= MIN_VALUE && value <= MAX_VALUE) {
            return value;
        } else {
            throw new Exception("THAT DIDN'T WORK");
        }
    }
}
