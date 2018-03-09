package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class LampTimeRemaining extends BarkoF22Command {
    private static final String LAMP_TIME_REMAINING = "LRM";
    private int lampNum;

    /**
     *
     */
    public LampTimeRemaining() {
    }

    /**
     *
     * @param lampNum
     */
    public LampTimeRemaining(int lampNum) throws Exception {
        if(lampNum >= 1 && lampNum <= 2) {
            this.lampNum = lampNum;
        } else {
            throw new Exception("A MISTAKE HAS BEEN MADE");
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        try {
            String[] ackArray = getResponse().split(" ");
            return ackArray[1].equals(LampTimeRemaining.LAMP_TIME_REMAINING + this.lampNum);
        } catch(ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            return false;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.getPrefix() + LampTimeRemaining.LAMP_TIME_REMAINING
                + this.lampNum + this.GET_CURRENT + this.getSuffix();
    }

    /**
     *
     * @param lampNum
     */
    public void setLampNum(int lampNum) {
        if (lampNum >= 1 && lampNum <= 2) {
            this.lampNum = lampNum;
        }
    }
}
