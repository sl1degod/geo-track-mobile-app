package com.sl1degod.kursovaya.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sl1degod.kursovaya.Activity.CreateReportActivity;
import com.sl1degod.kursovaya.Adapters.ReportsAdapter;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ReportsViewModel;
import com.sl1degod.kursovaya.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ReportsViewModel viewModel;
    private ReportsAdapter adapter;
    App app;

    List<Reports> reportsList = new ArrayList<>();

    Reports reports;

    Context context;
    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        context = getContext();
        setHasOptionsMenu(true);
        binding.rvReports.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReportsAdapter(getContext());
        binding.rvReports.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        getAllReports();

        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getAllReports() {
        viewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), reports -> {
            if (reports == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                adapter.setReportsList(reports);
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.getAllReports();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_toolbar, menu);
//        menu.setGroupVisible(R.id.homeGroup, true);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.addPlaylists) {
//            startActivity(new Intent(context, CreateReportActivity.class));
//        }
        return super.onOptionsItemSelected(item);
    }
}