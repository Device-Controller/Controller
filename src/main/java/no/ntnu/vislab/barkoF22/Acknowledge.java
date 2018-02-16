/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkoF22;

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

    static {
        generateTranslationMap();
        generatePowerStateTable();
        generatePowerTable();
    }
    private final String clearText;

    public Acknowledge(String acknowledge) {
        this.clearText = proccess(acknowledge);
    }

    public String getExplaination() {
        return clearText;
    }

    private String proccess(String acknowledge) {
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
        translateMap.put("LTR", "LAMP RUNTIME");
        translateMap.put("LRM", "LAMP REMAINING TIME");
        translateMap.put("LST", "LAMP STATUS");
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
}
