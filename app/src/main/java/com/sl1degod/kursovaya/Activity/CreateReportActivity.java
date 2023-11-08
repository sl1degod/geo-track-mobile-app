package com.sl1degod.kursovaya.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.Violations;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ObjectsViewModel;
import com.sl1degod.kursovaya.Viewmodels.ViolationsViewModel;

import java.io.File;
import java.io.IOException;
import java.security.acl.Owner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateReportActivity extends AppCompatActivity {

    ImageButton imageButton;

    ImageView imageView;

    private Uri outputFileUri;
    private ViolationsViewModel violationsViewModel;

    Spinner spinner;

    private Uri photoUri;

    List<Violations> violationsList = new ArrayList<>();
    Context context;

    private static final int TAKE_PICTURE_REQUEST = 1;
    private static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        context = getApplicationContext();
        violationsViewModel = new ViewModelProvider(this).get(ViolationsViewModel.class);
        spinner = findViewById(R.id.violations_spinner);
        imageButton = findViewById(R.id.addReportImage);
        imageView = findViewById(R.id.setCreateReportImage);
        imageButton.setOnClickListener(e -> {
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try{
                startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
            }catch (ActivityNotFoundException ex){
                ex.printStackTrace();
            }
        });
        getViolations();

    }

    @SuppressLint("NotifyDataSetChanged")
    public void getViolations() {
        violationsViewModel.getListMutableLiveData().observe(this, violations -> {
            if (violations == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                violationsList = violations;
                initSpinner();
            }
        });
        violationsViewModel.getViolations();
    }

    private void initSpinner() {
        List<String> violationsNames = new ArrayList<>();
        for (Violations violation : violationsList) {
            violationsNames.add(violation.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, violationsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thumbnailBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(thumbnailBitmap);
        }
    }


}