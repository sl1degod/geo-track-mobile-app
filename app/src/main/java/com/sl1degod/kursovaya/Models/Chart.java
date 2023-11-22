package com.sl1degod.kursovaya.Models;

public class Chart {
    String date, violation;
    int object_id, count;

    public Chart(String date, String violation, int object_id, int count) {
        this.date = date;
        this.violation = violation;
        this.object_id = object_id;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


