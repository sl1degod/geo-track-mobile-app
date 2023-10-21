package com.sl1degod.kursovaya.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sl1degod.kursovaya.HomeFragment;
import com.sl1degod.kursovaya.MapFragment;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Toolbar toolbar;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        toolbar = binding.toolBar;
        setSupportActionBar(toolbar);
        toolbar.setTitle("Главная");
        setContentView(binding.getRoot());

        bottomNavigationView = new BottomNavigationView(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();


        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.bottomHome:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                    item.setChecked(true);
                    toolbar.setTitle("Главная");
                    break;
                case R.id.bottomMap:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new MapFragment()).commit();
                    item.setChecked(true);
                    toolbar.setTitle("Карта");
                    Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
