package com.sl1degod.kursovaya;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class App extends Application {

    private static App mInstance;
    final String MAPKIT_API_KEY = "f082a4ae-f30e-45f0-8eff-1a1f556d6980";

    String latitude, longitude;

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
}
