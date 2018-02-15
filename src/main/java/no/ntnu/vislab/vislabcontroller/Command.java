package no.ntnu.vislab.vislabcontroller;

/**
 *
 * @author ThomasSTodal
 */
public class Command
{
    private final  String prefix;
    private final  String suffix;
    
    private String command;
    
    public Command(String header, String terminator)
    {
        this.prefix = header;
        this.suffix = terminator;
    }

    protected String getPrefix()
    {
        return prefix;
    }

    protected String getSuffix()
    {
        return suffix;
    }

    protected String getCommand()
    {
        return command;
    }

    protected void setCommand(String command)
    {
        this.command = command;
    }
    
    @Override
    public String toString()
    {
        return command;
    }
}
