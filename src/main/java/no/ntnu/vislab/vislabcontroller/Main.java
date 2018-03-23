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
        long time = System.currentTimeMillis();
        for(int i = 0; i < 12; i ++) {
            BarkoF22Projector f22 = new BarkoF22Projector(InetAddress.getByName(baseIp + endIp), port);
            System.out.println(i + " " + f22.powerOn());
            System.out.println(System.currentTimeMillis()-time);
        }
    }
}
