/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22;

import java.net.InetAddress;
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
    private Thread t1;

    public BarkoF22Projector(String projectorName, String id, InetAddress hostAddress, int portNumber) throws UnknownHostException{
        super(projectorName, id, hostAddress, portNumber);
        driver = null;
        driver = new CommunicationRunnable(hostAddress, portNumber);
        startDriver();
    }

    public BarkoF22Projector(InetAddress hostAddress, int portNumber)throws UnknownHostException{
        this("Barko F22", "f22", hostAddress, portNumber);
    }
    @Override
    public String powerOn() {
        driver.sendCommand(BarkoF22Command.powerOn());
        return driver.getResponse();
    }

    @Override
    public String powerOff() {
        driver.sendCommand(BarkoF22Command.powerOff());
        return driver.getResponse();
    }

    @Override
    public String mute() {
        driver.sendCommand(BarkoF22Command.mute());
        return driver.getResponse();
    }

    @Override
    public String unMute() {
        driver.sendCommand(BarkoF22Command.unMute());
        return driver.getResponse();
    }

    @Override
    public String getBrightness() {
        driver.sendCommand(BarkoF22Command.getBrightness());
        return driver.getResponse();
    }

    @Override
    public String setBrightness(int value) {
        driver.sendCommand(BarkoF22Command.setBrightness(value));
        return driver.getResponse();
    }

    @Override
    public String getContrast() {
        driver.sendCommand(BarkoF22Command.getContrast());
        return driver.getResponse();
    }

    @Override
    public String setConstrast(int value) {
        driver.sendCommand(BarkoF22Command.setContrast(value));
        return driver.getResponse();
    }

    @Override
    public String getPowerState() {
        driver.sendCommand(BarkoF22Command.powerState());
        return driver.getResponse();
    }

    @Override
    public String getLampRuntime(int lampNum) {
        if (lampNum == 1) {
            driver.sendCommand(BarkoF22Command.lamp1Runtime());
        } else if (lampNum == 2) {
            driver.sendCommand(BarkoF22Command.lamp2Runtime());
        }
        return driver.getResponse();
    }

    @Override
    public String getLampRemaining(int lampNum) {
        if (lampNum == 1) {
            driver.sendCommand(BarkoF22Command.lamp1EstTimeRemaining());
        } else if (lampNum == 2) {
            driver.sendCommand(BarkoF22Command.lamp2EstTimeRemaining());
        }
        return driver.getResponse();
    }

    @Override
    public String getTotalRuntime() {
        driver.sendCommand(BarkoF22Command.totalUnitTime());
        return driver.getResponse();
    }

    @Override
    public String getLampStatus(int lampNum) {
        if (lampNum == 1) {
            driver.sendCommand(BarkoF22Command.lamp1Status());
        } else if (lampNum == 2) {
            driver.sendCommand(BarkoF22Command.lamp2Status());
        }
        return driver.getResponse();
    }

    @Override
    public String getTemperature() {
        driver.sendCommand(BarkoF22Command.thermalStatus());
        return driver.getResponse();
    }

    private void startDriver() {
        t1 = new Thread(driver);
        t1.start();
    }

}
