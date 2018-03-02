package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class TestImage extends BarkoF22Command {
    private static final String TEST_IMAGE = "TEST";
    private int patternNum;

    /**
     *
     */
    public TestImage() {
    }

    /**
     *
     * @param patternNum
     */
    public TestImage(int patternNum) throws Exception {
        if(patternNum >= 0 && patternNum <= 7) {
            this.patternNum = patternNum;
        } else {
            throw new Exception("OUT OF BOUNDS!");
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        String[] ackArray = getResponse().split(" ");
        int value = Integer.parseInt(ackArray[2]);
        return ackArray[1] == "TEST" && value >= 0 && value <= 7;
    }

    /**
     *
     * @return
     */
    @Override
    public String getCmd() {
        return this.toString();
    }

    /**
     *
     * @param patternNum
     */
    public void setTestPattern(int patternNum) {
        if(patternNum >= 0 && patternNum <= 7) {
            this.patternNum = patternNum;
        }
    }
}
