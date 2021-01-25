package com.example.worker_world;

import com.google.firebase.firestore.GeoPoint;

public class Worker {
    String name;
    String work;
    String cost;
    String contactno;
    String address;
    Double latitude;
    Double longitude;

    public Worker() {
    }
    public Worker(String name, String work, String cost, String contactno, String address, Double latitude, Double longitude) {
        this.name = name;
        this.work = work;
        this.cost = cost;
        this.contactno = contactno;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getWork() {
        return work;
    }

    public String getCost() {
        return cost;
    }

    public String getContactno() {
        return contactno;
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }


}
