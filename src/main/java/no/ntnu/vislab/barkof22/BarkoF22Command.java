package no.ntnu.vislab.barkof22;

import no.ntnu.vislab.vislabcontroller.Command;

/**
 * @author ThomasSTodal
 */
public abstract class BarkoF22Command extends Command {

    protected static final String PREFIX = ":";
    protected static final String SUFFIX = "";
    protected static final String RELATIVE_MODIFIER = "R";
    protected static final String GET_CURRENT = "?";
    protected static final String GET_MAX = "?M";
    protected static final String GET_MIN = "?N";
    protected static final String GET_DEFAULT = "?D";
    protected static final String GET_DEFAULT_STEP = "?S";

    private final String FIELD;
    private final int MIN_VALUE;
    private final int MAX_VALUE;
    private int value;
    private final boolean GETTER;

    /**
     *
     * @param command
     * @param value
     * @param maxValue
     * @param minValue
     * @param getter
     */
    private BarkoF22Command(String command, int value, int maxValue, int minValue, boolean getter) {
        super(PREFIX, SUFFIX);
        this.FIELD = command;
        this.MAX_VALUE = maxValue;
        this.MIN_VALUE = minValue;
        this.value = (value > Integer.MIN_VALUE) ? value : this.value;
        this.GETTER = getter;
    }

    /**
     * Makes a setter command withe the given value, and checks that its inbetween the given max and min values.
     * @param command the command to set the given value
     * @param value the value to set
     * @param maxValue the commands max valuse
     * @param minValue the commands min value
     */
    public BarkoF22Command(String command, int value, int maxValue, int minValue) {
        this(command, value, maxValue, minValue, false);
    }

    /**
     * Makes a getter command that checks if the response is withing the given max and min values.
     * @param command the command to get the current value from
     * @param maxValue the commands max possible value
     * @param minValue the commands min possible value
     */
    public BarkoF22Command(String command, int maxValue, int minValue) {
        this(command, Integer.MIN_VALUE, maxValue, minValue, true);
    }

    /**
     * Makes a setter with the given value and checks if the response is
     * @param command the command to set the value to
     * @param value the value to set
     */
    public BarkoF22Command(String command, int value){
        this(command, value, value, value, false);
    }

    /**
     * Makes a getter for the given command
     * @param command the command to get the current value for.
     */
    public BarkoF22Command(String command){
        this(command, Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    /**
     *
     * @return
     */
    @Override
    public boolean checkAck() {
        try {
            String[] ackArray = getResponse().split(" ");
            value = Integer.parseInt(ackArray[2]);
            return FIELD.contains(ackArray[1]) && (value >= MIN_VALUE) && (value <= MAX_VALUE);
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
        return (GETTER) ? getPrefix() + FIELD + GET_CURRENT + getSuffix() : getPrefix() + FIELD + value + getSuffix();
    }

    /**
     *
     * @return
     */
    protected int getValue(){
        return value;
    }

    /**
     *
     * @param value
     */
    protected void setValue(int value) {
        this.value = value;
    }

    public String getCmd(){
        return FIELD;
    }
}
