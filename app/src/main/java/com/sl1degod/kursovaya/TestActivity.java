package com.sl1degod.kursovaya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class TestActivity extends AppCompatActivity {

    public MapView mapview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapview = (MapView) findViewById(R.layout.activity_test);
        MapKitFactory.setApiKey("f082a4ae-f30e-45f0-8eff-1a1f556d6980");
        MapKitFactory.initialize(getApplicationContext());
        setContentView(R.layout.activity_test);
//        mapview.getMap().move(
//                new CameraPosition(new Point(55.829399, 49.113720), 11.0f, 0.0f, 0.0f),
//                new Animation(Animation.Type.SMOOTH, 0), null
//        );


//        Point mappoint = new Point(55.79, 37.57);
//        mapview.getMap().getMapObjects().addPlacemark(mappoint);

    }


    @Override
    protected void onStop() {
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    protected void onStart() {
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
        super.onStart();

    }
}