package no.ntnu.vislab.vislabcontroller;

import java.util.Objects;

/**
 *
 * @author ThomasSTodal
 */
public abstract class Command {

    private  final String prefix;
    private  final String suffix;

    private  String cmd;
    
    private String response;

    /**
     *
     * @param header
     * @param terminator
     */
    public Command(String header, String terminator) {
        this.prefix = header;
        this.suffix = terminator;
    }

    /**
     *
     * @return
     */
    protected String getPrefix() {
        return prefix;
    }

    /**
     *
     * @return
     */
    protected String getSuffix() {
        return suffix;
    }

    /**
     *
     * @return
     */
    public abstract String getCmd();

    /**
     *
     * @param cmd
     */
    protected void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     *
     * @param response
     */
    protected void setResponse(String response) {
        this.response = response;
    }

    /**
     *
     * @return
     */
    public String getResponse() {
        return response;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return cmd;
    }

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
