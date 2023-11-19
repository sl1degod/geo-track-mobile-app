package com.sl1degod.kursovaya.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sl1degod.kursovaya.Activity.CreateReportActivity;
import com.sl1degod.kursovaya.Adapters.ReportsAdapter;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.PostReports;
import com.sl1degod.kursovaya.Models.ReportCurrent;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.Violations;
import com.sl1degod.kursovaya.Network.RetrofitInstance;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ReportsViewModel;
import com.sl1degod.kursovaya.Viewmodels.ViolationsViewModel;
import com.sl1degod.kursovaya.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private ReportsViewModel viewModel;
    private ReportsAdapter adapter;

    private ViolationsViewModel violationsViewModel;
    App app;

    Reports reportCurrent;

    Reports reports;

    Context context;
    FragmentHomeBinding binding;

    List<Reports> reportsList = new ArrayList<>();

    Spinner spinner;

    private List<Violations> violationsList = new ArrayList<>();


    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        context = getContext();
        setHasOptionsMenu(true);
        binding.rvReports.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReportsAdapter(getContext(), reportsList);
        binding.rvReports.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        violationsViewModel = new ViewModelProvider(this).get(ViolationsViewModel.class);
        dialog = new Dialog(context);
        getViolations();
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
                reportsList = reports;
            }
        });
        viewModel.getAllReports();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getReport(int id) {
        viewModel.getReportCurrentMutableLiveData().observe(getViewLifecycleOwner(), report -> {
            if (report == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                reportCurrent = report;
            }
        });
        viewModel.getReport(id);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getViolations() {
        violationsViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), violations -> {
            if (violations == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                violationsList = violations;
            }
        });
        violationsViewModel.getViolations();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_toolbar, menu);
        menu.setGroupVisible(R.id.homeGroup, true);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sortReports:
                adapter = new ReportsAdapter(context, reportsList);
                Collections.reverse(reportsList);
                binding.rvReports.setAdapter(adapter);
                break;
            case R.id.filterReports:
                showDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        dialog.setContentView(R.layout.filter_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        spinner = dialog.findViewById(R.id.filter_violations_spinner);
        Button complete_button = dialog.findViewById(R.id.button_ready);
        Button cancel_button = dialog.findViewById(R.id.button_cancel);
        initSpinner(spinner);

        complete_button.setOnClickListener(v -> {
            if (spinner.getSelectedItem().toString().equals("Не выбрано")) {
                getAllReports();
            } else {
                filterByViolation(spinner.getSelectedItem().toString());

            }
            dialog.hide();

        });

        cancel_button.setOnClickListener(v -> {
            dialog.cancel();

        });

        dialog.show();
    }

    private void filterByViolation(String word) {
        ArrayList<Reports> filteredList = new ArrayList<>();
        for (int i = 0; i < reportsList.size(); i++) {
            if (reportsList.get(i).getViolations().contains(word)) {
                filteredList.add(reportsList.get(i));
            }
        }
            adapter.setFilteredList(filteredList);

    }

    public void initSpinner(Spinner spinner) {
        List<String> violationsNames = new ArrayList<>();
        violationsNames.add("Не выбрано");
        for (Violations violation : violationsList) {
            violationsNames.add(violation.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, violationsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}