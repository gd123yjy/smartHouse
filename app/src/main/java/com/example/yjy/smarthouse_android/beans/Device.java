package com.example.yjy.smarthouse_android.beans;

/**
 * Created by Tradoff on 2017/5/8.
 */
public class Device {
    private String ID;
    private String place;
    private String type;

    public Device(String ID, String place, String type) {
        this.ID = ID;
        this.place = place;
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
