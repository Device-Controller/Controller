package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class LampStatus extends BarkoF22Command {
    private static final String LAMP_STATUS = "LST";
    private int lampNum = 1;

    /**
     *
     */
    public LampStatus() {
    }

    /**
     * @param lampNum
     * @throws Exception
     */
    public LampStatus(int lampNum) throws Exception {
        if (lampNum >= 1 && lampNum <= 2) {
            this.lampNum = lampNum;
        } else {
            throw new Exception("YOU MESSED UP");
        }
    }

    /**
     * @return
     */
    @Override
    public boolean checkAck() {
        try {
            String[] ackArray = getResponse().split(" ");
            int value = Integer.parseInt(ackArray[2]);
            return (ackArray[1].equals("LST" + this.lampNum)) && (value >= 0 && value <= 5);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            return false;
        }
    }

    /**
     * @return
     */
    @Override
    public String getCmd() {
        return this.getPrefix() + LampStatus.LAMP_STATUS + this.lampNum
                + this.GET_CURRENT + this.getSuffix();
    }

    @Override
    public String toString() {
        return getCmd();
    }

    /**
     * @param lampNum
     */
    public void setLampNum(int lampNum) {
        if (lampNum >= 1 && lampNum <= 2) {
            this.lampNum = lampNum;
        }
    }
}
