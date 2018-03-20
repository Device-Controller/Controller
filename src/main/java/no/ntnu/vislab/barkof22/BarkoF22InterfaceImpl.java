package no.ntnu.vislab.barkof22;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import no.ntnu.vislab.vislabcontroller.Projector;

/**
 * Testing class that returns ints.
 * Returns the ints in the valid ranges that could be expected from a projector.
 */
public class BarkoF22InterfaceImpl implements BarkoF22Interface, Projector {
    private final String projectorName;
    private final String id;
    private final InetAddress hostAddress;
    private final int portNumber;

    public BarkoF22InterfaceImpl(String projectorName, String id, InetAddress hostAddress, int portNumber) throws UnknownHostException {
        this.projectorName = projectorName;
        this.id = id;
        this.hostAddress = hostAddress;
        this.portNumber = portNumber;
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

    @Override
    public String getProjectorName() {
        return projectorName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getHostAddress() {
        return hostAddress.toString();
    }

    @Override
    public int getPortNumber() {
        return portNumber;
    }
}
