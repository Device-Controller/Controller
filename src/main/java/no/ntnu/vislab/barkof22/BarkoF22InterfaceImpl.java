package no.ntnu.vislab.barkof22;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import no.ntnu.vislab.vislabcontroller.Projector;

public class BarkoF22InterfaceImpl extends Projector implements BarkoF22Interface {
    public BarkoF22InterfaceImpl(String projectorName, String id, InetAddress hostAddress, int portNumber) throws UnknownHostException {
        super(projectorName, id, hostAddress, portNumber);
    }

    @Override
    public int powerOn() {
        return 1;
    }

    @Override
    public int powerOff() {
        return 0;
    }

    @Override
    public int mute() {
        return 1;
    }

    @Override
    public int unMute() {
        return 0;
    }

    @Override
    public int getBrightness() {
        return new Random().nextInt(201) - 100;
    }

    @Override
    public int setBrightness(int value) {
        return value;
    }

    @Override
    public int getContrast() {
        return new Random().nextInt(201) - 100;
    }

    @Override
    public int setContrast(int value) {
        return value;
    }

    @Override
    public int getPowerState() {
        return new Random().nextInt(7);
    }

    @Override
    public int getLampRuntime(int lampNum) {
        return new Random().nextInt(999999);
    }

    @Override
    public int getLampRemaining(int lampNum) {
        return new Random().nextInt(999999);
    }

    @Override
    public int getTotalRuntime() {
        return new Random().nextInt(999999);
    }

    @Override
    public int getLampStatus(int lampNum) {
        return new Random().nextInt(6);
    }

    @Override
    public int getTemperature() {
        return new Random().nextInt(150);
    }

    @Override
    public int testImageOn(int testImage) {
        return testImage;
    }

    @Override
    public int testImageOff() {
        return 0;
    }
}
