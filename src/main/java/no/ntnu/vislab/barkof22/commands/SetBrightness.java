package no.ntnu.vislab.barkof22.commands;

import no.ntnu.vislab.barkof22.BarkoF22Command;

/**
 *
 * @author ThomasSTodal
 */
public class SetBrightness extends BarkoF22Command {
    private static final String SET_BRIGHTNESS = "BRIG ";

    /**
     *
     */
    public SetBrightness() {
    }

    /**
     *
     * @param value
     * @param isAbsoluteValue
     */
    public SetBrightness(int value, boolean isAbsoluteValue) throws Exception {
        if(value >= -100 && value <= 100) {
            if (isAbsoluteValue) {
                setCmd(this.getPrefix() + SetBrightness.SET_BRIGHTNESS
                        + value + this.getSuffix());
            } else {
                setCmd(this.getPrefix() + SetBrightness.SET_BRIGHTNESS
                        + this.RELATIVE_VALUE + value + this.getSuffix());
            }
        } else {
            throw new Exception("THAT DIDN'T WORK");
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        String[] ackArray = getResponse().split(" ");
        int value = Integer.parseInt(ackArray[2]);
        return ackArray[1] == "BRIG" && value >= 0 && value <= 100;
    }

    /**
     *
     * @return
     */
    @Override
    public String getCmd() {
        return this.toString();
    }

    /**
     *
     * @param value
     * @param isAbsoluteValue
     */
    public void setBrig(int value, boolean isAbsoluteValue) {
        if(value >= -100 && value <= 100) {
            if (isAbsoluteValue) {
                setCmd(this.getPrefix() + SetBrightness.SET_BRIGHTNESS
                        + value + this.getSuffix());
            } else {
                setCmd(this.getPrefix() + SetBrightness.SET_BRIGHTNESS
                        + this.RELATIVE_VALUE + value + this.getSuffix());
            }
        }
    }
}
