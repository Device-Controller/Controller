package no.ntnu.vislab.barkof22;

public interface BarkoF22Interface {
    int getBrightness();

    int setBrightness(int value);

    int getContrast();

    int setContrast(int value);

    int getPowerState();

    int getLampRuntime(int lampNum);

    int getLampRemaining(int lampNum);

    int getTotalRuntime();

    int getLampStatus(int lampNum);

    int getTemperature();

    int testImageOn(int testImage);

    int testImageOff();
}
