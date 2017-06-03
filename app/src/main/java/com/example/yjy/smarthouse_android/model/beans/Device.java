package com.example.yjy.smarthouse_android.model.beans;

/**
 * Created by Tradoff on 2017/5/8.
 */
public class Device {
    private Integer ID;
    private Integer place;
    private Integer type;
    //designed for light,'0' stands for closed while '1' means opening
    private Integer status;

    public Device(Integer ID, Integer place, Integer type) {
        this.ID = ID;
        this.place = place;
        this.type = type;
        this.status = 0;
    }


    public Integer getStatus(){ return status; }

    public void setStatus(Integer status) { this.status = status;}

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
