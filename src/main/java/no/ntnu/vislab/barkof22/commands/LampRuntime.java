package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class LampRuntime extends BarkoF22Command {
    private static final String LAMP_RUNTIME = "LTR";
    private int lampNum;

    /**
     *
     */
    public LampRuntime() {
    }

    /**
     *
     * @param lampNum
     * @throws Exception
     */
    public LampRuntime(int lampNum) throws Exception {
        if(lampNum >= 1 && lampNum <= 2) {
            this.lampNum = lampNum;
        } else {
            throw new Exception("YOU DID A WRONG");
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
            return ackArray[1].equals(LampRuntime.LAMP_RUNTIME + this.lampNum);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            return false;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.getPrefix() + LampRuntime.LAMP_RUNTIME + this.lampNum
                + this.GET_CURRENT + this.getSuffix();
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
