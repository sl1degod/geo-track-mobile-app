package com.sl1degod.kursovaya;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class App extends Application {

    private static App mInstance;
    final String MAPKIT_API_KEY = "f082a4ae-f30e-45f0-8eff-1a1f556d6980";

    String user_id;

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
}
