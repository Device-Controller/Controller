/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import no.ntnu.vislab.barkof22.commands.LampStatus;
import no.ntnu.vislab.barkof22.commands.PowerState;
import no.ntnu.vislab.vislabcontroller.Command;
import no.ntnu.vislab.vislabcontroller.Projector;
import no.ntnu.vislab.barkof22.commands.PowerOn;

/**
 *
 * @author Kristoffer
 */
public class BarkoF22Projector extends Projector {
    CommunicationDriver cd;
    {
        try {
            Socket socket = new Socket("158.38.101.110", 1025);
            cd = new CommunicationDriver(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public BarkoF22Projector(String projectorName, String id, InetAddress hostAddress, int portNumber) throws UnknownHostException {
        super(projectorName, id, hostAddress, portNumber);
    }

    public BarkoF22Projector(InetAddress hostAddress, int portNumber, Command cmd) throws UnknownHostException {
        this("BarkoF22", "1",hostAddress,portNumber);
        cd.start();
        cd.queueCommand(cmd);
        Timer t = new Timer();
        while (!t.hasTimerPassed(1000)) {

        }
        t.reset();
        cd.stopThread();

    }
    @Override
    public String powerOn() {
        return null;
    }

    @Override
    public String powerOff() {
        return null;
    }

    @Override
    public String mute() {
        return null;
    }

    @Override
    public String unMute() {
        return null;
    }

    @Override
    public String getBrightness() {
        return null;
    }

    @Override
    public String setBrightness(int value) {
        return null;
    }

    @Override
    public String getContrast() {
        return null;
    }

    @Override
    public String setConstrast(int value) {
        return null;
    }

    @Override
    public String getPowerState() {
        return null;
    }

    @Override
    public String getLampRuntime(int lampNum) {
        return null;
    }

    @Override
    public String getLampRemaining(int lampNum) {
        return null;
    }

    @Override
    public String getTotalRuntime() {
        return null;
    }

    @Override
    public String getLampStatus(int lampNum) {
        return null;
    }

    @Override
    public String getTemperature() {
        return null;
    }

    @Override
    public String testImageOn() {
        return null;
    }

    @Override
    public String testImageOff() {
        return null;
    }
}
