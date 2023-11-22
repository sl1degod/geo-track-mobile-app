package com.sl1degod.kursovaya;

import android.app.Application;

import com.sl1degod.kursovaya.Models.Objects;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.Violations;
import com.yandex.mapkit.MapKitFactory;

import java.util.List;

public class App extends Application {

    private static App mInstance;
    final String MAPKIT_API_KEY = "f082a4ae-f30e-45f0-8eff-1a1f556d6980";

    String user_id;

    String object_id;

    int report_id;

    List<Reports> reportsList;

    List<Objects> objectsList;

    List<Violations> violationsList;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }

    private Boolean isActiveMap;

    public static synchronized App getInstance() {
        return mInstance;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }


    public List<Violations> getViolationsList() {
        return violationsList;
    }

    public void setViolationsList(List<Violations> violationsList) {
        this.violationsList = violationsList;
    }

    public List<Reports> getReportsList() {
        return reportsList;
    }

    public void setReportsList(List<Reports> reportsList) {
        this.reportsList = reportsList;
    }

    public List<Objects> getObjectsList() {
        return objectsList;
    }

    public void setObjectsList(List<Objects> objectsList) {
        this.objectsList = objectsList;
    }
}
