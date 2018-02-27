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
public class PowerOnCmd extends BarkoF22Cmd {

    private static final String POWER_ON = "POWR1";

    public PowerOnCmd() {
        setCmd(getPrefix() + PowerOnCmd.POWER_ON + getSuffix());
    }

    public boolean checkAck() {
        String[] ackArray = getResponse().split(" ");
        int value = Integer.parseInt(ackArray[2]);
        return ackArray[1] == "POWR" && value == 1;
    }
    
    public void setAck(String ack) {
        setResponse(ack);
    }
}
