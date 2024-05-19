package com.sl1degod.kursovaya.Models;

public class Reports {
    public String id, fio, violations, object, latitude, longitude, violations_image, date, time, type, days;

    public Reports(String id, String fio, String violations, String object, String latitude, String longitude, String violations_image, String date, String time, String type, String days) {
        this.id = id;
        this.fio = fio;
        this.violations = violations;
        this.object = object;
        this.latitude = latitude;
        this.longitude = longitude;
        this.violations_image = violations_image;
        this.date = date;
        this.time = time;
        this.type = type;
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getViolations() {
        return violations;
    }

    public void setViolations(String violations) {
        this.violations = violations;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
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

    public String getViolations_image() {
        return violations_image;
    }

    public void setViolations_image(String violations_image) {
        this.violations_image = violations_image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
