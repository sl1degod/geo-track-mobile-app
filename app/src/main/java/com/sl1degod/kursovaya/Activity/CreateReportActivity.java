package com.sl1degod.kursovaya.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputLayout;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Fragments.MapFragment;
import com.sl1degod.kursovaya.Models.Elimination;
import com.sl1degod.kursovaya.Models.PostReports;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.ReportsVio;
import com.sl1degod.kursovaya.Models.Violations;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ReportsViewModel;
import com.sl1degod.kursovaya.Viewmodels.ViolationsViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateReportActivity extends AppCompatActivity {

    ImageButton imageButton;

    ImageView imageView;

    Button sendReport;

    TextInputLayout descTIL;

    EditText descTIET;

    private Uri outputFileUri;
    private ViolationsViewModel violationsViewModel;
    private ReportsViewModel reportsViewModel;

    Spinner spinner;
    Spinner types_spinner;

    private Uri photoUri;

    private List<Violations> violationsList = new ArrayList<>();
    private List<Elimination> eliminations = new ArrayList<>();

    Context context;

    private File image = null;
    private String imageFileName = "";

    public int rep_vio_id;
    private static final int REQUEST_TAKE_PHOTO = 1;

    private static final int REQUEST_EDIT_PHOTO = 2;


    private GoogleApiClient mGoogleApiClient;

    private Location mLastLocation;

    private double latitude;
    private double longitude;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        context = getApplicationContext();
        violationsViewModel = new ViewModelProvider(this).get(ViolationsViewModel.class);
        reportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        spinner = findViewById(R.id.violations_spinner);
        types_spinner = findViewById(R.id.types_spinner);
        imageButton = findViewById(R.id.addReportImage);
        imageView = findViewById(R.id.setCreateReportImage);
        sendReport = findViewById(R.id.sendReport);
        descTIET = findViewById(R.id.desc_tiet);
        imageButton.setOnClickListener(e -> {
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (photoFile != null) {
                    photoUri = FileProvider.getUriForFile(context,
                            "com.example.android.fileprovider",
                            photoFile);
                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
                }
            }
        });
        sendReport.setOnClickListener(e -> {
            if (image == null) {
                Toast.makeText(context, "Пожалуйста, сделайте фото", Toast.LENGTH_SHORT).show();
            } else {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", imageFileName + ".jpg", requestFile);
                postReportVio(App.getInstance().getUser_id(), String.valueOf(spinner.getSelectedItemId() + 1),
                        String.valueOf(types_spinner.getSelectedItemId() + 1), body);
            }

        });
        getViolations();
        getEliminations();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d("location", latitude + " " + longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}

        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            } else {
                Toast.makeText(this, "Разрешение на доступ к местоположению не было предоставлено", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        outputFileUri = FileProvider.getUriForFile(context, "com.example.android.fileprovider", image);
        System.out.println(outputFileUri);
        return image;
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

    @SuppressLint("NotifyDataSetChanged")
    public void getEliminations() {
        violationsViewModel.getListElMutableLiveData().observe(this, elimination -> {
            if (elimination == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                eliminations = elimination;
                initTypesSpinner();
            }
        });
        violationsViewModel.getEliminations();
    }

    public void initSpinner() {
        List<String> violationsNames = new ArrayList<>();
        for (Violations violation : violationsList) {
            violationsNames.add(violation.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, violationsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void initTypesSpinner() {
        List<String> eliminationNames = new ArrayList<>();
        for (Elimination elimination : eliminations) {
            eliminationNames.add(elimination.getTypes());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, eliminationNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types_spinner.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void postReportVio(String user_id, String violations_id, String types_id, MultipartBody.Part requestBody) {
        reportsViewModel.getPostReportVio().observe(this, report -> {
            if (report == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "all good", Toast.LENGTH_SHORT).show();
                rep_vio_id = report.getId();
                PostReports postReports = new PostReports(Integer.parseInt(App.getInstance().getUser_id()), rep_vio_id,
                        Integer.parseInt(App.getInstance().getObject_id()), latitude, longitude, descTIET.getText().toString());
                Log.d("id", App.getInstance().getObject_id());
                Log.d("latitude", String.valueOf(latitude));
                Log.d("longitude", String.valueOf(longitude));
                Log.d("desc", String.valueOf(descTIET.getText().toString()));
                postReport(postReports);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        reportsViewModel.postReportVio(Integer.parseInt(user_id), Integer.parseInt(violations_id), Integer.parseInt(types_id), requestBody);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void postReport(PostReports postReports) {
        reportsViewModel.getPostReport().observe(this, report -> {
            if (report == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
            }
        });
        reportsViewModel.postReport(postReports);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outputFileUri);
                imageView.setImageBitmap(bitmap);
                imageView.setOnClickListener(e -> {
                    if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
                        Intent editIntent = new Intent(Intent.ACTION_EDIT);
                        editIntent.setDataAndType(outputFileUri, "image/*");
                        editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        editIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                        List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(editIntent, PackageManager.MATCH_DEFAULT_ONLY);

                        for (ResolveInfo resolveInfo : resInfoList) {
                            String packageName = resolveInfo.activityInfo.packageName;
                            this.grantUriPermission(packageName, outputFileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        }
                        startActivityForResult(Intent.createChooser(editIntent, "Edit Image"), REQUEST_EDIT_PHOTO);
                    }
                });

                if (requestCode == REQUEST_EDIT_PHOTO) {
                    // Перезагрузка отредактированного изображения
                    Bitmap editedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outputFileUri);
                    imageView.setImageBitmap(editedBitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }







}