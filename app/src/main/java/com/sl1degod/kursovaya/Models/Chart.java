package com.sl1degod.kursovaya.Models;

public class Chart {
    String date, violation;
    int id, count;

    public Chart(String date, String violation, int id, int count) {
        this.date = date;
        this.violation = violation;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


