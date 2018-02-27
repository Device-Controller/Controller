package no.ntnu.vislab.vislabcontroller;

import java.util.Objects;

/**
 *
 * @author ThomasSTodal
 */
public class Command {

    private  final String prefix;
    private  final String suffix;

    private  String cmd;
    
    private String response;

    public Command(String header, String terminator) {
        this.prefix = header;
        this.suffix = terminator;
    }

    protected String getPrefix() {
        return prefix;
    }

    protected String getSuffix() {
        return suffix;
    }

    public String getCmd() {
        return cmd;
    }

    protected void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return cmd; //TODO should not be the same as getCmd()
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.cmd);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Command other = (Command) obj;
        if (!Objects.equals(this.cmd, other.cmd)) {
            return false;
        }
        return true;
    }

}
