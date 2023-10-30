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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sl1degod.kursovaya.Adapters.ReportsAdapter;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Objects;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ObjectsViewModel;
import com.sl1degod.kursovaya.Viewmodels.ReportsViewModel;
import com.squareup.picasso.BuildConfig;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.Image;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MapFragment extends BottomSheetDialogFragment {
    MapView mapView;
    public Context context;

    public Reports reports1;
    private ReportsViewModel viewModel;

    private ObjectsViewModel objectsViewModel;

    private ReportsAdapter adapter;
    List<Reports> reportsList = new ArrayList<>();

    List<Objects> objectsList = new ArrayList<>();

    List<Point> points = new ArrayList<>();

    String idObject = "";
    ImageProvider icon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getContext();
        icon = ImageProvider.fromResource(context, R.drawable.marker);
        MapKitFactory.initialize(context);
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        setHasOptionsMenu(true);
        mapView = rootView.findViewById(R.id.mapview);
        viewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        objectsViewModel = new ViewModelProvider(this).get(ObjectsViewModel.class);
        getAllReports();
        getObject();

        mapView.getMap().move(
                new CameraPosition(new Point(54.900316, 52.275428), 12.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0), null
        );


        return rootView;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getAllReports() {
        viewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), reports -> {
            if (reports == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                reportsList = reports;
            }
        });
        viewModel.getAllReports();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getObject() {
        objectsViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), objects -> {
            if (objects == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                objectsList = objects;
                addPoints();
            }
        });
        objectsViewModel.getObjects();
    }



    private void addPoints() {
        Log.d("12131", reportsList.toString());
        objectsList.forEach(objects -> {
            Point point = new Point(Double.parseDouble(objects.getLatitude()), Double.parseDouble(objects.getLongitude()));
            points.add(point);
        });

        for (Point point : points) {
            mapView.getMap().getMapObjects().addPlacemark(point).setIcon(icon, new IconStyle().setScale(0.5f));
            mapView.getMap().getMapObjects().addTapListener(mapObjectTapListener);
        }
    }

    private MapObjectTapListener mapObjectTapListener = new MapObjectTapListener() {
        @Override
        public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
            showDialog();

//            getObject(userData.toString());
            return false;
        }
    };

    private void showDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.MyBottomDialog);
        bottomSheetDialog.setContentView(R.layout.bottomsheet);
        TextView set_id_map = bottomSheetDialog.findViewById(R.id.set_id_map);
        TextView set_object_map = bottomSheetDialog.findViewById(R.id.set_object_map);
        TextView set_latitude_map = bottomSheetDialog.findViewById(R.id.set_latitude_map);
        TextView set_longitude_map = bottomSheetDialog.findViewById(R.id.set_longitude_map);
        ImageView setImageMap = bottomSheetDialog.findViewById(R.id.setImageMap);
        try {
//            Objects objects = objectsList.get(getObject(id));

            set_id_map.setText("1221");
//            Log.d("objcetsID", objects.getId());
//            set_object_map.setText(objects.getName());
//            set_latitude_map.setText(objects.getLatitude());
//            set_longitude_map.setText(objects.getLongitude());
//            Glide.with(context)
//                    .load(objects.getImage())
//                    .centerCrop()
//                    .into(setImageMap);

        } catch (Exception ex) {
            Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        bottomSheetDialog.show();
    };



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