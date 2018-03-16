package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;
import no.ntnu.vislab.barkof22.BarkoF22Exception;

/**
 * @author ThomasSTodal
 */
public class TestImage extends BarkoF22Command {
    private static final String TEST_IMAGE = "TEST";
    private static final int MAX_VALUE = 7;
    private static final int MIN_VALUE = 0;

    public TestImage(){
        super(TEST_IMAGE, 0);
    }
    /**
     *
     * @param integer
     */
    private TestImage(Integer integer) {
        super(TEST_IMAGE, integer, MAX_VALUE, MIN_VALUE);
    }

    /**
     *
     * @param patternNum
     * @throws BarkoF22Exception
     */
    public TestImage(int patternNum) throws BarkoF22Exception {
        this(new Integer(checkValue(patternNum)));
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
    public int getTestImage() {
        return getValue();
    }

    /**
     *
     * @param num
     * @throws BarkoF22Exception
     */
    public void setTestImage(int num) throws BarkoF22Exception {
        setValue(checkValue(num));
    }
}
