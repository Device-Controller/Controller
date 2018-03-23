package no.ntnu.vislab.vislabcontroller.DummyBase;

import no.ntnu.vislab.vislabcontroller.Entity.Device;
import no.ntnu.vislab.vislabcontroller.Entity.DeviceType;

import java.util.ArrayList;

/**
 * DUMMYBASE DO NOT USE FOR OTHER THAN TESTING.
 */
public class DummyBase {
    ArrayList<DummyDevice> list;
    ArrayList<no.ntnu.vislab.vislabcontroller.Entity.Device> realList;

    public DummyBase() {
        int port = 1025;
        String baseIp = "158.38.101.";
        String endIp = "110";
        this.list = new ArrayList<>();
        this.list.add(new DummyDevice(50,550,270,1,"Barko F22", "F22", "Barko", baseIp + endIp, port));
        this.list.add(new DummyDevice(50,420,270,2,"Barko F22", "F22", "Barko", baseIp + endIp, port));
        this.list.add(new DummyDevice(50,290,280,3,"Barko F22", "F22", "Barko", baseIp + endIp, port));
        this.list.add(new DummyDevice(70,190,290,4,"Barko F22", "F22", "Barko", baseIp + endIp, port));
        this.list.add(new DummyDevice(110,110,305,5,"Barko F22", "F22", "Barko", baseIp + endIp, port));
        this.list.add(new DummyDevice(170,50,340,6,"Barko F22", "F22", "Barko", baseIp + endIp, port));
        this.list.add(new DummyDevice(230,50,20,7,"Barko F22", "F22", "Barko", baseIp + endIp, port));
        this.list.add(new DummyDevice(290,110,55,8,"Barko F22", "F22", "Barko", baseIp + endIp, port));
        this.list.add(new DummyDevice(330,190,70,9,"Barko F22", "F22", "Barko", baseIp + endIp, port));
        this.list.add(new DummyDevice(350,290,80,10,"Barko F22", "F22", "Barko", baseIp + endIp, port));
        this.list.add(new DummyDevice(350,420,90,11,"Barko F22", "F22", "Barko", baseIp + endIp, port));
        this.list.add(new DummyDevice(350,550,90,12,"Barko F22", "F22", "Barko", baseIp + endIp, port));
    }

    public DummyBase(int i) {
        int port = 1025;
        String baseIp = "158.38.101.";
        String endIp = "110";
        DeviceType barkoF22 = new DeviceType("projector", "barko", "F22");
        this.realList = new ArrayList<>();
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 50,550,270));
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 50,420,270));
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 50,290,280));
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 70,190,290));
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 110,110,305));
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 170,50,340));
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 230,50,20));
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 290,110,55));
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 330,190,70));
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 350,290,80));
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 350,420,90));
        this.realList.add(new Device(barkoF22, baseIp + endIp, port, 350,550,90));
    }

    public ArrayList<DummyDevice> getList() {
        return list;
    }

    public DummyDevice getSingle(int id){
        DummyDevice returnD = null;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getId() == id){
                returnD = list.get(i);
            }
        }
        return returnD;
    }

    public ArrayList<no.ntnu.vislab.vislabcontroller.Entity.Device> getRealList() {
        return realList;
    }
}


