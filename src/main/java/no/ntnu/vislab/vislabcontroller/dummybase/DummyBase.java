package no.ntnu.vislab.vislabcontroller.dummybase;

import java.util.ArrayList;

/**
 * DUMMYBASE DO NOT USE FOR OTHER THAN TESTING.
 */
public class DummyBase {
    ArrayList<DummyDevice> list;

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
}


