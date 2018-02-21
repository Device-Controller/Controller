package no.ntnu.vislab.vislabcontroller;

import java.util.Objects;

/**
 *
 * @author ThomasSTodal
 */
public class Command {

    private final String prefix;
    private final String suffix;

    private String command;

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

    protected String getCommand() {
        return command;
    }

    protected void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.command);
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
        if (!Objects.equals(this.command, other.command)) {
            return false;
        }
        return true;
    }

}
