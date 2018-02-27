/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Cmd;

/**
 *
 * @author ThomasSTodal
 */
public class LampStatusCmd extends BarkoF22Cmd {
    private static final String LAMP_STATUS = "LST";
    private final int lampNo;
    
    public LampStatusCmd(int lampNum) throws Exception {
        if (lampNum >= 1 && lampNum <= 2) {
            this.setCmd(getPrefix() + LAMP_STATUS + lampNum + getSuffix());
            this.lampNo = lampNum;
        } else {
            throw new Exception("YOU MESSED UP");
        }
    }

    public boolean checkAck() {
        String[] ackArray = getResponse().split(" ");
        int value = Integer.parseInt(ackArray[2]);
        if(ackArray[1] == ("LST" + lampNo) && value >= 0 && value <= 5) {
            return true;
        } else {
            return false;
        }
    }
    
    public void setAck(String ack) {
        setResponse(ack);
    }
}
