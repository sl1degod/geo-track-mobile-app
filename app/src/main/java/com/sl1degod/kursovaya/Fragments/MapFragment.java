package com.sl1degod.kursovaya.Fragments;

import static com.sl1degod.kursovaya.R.id.frame_layout;
import static com.sl1degod.kursovaya.R.id.mapview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.R;
import com.squareup.picasso.BuildConfig;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class MapFragment extends Fragment {
    MapView mapView;
    public Context context;

    Reports reports;

    List<Reports> reportsList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getContext();

        MapKitFactory.initialize(context);


        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        setHasOptionsMenu(true);

        String longitude = reports.getLongitude();


        mapView = rootView.findViewById(R.id.mapview);

        mapView.getMap().move(
                new CameraPosition(new Point(54.900316, 52.275428), 12.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0), null
        );




        MapObjectCollection pinsCollection = mapView.getMap().getMapObjects().addCollection();
        List<Point> points = Arrays.asList(
                new Point(Double.parseDouble(reports.getLatitude()), Double.parseDouble(reports.getLongitude()))
        );

        for (Point point : points) {
            PlacemarkMapObject placemark = pinsCollection.addPlacemark();
            placemark.setGeometry(point);
        }


        return rootView;
    }
    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    public void onStart() {
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
        super.onStart();
    }
}