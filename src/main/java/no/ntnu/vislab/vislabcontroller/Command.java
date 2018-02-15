/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.vislabcontroller;

/**
 *
 * @author ThomasSTodal
 */
public class Command
{
    protected final String header;
    protected final String terminator;
    
    protected String command;
    
    public Command(String header, String terminator)
    {
        this.header = header;
        this.terminator = terminator;
    }
    
    @Override
    public String toString()
    {
        return command;
    }
}
