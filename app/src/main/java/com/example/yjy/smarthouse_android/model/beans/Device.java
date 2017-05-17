package com.example.yjy.smarthouse_android.model.beans;

/**
 * Created by Tradoff on 2017/5/8.
 */
public class Device {
    private Integer ID;
    private Integer place;
    private Integer type;

    public Device(Integer ID, Integer place, Integer type) {
        this.ID = ID;
        this.place = place;
        this.type = type;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
