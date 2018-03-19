package no.ntnu.vislab.vislabcontroller.DummyBase;

import java.util.ArrayList;

public class DummyBase {
    ArrayList<Device> list;

    public DummyBase() {
        this.list = new ArrayList<>();
        this.list.add(new Device(50,550,270,1,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
        this.list.add(new Device(50,420,270,2,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
        this.list.add(new Device(50,290,280,3,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
        this.list.add(new Device(70,190,290,4,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
        this.list.add(new Device(110,110,305,5,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
        this.list.add(new Device(170,50,340,6,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
        this.list.add(new Device(230,50,20,7,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
        this.list.add(new Device(290,110,55,8,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
        this.list.add(new Device(330,190,70,9,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
        this.list.add(new Device(350,290,80,10,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
        this.list.add(new Device(350,420,90,11,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
        this.list.add(new Device(350,550,90,12,"Barko F22", "F22", "Barko", "158.38.101.110", 1025));
    }

    public ArrayList<Device> getList() {
        return list;
    }

    public Device getSingle(int id){
        Device returnD = null;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getId() == id){
                returnD = list.get(i);
            }
        }
        return returnD;
    }
}


