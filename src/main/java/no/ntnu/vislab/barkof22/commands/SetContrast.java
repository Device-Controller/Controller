package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 * @author ThomasSTodal
 */
public class SetContrast extends BarkoF22Command {
    private static final String SET_CONTRAST = "CNTR ";

    /**
     *
     */
    public SetContrast() {
    }

    /**
     *
     * @param value
     * @param isAbsoluteValue
     */
    public SetContrast(int value, boolean isAbsoluteValue) throws Exception {
        if (value >= -100 && value <= 100) {
            if(isAbsoluteValue) {
                setCmd(this.getPrefix() + SetContrast.SET_CONTRAST
                        + value + this.getSuffix());
            } else {
                setCmd(this.getPrefix() + SetContrast.SET_CONTRAST
                        + this.RELATIVE_MODIFIER + value + this.getSuffix());
            }
        } else {
            throw new Exception("WHOOOPSIE");
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        try {
            String[] ackArray = getResponse().split(" ");
            int value = Integer.parseInt(ackArray[2]);
            return ackArray[1].equals(SetContrast.SET_CONTRAST.trim())
                    && (value >= 0) && (value <= 100);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            return false;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.getCmd();
    }

    /**
     *
     * @param value
     * @param isAbsoluteValue
     */
    public void setContr(int value, boolean isAbsoluteValue) {
        if (value >= -100 && value <= 100) {
            if(isAbsoluteValue) {
                setCmd(this.getPrefix() + SetContrast.SET_CONTRAST
                        + value + this.getSuffix());
            } else {
                setCmd(this.getPrefix() + SetContrast.SET_CONTRAST
                        + this.RELATIVE_MODIFIER + value + this.getSuffix());
            }
        }
    }
}
