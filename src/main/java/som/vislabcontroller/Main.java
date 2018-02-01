/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package som.vislabcontroller;

/**
 *
 * @author Kristoffer
 */
public class Main {

    public static void main(String[] args) {
        for (int i = 40; i < 52; i++) {
            Connector.powerON("158.38.65."+i, 1025, 1);
            float time = System.currentTimeMillis();
            while(time +1000 < System.currentTimeMillis()){
                
            }
        }
    }
}
