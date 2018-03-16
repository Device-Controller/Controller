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

    public boolean proccesCommand(Command command) {
        if (!(command instanceof BarkoF22Command)) {
            return false;
        }
        try {
            BarkoF22Command f22Command = (BarkoF22Command) command;
            if (f22Command.getCmd().equals(new Contrast().getCmd())) {
                contrast = ((Contrast) f22Command).getContrast();
                return true;
            } else if (f22Command.getCmd().equals(new Brightness().getCmd())) {
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

    public int getPowerSetting() {
        return powerSetting;
    }

    public int getMuteSetting() {
        return muteSetting;
    }

    public int getRuntime() {
        return runtime;
    }

    public int getLamp1Runtime() {
        return lamp1Runtime;
    }

    public int getLamp2Runtime() {
        return lamp2Runtime;
    }

    public int getLamp1TimeRemaining() {
        return lamp1TimeRemaining;
    }

    public int getLamp2TimeRemaining() {
        return lamp2TimeRemaining;
    }

    public int getLamp1Status() {
        return lamp1Status;
    }

    public int getLamp2Status() {
        return lamp2Status;
    }

    public int getThermal() {
        return thermal;
    }

    public int getTestImage() {
        return testImage;
    }
}
