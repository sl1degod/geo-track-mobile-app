package com.sl1degod.kursovaya.Models;

public class Elimination {
    private String id;
    private String type;
    private String days;

    public Elimination(String id, String type, String days) {
        this.id = id;
        this.type = type;
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypes() {
        return type;
    }

    public void setTypes(String types) {
        this.type = types;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
