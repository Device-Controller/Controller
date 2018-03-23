package no.ntnu.vislab.simulator;

import java.util.Random;

public class Projector {
    private int lamp1Status = 0;
    private int lamp2Status = 1;
    private int lamp1Runtime = 621;
    private int lamp2Runtime = 578;
    private int lamp1TimeRemaining = 888;
    private int lamp2TimeRemaining = 1892;
    private int totalRuntime = 3452;
    private int brightness = 84;
    private int contrast = 50;
    private int mute = 0;
    private int power = 0;
    private int powerState = 0;
    private int testImage = 0;
    private int thermal = 22;


    private static final String ADDRESS = "001";
    private static final String PREFIX = "%";
    private static final String SEPARATOR = " ";
    private static final String POWER = "POWR";
    private static final String POWER_STATE = "POST";
    private static final String MUTE = "PMUT";
    private static final String BRIGHTNESS = "BRIG";
    private static final String CONTRAST = "CNTR";
    private static final String LAMP_RUNTIME = "LTR";
    private static final String LAMP_EST_TIME_REMAINING = "LRM";
    private static final String LAMP_STATUS = "LST";
    private static final String UNIT_TOT_TIME = "UTOT";
    private static final String THERMAL = "THRM";
    private static final String TEST_IMAGE = "TEST";

    public String getLampStatusString(int num) {
        return PREFIX + ADDRESS + SEPARATOR + LAMP_STATUS + num + SEPARATOR + handleNumber(getLampStatus(num));
    }

    public String getLampRuntimeString(int num) {
        return PREFIX + ADDRESS + SEPARATOR + LAMP_RUNTIME + num + SEPARATOR + handleNumber(getLampRuntime(num));
    }

    public String getLampTimeRemainingString(int num) {
        return PREFIX + ADDRESS + SEPARATOR + LAMP_EST_TIME_REMAINING + num + SEPARATOR + handleNumber(getLampTimeRemaining(num));
    }

    public String getTotalRunimeString() {
        return PREFIX + ADDRESS + SEPARATOR + UNIT_TOT_TIME + SEPARATOR + handleNumber(getTotalRuntime());
    }

    public String getPowerString() {
        return PREFIX + ADDRESS + SEPARATOR + POWER + SEPARATOR + handleNumber(getPower());
    }

    public String getMuteString() {
        return PREFIX + ADDRESS + SEPARATOR + MUTE + SEPARATOR + handleNumber(getMute());
    }

    public String getPowerStateString() {
        return PREFIX + ADDRESS + SEPARATOR + POWER_STATE + SEPARATOR + handleNumber(getPowerState());
    }

    public String getBrightnessString() {
        return PREFIX + ADDRESS + SEPARATOR + BRIGHTNESS + SEPARATOR + handleNumber(getBrightness());
    }

    public String getContrastString() {
        return PREFIX + ADDRESS + SEPARATOR + CONTRAST + SEPARATOR + handleNumber(getContrast());
    }

    public String getThermalString() {
        return PREFIX + ADDRESS + SEPARATOR + THERMAL + SEPARATOR + handleNumber(getThermal());
    }

    public String getTestImageString() {
        return PREFIX + ADDRESS + SEPARATOR + TEST_IMAGE + SEPARATOR + handleNumber(getTestImage());
    }


    public String handleNumber(int number) {
        String num = "" + number;
        while (num.length() < 6) {
            num = "0" + num;
        }
        return num;
    }

    public int getLampStatus(int num) {
        if (num == 1) {
            return getLamp1Status();
        }
        return getLamp2Status();
    }

    public int getLamp1Status() {
        return lamp1Status;
    }

    public void setLamp1Status(int lamp1Status) {
        this.lamp1Status = lamp1Status;
    }

    public int getLamp2Status() {
        return lamp2Status;
    }

    public void setLamp2Status(int lamp2Status) {
        this.lamp2Status = lamp2Status;
    }

    public int getLampRuntime(int num) {
        if (num == 1) {
            return getLamp1Runtime();
        }
        return getLamp2Runtime();
    }

    public int getLamp1Runtime() {
        return lamp1Runtime;
    }

    public void setLamp1Runtime(int lamp1Runtime) {
        this.lamp1Runtime = lamp1Runtime;
    }

    public int getLamp2Runtime() {
        return lamp2Runtime;
    }

    public void setLamp2Runtime(int lamp2Runtime) {
        this.lamp2Runtime = lamp2Runtime;
    }

