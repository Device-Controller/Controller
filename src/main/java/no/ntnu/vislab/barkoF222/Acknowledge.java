/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkoF222;

import java.util.HashMap;

/**
 *
 * @author Kristoffer
 */
public class Acknowledge {

    private static final String HEADER = "%";
    private static final String SEPERATOR = " ";
    private static final HashMap<String, String> translateMap = new HashMap<>();
    private static final HashMap<String, HashMap<Integer, String>> valueMap = new HashMap<>();
    private static final HashMap<String, String> multiMap = new HashMap<>();

    static {
        generateTranslationMap();
        generatePowerStateTable();
        generatePowerTable();
        generateMuteTable();
        generateLampStateTable();
    }

    private final String clearText;

    public Acknowledge(String acknowledge) {
        this.clearText = process(acknowledge);
    }

    public String getExplaination() {
        return clearText;
    }

    private String process(String acknowledge) {
        if (acknowledge.contains(HEADER)) {
            String[] str = acknowledge.split(SEPERATOR);
            String header = str[0];
            String command = str[1];
            String value = str[2];
            String extra = null;
            if (value.equals("e00001")) {
                extra = str[3];
            }
            StringBuilder strb = new StringBuilder();
            if (translateMap.get(command) != null) {
                strb.append(translateMap.get(command));
                strb.append(" ");
            } else {
                return acknowledge;
            }
            if (valueMap.get(command) != null) {
                strb.append(valueMap.get(command).get(Integer.parseInt(value)));
            } else {
                strb.append(value);
            }
            if (extra != null) {
                strb.append(" ");
                strb.append(extra);
            }
            return strb.toString();

        }
        return acknowledge;
    }
    private static void generateTranslationMap() {
        translateMap.put("POWR", "POWER");
        translateMap.put("BRIG", "BRIGHTNESS");
        translateMap.put("PMUT", "MUTE");
        translateMap.put("POST", "POWER-STATE");
        translateMap.put("CNTR", "CONTRAST");
        translateMap.put("LTR1", "LAMP 1 RUNTIME");
        translateMap.put("LRM1", "LAMP 1 REMAINING TIME");
        translateMap.put("LST1", "LAMP 1 STATUS");
        translateMap.put("LTR2", "LAMP 2 RUNTIME");
        translateMap.put("LRM2", "LAMP 2 REMAINING TIME");
        translateMap.put("LST2", "LAMP 2 STATUS");
        translateMap.put("UTOT", "UNIT TOTAL TIME");
        translateMap.put("THRM", "TEMPERATURE");
    }

    private static void generatePowerStateTable() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "Deep Sleep");
        map.put(1, "Off");
        map.put(2, "Powering Up");
        map.put(3, "On");
        map.put(4, "Powering Down");
        map.put(5, "Critical Powering Down");
        map.put(6, "Critical Off");
        valueMap.put("POST", map);
    }

    private static void generatePowerTable() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "Off");
        map.put(1, "On");
        valueMap.put("POWR", map);
    }

    private static void generateMuteTable() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "Muted");
        map.put(1, "Unmuted");
        valueMap.put("PMUT", map);
    }
    
    private static void generateLampStateTable() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "Broken");
        map.put(1, "Warming up");
        map.put(2, "Lamp is on");
        map.put(3, "Lamp is off");
        map.put(4, "Lamp is cooling down");
        map.put(5, "Lamp is not present");
        valueMap.put("LST", map);
        valueMap.put("LST1", map);
        valueMap.put("LST2", map);
    }
}
