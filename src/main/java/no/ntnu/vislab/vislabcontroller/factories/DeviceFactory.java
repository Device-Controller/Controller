package no.ntnu.vislab.vislabcontroller.factories;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import no.ntnu.vislab.vislabcontroller.providers.Device;

public class DeviceFactory {
    private final ServiceLoader<Device> loader;
    private static DeviceFactory service;

    private DeviceFactory(){
        loader = ServiceLoader.load(Device.class);
    }

    public static synchronized DeviceFactory getInstance(){
        if(service == null){
            service = new DeviceFactory();
        }
        return service;
    }

    public Device getDevice(String make, String model){
        Device device = null;
        loader.reload();
        try{
            Iterator<Device> devices = loader.iterator();
            while(device == null && devices.hasNext()){
                Device tempDevice = devices.next();
                if(tempDevice.getMake().toLowerCase().equals(make.toLowerCase())
                        && tempDevice.getModel().toLowerCase().equals(model.toLowerCase())){
                    device = tempDevice;
                }
            }
        } catch (ServiceConfigurationError serviceError){
            device = null;
            System.out.println("ERROR");
            serviceError.printStackTrace();
        }
        return device;
    }
}
