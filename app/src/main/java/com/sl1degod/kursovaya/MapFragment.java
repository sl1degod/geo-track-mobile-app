package com.sl1degod.kursovaya;

import static com.sl1degod.kursovaya.R.id.frame_layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sl1degod.kursovaya.databinding.FragmentMapBinding;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class MapFragment extends Fragment {
    MapView mapview;
    Context context;
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getContext();
        setHasOptionsMenu(true);
        MapKitFactory.setApiKey("f082a4ae-f30e-45f0-8eff-1a1f556d6980");
        MapKitFactory.initialize(context);
        mapview = (MapView) getActivity().findViewById(R.id.mapView);
        Log.d("1230", String.valueOf(mapview));


        mapview.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0), null
        );


        Point mappoint = new Point(55.79, 37.57);
        mapview.getMap().getMapObjects().addPlacemark(mappoint);

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onStop() {
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
        super.onStart();

    }
}