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
import java.util.logging.Level;
import java.util.logging.Logger;

import no.ntnu.vislab.barkof22.commands.Brightness;
import no.ntnu.vislab.barkof22.commands.Contrast;
import no.ntnu.vislab.barkof22.commands.LampRuntime;
import no.ntnu.vislab.barkof22.commands.LampStatus;
import no.ntnu.vislab.barkof22.commands.LampTimeRemaining;
import no.ntnu.vislab.barkof22.commands.Mute;
import no.ntnu.vislab.barkof22.commands.Power;
import no.ntnu.vislab.barkof22.commands.PowerState;
import no.ntnu.vislab.barkof22.commands.TestImage;
import no.ntnu.vislab.barkof22.commands.ThermalStatus;
import no.ntnu.vislab.barkof22.commands.UnitTotalTime;
import no.ntnu.vislab.vislabcontroller.Command;
import no.ntnu.vislab.vislabcontroller.Projector;

/**
 * @author Kristoffer
 */
public class BarkoF22Projector extends Projector implements BarkoF22Interface {
    private CommunicationDriver cd;
    private int powerState;
    private int powerSetting;
    private int muteSetting;
    private int brightness;
    private int contrast;
    private int runtime;
    private int lamp1Runtime;
    private int lamp2Runtime;
    private int lamp1TimeRemaining;
    private int lamp2TimeRemaining;
    private int lamp1Status;
    private int lamp2Status;
    private int thermal;
    private int testImage;

    public BarkoF22Projector(String projectorName, String id, InetAddress hostAddress, int portNumber) throws UnknownHostException {
        super(projectorName, id, hostAddress, portNumber);
    }

    public BarkoF22Projector(InetAddress hostAddress, int portNumber) throws IOException {
        this("BarkoF22", "1", hostAddress, portNumber);
        cd = new CommunicationDriver(new Socket(hostAddress, portNumber));
        cd.setOnCommandReady(this::processCommand);
        cd.start();

    }

