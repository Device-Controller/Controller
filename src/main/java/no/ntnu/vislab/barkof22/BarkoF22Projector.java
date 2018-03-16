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
import no.ntnu.vislab.barkof22.commands.Brightness;
import no.ntnu.vislab.vislabcontroller.Projector;

/**
 * @author Kristoffer
 */
public class BarkoF22Projector extends Projector implements BarkoF22Interface {
    CommunicationDriver cd;

    public BarkoF22Projector(String projectorName, String id, InetAddress hostAddress, int portNumber) throws UnknownHostException {
        super(projectorName, id, hostAddress, portNumber);
    }

    public BarkoF22Projector(InetAddress hostAddress, int portNumber) throws IOException {
        this("BarkoF22", "1", hostAddress, portNumber);
        cd = new CommunicationDriver(new Socket(hostAddress, portNumber));
        cd.start();

    }

    @Override
    public int powerOn() {
        return 0;
    }

    @Override
    public int powerOff() {
        return 0;
    }

    @Override
    public int mute() {
        return 0;
    }

    @Override
    public int unMute() {
        return 0;
    }

    @Override
    public int getBrightness() {
        return 0;
    }

    @Override
    public int setBrightness(int value) {
        return 0;
    }

    @Override
    public int getContrast() {
        return 0;
    }

    @Override
    public int setContrast(int value) {
        return 0;
    }

    @Override
    public int getPowerState() {
        return 0;
    }

    @Override
    public int getLampRuntime(int lampNum) {
        return 0;
    }

    @Override
    public int getLampRemaining(int lampNum) {
        return 0;
    }

    @Override
    public int getTotalRuntime() {
        return 0;
    }

    @Override
    public int getLampStatus(int lampNum) {
        try {
            cd.queueCommand(new LampStatus(1));
            cd.queueCommand(new Brightness(100, true));
            cd.queueCommand(new Brightness());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getTemperature() {
        return 0;
    }

    @Override
    public int testImageOn(int testImage) {
        return 0;
    }
    @Override
    public int testImageOff() {
        return 0;
    }
}
