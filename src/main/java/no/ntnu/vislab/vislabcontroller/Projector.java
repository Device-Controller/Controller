/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.vislabcontroller;

/**
 *
 * @author Kristoffer
 */
public abstract class Projector {
    private final String projectorName;
    private final String id;
    private final String hostAddress;
    private final int portNumber;

    public Projector(String projectorName, String id, String hostAddress, int portNumber) {
        this.projectorName = projectorName;
        this.id = id;
        this.hostAddress = hostAddress;
        this.portNumber = portNumber;
    }
    public abstract String powerOn();
    public abstract String powerOff();
    public abstract String mute();
    public abstract String unMute();
    public abstract String getBrightness();
    public abstract String setBrightness(int value);
    public abstract String getContrast();
    public abstract String setConstrast(int value);
    public abstract String getPowerState();
    public abstract String getLampRuntime(int lampNum);
    public abstract String getLampRemaining(int lampNum);
    public abstract String getTotalRuntime();
    public abstract String getLampStatus(int lampNum);
    public abstract String getTemperature();

    public String getProjectorName() {
        return projectorName;
    }

    public String getId() {
        return id;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public int getPortNumber() {
        return portNumber;
    }
    
    
}
