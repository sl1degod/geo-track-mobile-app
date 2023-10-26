package com.sl1degod.kursovaya.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sl1degod.kursovaya.Adapters.ReportsAdapter;
import com.sl1degod.kursovaya.Adapters.UserAdapter;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ReportsViewModel;
import com.sl1degod.kursovaya.Viewmodels.UserViewModel;
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
                Toast.makeText(context, "LETS GOOO", Toast.LENGTH_SHORT).show();
                adapter.setReportsList(reports);
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.getAllReports();
    }
}