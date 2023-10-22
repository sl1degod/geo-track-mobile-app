package com.sl1degod.kursovaya;

import static com.sl1degod.kursovaya.R.id.frame_layout;

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

import com.sl1degod.kursovaya.databinding.FragmentMapBinding;
import com.squareup.picasso.BuildConfig;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class MapFragment extends Fragment {
    MapView mapView;
    Context context;

    App app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("f082a4ae-f30e-45f0-8eff-1a1f556d6980");
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        setHasOptionsMenu(true);
        app = getActivity().getApplication().
        context = getContext();

        MapKitFactory.initialize(context);
        mapView = rootView.findViewById(R.id.mapview);



        mapView.getMap().move(
                new CameraPosition(new Point(55.829399, 49.113720), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0), null
        );

        Point mappoint = new Point(55.79, 37.57);
        mapView.getMap().getMapObjects().addPlacemark(mappoint);

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