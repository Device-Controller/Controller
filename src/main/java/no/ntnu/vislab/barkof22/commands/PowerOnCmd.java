/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Cmd;

/**
 *
 * @author ThomasSTodal
 */
public class PowerOnCmd extends BarkoF22Cmd {
    
    private static final String POWER = "POWR";
    
    public PowerOnCmd(){
        this.cmd = this.prefix + this.POWER + this.suffix;
    }
}
