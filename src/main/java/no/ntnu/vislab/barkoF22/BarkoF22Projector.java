/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkoF22;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.vislab.vislabcontroller.Projector;

/**
 *
 * @author Kristoffer
 */
public class BarkoF22Projector extends Projector {

    private CommunicationRunnable driver;

    public BarkoF22Projector(String projectorName, String id, String hostAddress, int portNumber) {
        super(projectorName, id, hostAddress, portNumber);
        driver = null;
        try {
            driver = new CommunicationRunnable(hostAddress, portNumber);
        } catch (UnknownHostException ex) {
            Logger.getLogger(BarkoF22Projector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String powerOn() {
        driver.sendCommand(BarkoF22Command.powerOn());
        return null;
    }

    @Override
    public String powerOff() {
        driver.sendCommand(BarkoF22Command.powerOff());
        return null;
    }

    @Override
    public String mute() {
        driver.sendCommand(BarkoF22Command.mute());
        return null;
    }

    @Override
    public String unMute() {
        driver.sendCommand(BarkoF22Command.unMute());
        return null;
    }

    @Override
    public String getBrightness() {
        driver.sendCommand(BarkoF22Command.getBrightness());
        return null;
    }

    @Override
    public String setBrightness(int value) {
        driver.sendCommand(BarkoF22Command.setBrightness(value));
        return null;
    }

    @Override
    public String getContrast() {
        driver.sendCommand(BarkoF22Command.getContrast());
        return null;
    }

    @Override
    public String setConstrast(int value) {
        driver.sendCommand(BarkoF22Command.setContrast(value));
        return null;
    }

    @Override
    public String getPowerState() {
        driver.sendCommand(BarkoF22Command.powerState());
        return null;
    }

    @Override
    public String getLampRuntime(int lampNum) {
        if (lampNum == 1) {
            driver.sendCommand(BarkoF22Command.lamp1Runtime());
        } else if (lampNum == 2) {
            driver.sendCommand(BarkoF22Command.lamp2Runtime());
        }
        return null;
    }

    @Override
    public String getLampRemaining(int lampNum) {
        if (lampNum == 1) {
            driver.sendCommand(BarkoF22Command.lamp1EstTimeRemaining());
        } else if (lampNum == 2) {
            driver.sendCommand(BarkoF22Command.lamp2EstTimeRemaining());
        }
        return null;
    }

    @Override
    public String getTotalRuntime() {
        driver.sendCommand(BarkoF22Command.totalUnitTime());
        return null;
    }

    @Override
    public String getLampStatus(int lampNum) {
        if (lampNum == 1) {
            driver.sendCommand(BarkoF22Command.lamp1Status());
        } else if (lampNum == 2) {
            driver.sendCommand(BarkoF22Command.lamp2Status());
        }
        return null;
    }

    @Override
    public String getTemperature() {
        driver.sendCommand(BarkoF22Command.thermalStatus());
        return null;
    }

}
