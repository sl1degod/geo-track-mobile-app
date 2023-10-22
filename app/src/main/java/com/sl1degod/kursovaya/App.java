package com.sl1degod.kursovaya;

import android.app.Application;

public class App extends Application {
    private Boolean isActiveMap;


    public Boolean getActiveMap() {
        return isActiveMap;
    }

    public void setActiveMap(Boolean activeMap) {
        isActiveMap = activeMap;
    }
}
