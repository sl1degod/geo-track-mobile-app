package com.sl1degod.kursovaya.Models;

public class ReportCurrent {
    String fio, name, image;

    public ReportCurrent(String fio, String name, String image) {
        this.fio = fio;
        this.name = name;
        this.image = image;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
