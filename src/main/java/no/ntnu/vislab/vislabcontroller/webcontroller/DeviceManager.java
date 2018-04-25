package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.ntnu.vislab.vislabcontroller.factories.ProjectorFactory;
import no.ntnu.vislab.vislabcontroller.providers.Device;
import no.ntnu.vislab.vislabcontroller.providers.Projector;
import no.ntnu.vislab.vislabcontroller.repositories.DeviceRepository;

@Component
public abstract class DeviceManager {
    private static Map<Integer, Device> activeDevices;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private List<DeviceManager> controllers;

    protected synchronized Projector getProjector(int id){
        Projector projector;
        if (!getProjectors().keySet().contains(id)) {
            if (deviceRepository.findById(id).isPresent()) {
                no.ntnu.vislab.vislabcontroller.entity.Device device = deviceRepository.findById(id).get();
                ProjectorFactory pf = ProjectorFactory.getInstance();
                projector = pf.getProjector(device.getDeviceInfo().getManufacturer(), device.getDeviceInfo().getModel());

                if (projector != null) {
                    projector.setIpAddress(device.getIpAddress());
                    projector.setPort(device.getPort());
                    activeDevices.put(device.getId(), projector);
                }
            } else {
                projector = null;
            }
        } else {
            Device device = activeDevices.get(id);
            if(device instanceof Projector) {
                projector = (Projector) device;
            } else {
                projector = null;
            }
        }
        return projector;
    }
    private Map<Integer, Device> getDevices(){
        checkActiveDevices();
        Map<Integer, Device> devices = new HashMap<>();
        activeDevices.forEach((i,d)-> {if(d != null && !(d instanceof Projector)) devices.put(i,d); });
        return devices;
    }

    private void checkActiveDevices() {
        if (activeDevices == null) {
            activeDevices = new HashMap<>();
        }
    }

    private Map<Integer, Projector> getProjectors(){
        checkActiveDevices();
        Map<Integer, Projector> projectors = new HashMap<>();
        activeDevices.forEach((i,d)-> {if(d != null && (d instanceof Projector)) projectors.put(i,(Projector) d); });
        return projectors;
    }

    @RequestMapping("/device")
    private String locateDevicePage(@RequestParam("id") int id){
        String deviceControllerPageLink = "";
        for(DeviceManager mc : controllers){
            if(deviceControllerPageLink.isEmpty()){
                deviceControllerPageLink = mc.getDevicePage(id);
                if(deviceControllerPageLink == null){
                    deviceControllerPageLink = "";
                }
            }
        }
        return deviceControllerPageLink;
    }

    /**
     *  Returns the implementing classes path to their html page. Default is empty string, causing a page reload.
     * @param id The id of the device in question.
     * @return the string containg the path to the devices html page
     */
    public String getDevicePage(int id){
        return "";
    }
}
