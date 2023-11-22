package com.sl1degod.kursovaya.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.sl1degod.kursovaya.Models.Chart;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ViolationsViewModel;

import java.util.ArrayList;
import java.util.List;

public class DiagramFragment extends Fragment {

    private LineChart lineChart;

    ViolationsViewModel violationsViewModel;

    List<Chart> charList = new ArrayList<>();

    Context context;

    Chart chart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diagram, container, false);
        lineChart = rootView.findViewById(R.id.chart);
        context = getContext();
        setHasOptionsMenu(true);
        violationsViewModel = new ViewModelProvider(this).get(ViolationsViewModel.class);
        getCharViolations();

        return rootView;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getCharViolations() {
        violationsViewModel.getCharList().observe(getViewLifecycleOwner(), violations -> {
            if (violations == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                charList = violations;
                chartLine();
            }
        });
        violationsViewModel.getCharViolations();
    }

    private void chartLine() {
        List<String> dates = new ArrayList<>();
        for (int i = 0; i < charList.size(); i++) {
            dates.add(charList.get(i).getDate());
        }

        List<String> violations = new ArrayList<>();
        for (int i = 0; i < charList.size(); i++) {
            violations.add(charList.get(i).getViolation());
        }
        List<Integer> count = new ArrayList<>();
        for (int i = 0; i < charList.size(); i++) {
            count.add(charList.get(i).getCount());
        }

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < violations.size(); i++) {
            entries.add(new Entry(i, count.get(i)));
        }

        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxisLeft = lineChart.getAxisLeft();
        YAxis yAxisRight = lineChart.getAxisRight();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(2f);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < dates.size()) {
                    return dates.get(index);
                } else {
                    return "";
                }
            }
        });
        yAxisRight.setEnabled(false);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setGranularity(1f);
        yAxisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        LineData lineData = new LineData(new LineDataSet(entries, charList.get(0).getViolation()));
        lineChart.setData(lineData);
//        lineChart.zoom(-1, 1, 10, 10);
        lineChart.invalidate();
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_toolbar, menu);
        menu.setGroupVisible(R.id.homeGroup, false);
        menu.setGroupVisible(R.id.profile, false);

        super.onCreateOptionsMenu(menu, inflater);
    }
}