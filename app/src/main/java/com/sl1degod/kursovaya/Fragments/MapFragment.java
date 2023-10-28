package com.sl1degod.kursovaya.Fragments;

import static com.sl1degod.kursovaya.R.id.frame_layout;
import static com.sl1degod.kursovaya.R.id.mapview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sl1degod.kursovaya.Adapters.ReportsAdapter;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ReportsViewModel;
import com.squareup.picasso.BuildConfig;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.Image;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObject;
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
    private ReportsViewModel viewModel;
    private ReportsAdapter adapter;
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
        ImageProvider icon = ImageProvider.fromResource(context, R.drawable.marker);
        setHasOptionsMenu(true);
        mapView = rootView.findViewById(R.id.mapview);

        mapView.getMap().move(
                new CameraPosition(new Point(54.900316, 52.275428), 12.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0), null
        );

        List<Point> points = Arrays.asList(
                new Point(54.900333, 52.275421),
                new Point(54.904679, 52.312377),
                new Point(54.903434, 52.250762),
                new Point(54.910435, 52.258074),
                new Point(54.907282, 52.274163)
        );

        for (Point point : points) {
            mapView.getMap().getMapObjects().addPlacemark(point).setIcon(icon, new IconStyle().setScale(0.5f));
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_toolbar, menu);
        menu.setGroupVisible(R.id.homeGroup, false);

        super.onCreateOptionsMenu(menu, inflater);
    }
}