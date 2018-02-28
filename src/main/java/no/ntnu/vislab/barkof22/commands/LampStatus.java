package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class LampStatus extends BarkoF22Command {
    private static final String LAMP_STATUS = "LST";
    private final int lampNo;

    /**
     *
     * @param lampNum
     * @throws Exception
     */
    public LampStatus(int lampNum) throws Exception {
        if (lampNum >= 1 && lampNum <= 2) {
            this.setCmd(getPrefix() + LampStatus.LAMP_STATUS
                    + lampNum + this.GET_CURRENT + this.getSuffix());
            this.lampNo = lampNum;
        } else {
            throw new Exception("YOU MESSED UP");
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
        return ackArray[1] == ("LST" + lampNo) && value >= 0 && value <= 5;
    }
}