    /**
     * Queues up a command and waits for the response. This method blocks.
     *
     * @param command the command to queue.
     */
    private synchronized void sendAndWait(Command command) {
        cd.queueCommand(command);
        while (command.getResponse() == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Logger.getLogger(BarkoF22Projector.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    /**
     * Powers on the barko f22 projector.
     *
     * @return returns 1 if everything went like it should.
     */
    @Override
    public int powerOn() {
        try {
            Power power = new Power(Power.ON);
            sendAndWait(power);
            return power.getPowerSetting();
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Powers off the barko f22 projector.
     *
     * @return returns 0 if everything went like it should.
     */
    @Override
    public int powerOff() {
        try {
            Power power = new Power(Power.OFF);
            sendAndWait(power);
            return power.getPowerSetting();
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Mutes the image on the barko f22 projector.
     * @return returns 1 if everything went like it should.
     */
    @Override
    public int mute() {
        try {
            Mute mute = new Mute(Mute.ON);
            sendAndWait(mute);
            return mute.getMuteSetting();
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * UnMutes the image on the barko f22 projector.
     * @return returns 0 if everything went like it should.
     */
    @Override
    public int unMute() {
        try {
            Mute mute = new Mute(Mute.OFF);
            sendAndWait(mute);
            return mute.getMuteSetting();
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets the current brightness setting on the projector
     * @return the current brightness setting on the projector
     */
    @Override
    public int getBrightness() {
        Brightness brightness = new Brightness();
        sendAndWait(brightness);
        return brightness.getBrightness();
    }

    /**
     * Sets the brightness setting on the projector and returns it.
     * @param value the brightness value to set.
     * @return the brightness setting on the projector
     */
    @Override
    public int setBrightness(int value) {
        try {
            Brightness brightness = new Brightness(value, true);
            sendAndWait(brightness);
            return brightness.getBrightness();
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getContrast() {
        Contrast contrast = new Contrast();
        sendAndWait(contrast);
        return contrast.getContrast();
    }

    @Override
    public int setContrast(int value) {
        try {
            Contrast contrast = new Contrast(value, true);
            sendAndWait(contrast);
            return contrast.getContrast();
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getPowerState() {
        PowerState powerState = new PowerState();
        sendAndWait(powerState);
        return powerState.getPowerState();
    }

    @Override
    public int getLampRuntime(int lampNum) {
        try {
            LampRuntime lampRuntime = new LampRuntime(lampNum);
            sendAndWait(lampRuntime);
            return lampRuntime.getLampRuntime();
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getLampRemaining(int lampNum) {
        try {
            LampTimeRemaining lampTimeRemaining = new LampTimeRemaining(lampNum);
            sendAndWait(lampTimeRemaining);
            return lampTimeRemaining.getLampTimeRemaining();
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getTotalRuntime() {
        UnitTotalTime unitTotalTime = new UnitTotalTime();
        sendAndWait(unitTotalTime);
        return unitTotalTime.getTotalRuntime();
    }

    @Override
    public int getLampStatus(int lampNum) {
        try {
            LampStatus lampStatus = new LampStatus(lampNum);
            sendAndWait(lampStatus);
            return lampStatus.getLampStatus();
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getTemperature() {
        ThermalStatus thermalStatus = new ThermalStatus();
        sendAndWait(thermalStatus);
        return thermalStatus.getThermal();
    }

    @Override
    public int testImageOn(int testImageNum) {
        try {
            TestImage testImage = new TestImage(testImageNum);
            sendAndWait(testImage);
            return testImage.getTestImage();
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int testImageOff() {
        TestImage testImage = new TestImage();
        sendAndWait(testImage);
        return testImage.getTestImage();
    }

    public synchronized boolean processCommand(Command command) {
        if (!(command instanceof BarkoF22Command)) {
            return false;
        }
        try {
            BarkoF22Command f22Command = (BarkoF22Command) command;
            notifyAll();
            if (f22Command.getCmd().split(" ")[0].equals(new Contrast().getCmd())) {
                contrast = ((Contrast) f22Command).getContrast();
                return true;
            } else if (f22Command.getCmd().split(" ")[0].equals(new Brightness().getCmd())) {
                brightness = ((Brightness) f22Command).getBrightness();
                return true;
            } else if (f22Command.getCmd().equals(new LampRuntime(1).getCmd())) {
                lamp1Runtime = ((LampRuntime) f22Command).getLampRuntime();
                return true;
            } else if (f22Command.getCmd().equals(new LampRuntime(2).getCmd())) {
                lamp2Runtime = ((LampRuntime) f22Command).getLampRuntime();
                return true;
            } else if (f22Command.getCmd().equals(new LampStatus(1).getCmd())) {
                lamp1Status = ((LampStatus) f22Command).getLampStatus();
                return true;
            } else if (f22Command.getCmd().equals(new LampStatus(2).getCmd())) {
                lamp2Status = ((LampStatus) f22Command).getLampStatus();
                return true;
            } else if (f22Command.getCmd().equals(new LampTimeRemaining(1).getCmd())) {
                lamp1TimeRemaining = ((LampTimeRemaining) f22Command).getLampTimeRemaining();
                return true;
            } else if (f22Command.getCmd().equals(new LampTimeRemaining(2).getCmd())) {
                lamp2TimeRemaining = ((LampTimeRemaining) f22Command).getLampTimeRemaining();
                return true;
            } else if (f22Command.getCmd().equals(new Mute().getCmd())) {
                muteSetting = ((Mute) f22Command).getMuteSetting();
                return true;
            } else if (f22Command.getCmd().equals(new Power().getCmd())) {
                powerSetting = ((Power) f22Command).getPowerSetting();
                return true;
            } else if (f22Command.getCmd().equals(new PowerState().getCmd())) {
                powerState = ((PowerState) f22Command).getPowerState();
                return true;
            } else if (f22Command.getCmd().equals(new TestImage(1).getCmd())) {
                testImage = ((TestImage) f22Command).getTestImage();
                return true;
            } else if (f22Command.getCmd().equals(new ThermalStatus().getCmd())) {
                thermal = ((ThermalStatus) f22Command).getThermal();
                return true;
            } else if (f22Command.getCmd().equals(new UnitTotalTime().getCmd())) {
                runtime = ((UnitTotalTime) f22Command).getTotalRuntime();
                return true;
            }
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
