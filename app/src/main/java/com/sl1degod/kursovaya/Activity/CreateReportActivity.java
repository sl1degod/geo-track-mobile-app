package com.sl1degod.kursovaya.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sl1degod.kursovaya.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateReportActivity extends AppCompatActivity {

    ImageButton imageButton;

    ImageView imageView;

    private Uri outputFileUri;

    private Uri photoUri;

    private static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

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
    }

    private File createImageFile() throws IOException {
        // Создаем уникальное имя файла на основе временной метки
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // Получаем директорию, в которой будет создан файл
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Создаем файл с указанным именем
        File imageFile = File.createTempFile(
                imageFileName,  /* префикс имени файла */
                ".jpg",         /* расширение файла */
                storageDir      /* директория для сохранения файла */
        );

        // Возвращаем созданный файл
        return imageFile;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Обработка ошибки создания файла
            }
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            dispatchTakePictureIntent();
            String imagePath = photoUri.getPath();
            // Загружаем полноразмерное изображение с помощью Glide
            Glide.with(this).load(imagePath).into(imageView);
        }
    }


}