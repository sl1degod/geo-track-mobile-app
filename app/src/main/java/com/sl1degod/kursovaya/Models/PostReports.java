package com.sl1degod.kursovaya.Models;

public class PostReports {
    int user_id, rep_vio_id, object_id;
    double latitude, longitude;
    String description;

    public PostReports(int user_id, int rep_vio_id, int object_id, double latitude, double longitude, String description) {
        this.user_id = user_id;
        this.rep_vio_id = rep_vio_id;
        this.object_id = object_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRep_vio_id() {
        return rep_vio_id;
    }

    public void setRep_vio_id(int rep_vio_id) {
        this.rep_vio_id = rep_vio_id;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
