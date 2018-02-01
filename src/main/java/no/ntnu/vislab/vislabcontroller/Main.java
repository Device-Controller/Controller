/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.vislabcontroller;

/**
 *
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) {
        Connector c = new Connector("158.38.65." + 45, 1025);
        c.powerON(0);
    }
}