    public int getLampTimeRemaining(int num) {
        if (num == 1) {
            return getLamp1TimeRemaining();
        }
        return getLamp2TimeRemaining();
    }

    public int getLamp1TimeRemaining() {
        return lamp1TimeRemaining;
    }

    public void setLamp1TimeRemaining(int lamp1TimeRemaining) {
        this.lamp1TimeRemaining = lamp1TimeRemaining;
    }

    public int getLamp2TimeRemaining() {
        return lamp2TimeRemaining;
    }

    public void setLamp2TimeRemaining(int lamp2TimeRemaining) {
        this.lamp2TimeRemaining = lamp2TimeRemaining;
    }

    public int getTotalRuntime() {
        return totalRuntime;
    }

    public void setTotalRuntime(int totalRuntime) {
        this.totalRuntime = totalRuntime;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getContrast() {
        return contrast;
    }

    public void setContrast(int contrast) {
        this.contrast = contrast;
    }

    public int getMute() {
        return mute;
    }

    public void setMute(int mute) {
        this.mute = mute;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPowerState() {
        return powerState;
    }

    public void setPowerState(int powerState) {
        this.powerState = powerState;
    }

    public int getTestImage() {
        return testImage;
    }

    public void setTestImage(int testImage) {
        this.testImage = testImage;
    }

    public int getThermal() {
        return thermal;
    }

    public void setThermal(int thermal) {
        this.thermal = thermal;
    }

    public String generateResponse(String string) {
        try {
            String command = string.substring(0, 4);
            boolean getter = string.substring(4, 5).equals("?");
            if (getter) {
                switch (command) {
                    case POWER:
                        return getPowerString();

                    case MUTE:
                        return getMuteString();

                    case POWER_STATE:
                        return getPowerStateString();

                    case BRIGHTNESS:
                        return getBrightnessString();

                    case CONTRAST:
                        return getContrastString();

                    case THERMAL:
                        return getThermalString();

                    case LAMP_RUNTIME + 1:
                        return getLampRuntimeString(1);

                    case LAMP_RUNTIME + 2:
                        return getLampRuntimeString(2);

                    case LAMP_STATUS + 1:
                        return getLampStatusString(1);

                    case LAMP_STATUS + 2:
                        return getLampStatusString(2);

                    case LAMP_EST_TIME_REMAINING + 1:
                        return getLampTimeRemainingString(1);

                    case LAMP_EST_TIME_REMAINING + 2:
                        return getLampTimeRemainingString(2);

                    case UNIT_TOT_TIME:
                        return getTotalRunimeString();

                    case TEST_IMAGE:
                        return getTestImageString();
                }
            } else {
                String[] str = string.split(" ");
                int value = 0;
                if (str.length == 1) {
                    value = Integer.parseInt(str[0].substring(4));
                } else if (str.length == 2) {
                    if (str[1].contains("R")) {
                        value = Integer.parseInt(str[1].substring(1));
                    } else {
                        value = Integer.parseInt(str[1]);
                    }
                }
                switch (command) {
                    case POWER:
                        setPower(value);
                        return getPowerString();

                    case MUTE:
                        setMute(value);
                        return getMuteString();

                    case POWER_STATE:
                        setPowerState(value);
                        return getPowerStateString();

                    case BRIGHTNESS:
                        setBrightness(value);
                        return getBrightnessString();

                    case CONTRAST:
                        setContrast(value);
                        return getContrastString();

                    case TEST_IMAGE:
                        setTestImage(value);
                        return getTestImageString();
                }
            }
            return "ERROR";
        } catch (StringIndexOutOfBoundsException | NumberFormatException ex) {
            return "ERROR";
        }
    }

    public String generateRandomResponse() {
        int num = new Random().nextInt(14);
        switch (num) {
            case 0:
                return getPowerString();
            case 1:
                return getPowerStateString();
            case 2:
                return getMuteString();
            case 3:
                return getTotalRunimeString();
            case 4:
                return getBrightnessString();
            case 5:
                return getContrastString();
            case 6:
                return getLampTimeRemainingString(1);
            case 7:
                return getLampTimeRemainingString(2);
            case 8:
                return getLampStatusString(1);
            case 9:
                return getLampStatusString(2);
            case 10:
                return getLampRuntimeString(1);
            case 11:
                return getLampRuntimeString(2);
            case 12:
                return getThermalString();
            case 13:
                return getTestImageString();
        }
        return "ERROR";
    }
}
