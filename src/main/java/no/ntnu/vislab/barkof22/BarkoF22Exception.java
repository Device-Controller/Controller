package no.ntnu.vislab.barkof22;

/**
 * @author ThomasSTodal
 */
public class BarkoF22Exception extends Exception {
    private final String message;

    /**
     * @param message
     */
    public BarkoF22Exception(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }
}
