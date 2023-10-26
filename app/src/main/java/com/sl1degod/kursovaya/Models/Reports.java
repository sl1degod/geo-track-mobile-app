package com.sl1degod.kursovaya.Models;

public class Reports {
    String id, FIO, violations, violations_image;

    public Reports(String id, String FIO, String violations, String violations_image) {
        this.id = id;
        this.FIO = FIO;
        this.violations = violations;
        this.violations_image = violations_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getViolations() {
        return violations;
    }

    public void setViolations(String violations) {
        this.violations = violations;
    }

    public String getViolations_image() {
        return violations_image;
    }

    public void setViolations_image(String violations_image) {
        this.violations_image = violations_image;
    }
}
