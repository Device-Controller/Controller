/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.vislabcontroller;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Kristoffer
 */
public abstract class Projector {
    private final String projectorName;
    private final String id;
    private final InetAddress hostAddress;
    private final int portNumber;

    public Projector(String projectorName, String id, InetAddress hostAddress, int portNumber) throws UnknownHostException{
        this.projectorName = projectorName;
        this.id = id;
        this.hostAddress = hostAddress;
        this.portNumber = portNumber;
    }
    public abstract int powerOn();
    public abstract int powerOff();
    public abstract int mute();
    public abstract int unMute();
    public abstract int getPowerState();
    public abstract int getLampStatus(int lampNum);

    public String getProjectorName() {
        return projectorName;
    }

    public String getId() {
        return id;
    }

    public InetAddress getHostAddress() {
        return hostAddress;
    }

    public int getPortNumber() {
        return portNumber;
    }
    
    
}
