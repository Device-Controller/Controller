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
public interface Projector extends Device{

    int mute();

    int unMute();

    int getPowerState();

    int getLampStatus();
}
