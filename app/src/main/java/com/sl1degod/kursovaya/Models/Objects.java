package com.sl1degod.kursovaya.Models;

public class Objects {
    String id, name, latitude, longitude, uuid_image, count;

    public Objects(String id, String name, String latitude, String longitude, String uuid_image, String count) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.uuid_image = uuid_image;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUuid_image() {
        return uuid_image;
    }

    public void setUuid_image(String uuid_image) {
        this.uuid_image = uuid_image;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
