package no.ntnu.vislab.vislabcontroller;

import java.util.Objects;

/**
 * This is a superclass for all commands
 *
 * @author ThomasSTodal
 */
public abstract class Command {

    private  final String prefix;
    private  final String suffix;

    private  String cmd;
    
    private String response;

    /**
     * The prefix and suffix needs to be set by the subclass.
     *
     * @param prefix represents the beginning of each command
     * @param suffix represents the end of each command
     */
    public Command(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    /**
     *
     * @return The prefix that was set upon instancing this class.
     */
    protected String getPrefix() {
        return prefix;
    }

    /**
     *
     * @return  The suffix that was set upon instancing this class.
     */
    protected String getSuffix() {
        return suffix;
    }

    /**
     *
     *
     * @return
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * Stores the command String in the cmd field.
     *
     * @param cmd This should be the entire command, including prefix and suffix
     */
    protected void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * Upon receiving an response it should be stored in the response field
     * to be manipulated later if needed.
     *
     * @param response this should be the response from the device
     */
    protected void setResponse(String response) {
        this.response = response;
    }

    /**
     *
     * @return The previously stored response, null or empty String if no response is found.
     */
    public String getResponse() {
        return response;
    }

    /**
     *
     * @return
     */
    @Override
    public abstract String toString();

    /**
     *
     * @return
     */
    public abstract boolean checkAck();

    /**
     *
     * @param ack
     */
    public void setAck(String ack) { setResponse(ack); }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.cmd);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
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
