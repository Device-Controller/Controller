package no.ntnu.vislab.barkof22;

/**
 * @author ThomasSTodal
 */
public class BarkoF22Exception extends Exception {
    private final Class classVariable;
    private final int value;

    /**
     *
     * @param classVariable The class throwing this exception
     * @param value the value causing this exception
     */
    public BarkoF22Exception(Class classVariable, int value) {
        this.classVariable = classVariable;
        this.value = value;
    }

    /**
     *
     * @return the offending class name
     */
    public String getClassName() {
        return classVariable.getName();
    }

    /**
     *
     * @return the erroneous value
     */
    public int getValue() {
        return value;
    }
}
