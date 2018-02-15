/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkoF22;

import no.ntnu.vislab.vislabcontroller.Command;

/**
 *
 * @author ThomasSTodal
 */
public class BarkoF22Command extends Command
{
    
    public BarkoF22Command(String command)
    {
        super(":", "CR");
        this.command = header + command + terminator;
    }
}
