/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.vislabcontroller.providers;

/**
 * Interface for projectors in general.
 * @author Kristoffer
 */
public interface Projector{

    int powerOn();

    int powerOff();

    String getMake();

    String getModel();

    String getDeviceName();

    String getHostAddress();

    int getPortNumber();

    boolean setIpAddress(String ip);

    void setPort(int port);

    int mute();

    int unMute();

    int getPowerState();

    int getLampStatus();
}
