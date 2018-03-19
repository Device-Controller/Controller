package no.ntnu.vislab.vislabcontroller.DummyBase;

public class Device {
    private int x;
    private int y;
    private int rotDeg;
    private int id;
    private String name;
    private String model;
    private String make;
    private String ip;
    private int port;

    public Device(int x, int y, int rotDeg, int id, String name, String model, String make, String ip, int port) {
        this.x = x;
        this.y = y;
        this.rotDeg = rotDeg;
        this.id = id;
        this.name = name;
        this.model = model;
        this.make = make;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRotDeg() {
        return rotDeg;
    }

    public void setRotDeg(int rotDeg) {
        this.rotDeg = rotDeg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }
}