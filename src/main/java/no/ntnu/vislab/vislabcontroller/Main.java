package no.ntnu.vislab.vislabcontroller;


import java.net.InetAddress;

import no.ntnu.vislab.barkof22.BarkoF22Projector;

/**
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) throws Exception {
        int port = 1025;
        String baseIp = "158.38.101.";
        String endIp = "110";
        BarkoF22Projector f22 = new BarkoF22Projector(InetAddress.getByName(baseIp+endIp), port);
        System.out.println(f22.unMute());
        System.out.println(f22.mute());
        System.out.println(f22.getLampRuntime(1));
        System.out.println(f22.getLampRuntime(2));
        System.out.println(f22.getLampRemaining(1));
        System.out.println(f22.getLampRemaining(2));
        System.out.println(f22.getLampStatus(1));
        System.out.println(f22.getLampStatus(2));
        System.out.println(f22.testImageOn(1));
        System.out.println(f22.testImageOff());
        System.out.println(f22.getBrightness());
        System.out.println(f22.setBrightness(50));
        System.out.println(f22.setContrast(50));
        System.out.println(f22.getContrast());
        System.out.println(f22.getTemperature());
        System.out.println(f22.getTotalRuntime());
        System.out.println("LUL");
    }
}
