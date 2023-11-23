package com.sl1degod.kursovaya.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.sl1degod.kursovaya.Adapters.ReportsAdapter;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Objects;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.Violations;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ObjectsViewModel;
import com.sl1degod.kursovaya.Viewmodels.ReportsViewModel;
import com.sl1degod.kursovaya.Viewmodels.ViolationsViewModel;
import com.sl1degod.kursovaya.databinding.FragmentHomeBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    private ReportsViewModel viewModel;
    private ReportsAdapter adapter;

    private ViolationsViewModel violationsViewModel;

    private ObjectsViewModel objectsViewModel;

    App app;

    Reports reportCurrent;

    Reports reports;

    Context context;
    FragmentHomeBinding binding;

    List<Reports> reportsList = new ArrayList<>();

    Spinner spinnerVio;
    Spinner spinnerObj;

    Long startDateFilter;
    Long endDateFilter;

    private List<Violations> violationsList = new ArrayList<>();
    private List<Objects> objectsList = new ArrayList<>();

    TextView dateStart, dateInterval;

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
        objectsViewModel = new ViewModelProvider(this).get(ObjectsViewModel.class);
        dialog = new Dialog(context);
        getViolations();
        getObjects();
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
                App.getInstance().setReportsList(reports);

            }
        });
        viewModel.getAdminReports(Integer.parseInt(App.getInstance().getUser_id()));
//        viewModel.getAdminReports(Integer.parseInt(String.valueOf(1)));
    }


    @SuppressLint("NotifyDataSetChanged")
    public void getViolations() {
        violationsViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), violations -> {
            if (violations == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                violationsList = violations;
                App.getInstance().setViolationsList(violations);
            }
        });
        violationsViewModel.getViolations();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getObjects() {
        objectsViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), objects -> {
            if (objects == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                objectsList = objects;
                App.getInstance().setObjectsList(objects);
            }
        });
        objectsViewModel.getObjects();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_toolbar, menu);
        menu.setGroupVisible(R.id.homeGroup, true);
        menu.setGroupVisible(R.id.profile, false);

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
        spinnerVio = dialog.findViewById(R.id.filter_violations_spinner);
        spinnerObj = dialog.findViewById(R.id.filter_object_spinner);
        Button complete_button = dialog.findViewById(R.id.button_ready);
        Button cancel_button = dialog.findViewById(R.id.button_cancel);
        dateInterval = dialog.findViewById(R.id.dateInterval);
        dateStart = dialog.findViewById(R.id.setStartDate);
        dateInterval.setOnClickListener(e -> {
            showDateRangePicker();
        });
        initVioSpinner(spinnerVio);
        initObjectSpinner(spinnerObj);

        complete_button.setOnClickListener(v -> {
            if (spinnerVio.getSelectedItem().toString().equals("Не выбрано") && spinnerObj.getSelectedItem().equals("Не выбрано")) {
                getAllReports();
            } else {
                try {
                    filter();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            dialog.hide();

        });

        cancel_button.setOnClickListener(v -> {
            dialog.cancel();
            getAllReports();
        });
        dialog.show();
    }

    private void showDateRangePicker() {
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Выберите диапазон дат")
                .setTheme(R.style.myMaterialCalendarHeaderToggleButton)
                .build();

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            String startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selection.first));
            String endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selection.second));
            dateStart.setText(startDate + " - " + endDate);
            startDateFilter = selection.first;
            endDateFilter = selection.second;
            System.out.println(startDateFilter + " - " + endDateFilter);
        });
        materialDatePicker.show(getParentFragmentManager(), "MaterialDateRangePicker");
    }

    private void filter() throws ParseException {
        TextView error = binding.clear;

        ArrayList<Reports> filteredList = new ArrayList<>();
        String selectedViolation = spinnerVio.getSelectedItem().toString();
        String selectedObject = spinnerObj.getSelectedItem().toString();
        for (Reports report : reportsList) {
            if (report.getViolations() != null && report.getObject() != null) {
                if (spinnerVio.getSelectedItem().equals("Не выбрано") && !spinnerObj.getSelectedItem().equals("Не выбрано") &&
                        report.getObject().contains(selectedObject)) {
                    filteredList.add(report);
                } else if (!spinnerVio.getSelectedItem().equals("Не выбрано") && spinnerObj.getSelectedItem().equals("Не выбрано") &&
                        report.getViolations().contains(selectedViolation)) {
                    filteredList.add(report);
                } else if (!spinnerVio.getSelectedItem().equals("Не выбрано") && !spinnerObj.getSelectedItem().equals("Не выбрано") &&
                        report.getViolations().contains(selectedViolation) && report.getObject().contains(selectedObject)) {
                    filteredList.add(report);
                }
                if (filteredList.isEmpty()) {
                    error.setVisibility(View.VISIBLE);
                } else {
                    error.setVisibility(View.INVISIBLE);
                }
            }
        }
        adapter.setFilteredList(filteredList);
    }

    public void initVioSpinner(Spinner spinnerVio) {
        List<String> violationsNames = new ArrayList<>();
        violationsNames.add("Не выбрано");
        for (Violations violation : violationsList) {
            violationsNames.add(violation.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, violationsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVio.setAdapter(adapter);
    }

    public void initObjectSpinner(Spinner spinnerObj) {
        List<String> objectsNames = new ArrayList<>();
        objectsNames.add("Не выбрано");
        for (Objects objects : objectsList) {
            objectsNames.add(objects.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, objectsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerObj.setAdapter(adapter);

    }
}