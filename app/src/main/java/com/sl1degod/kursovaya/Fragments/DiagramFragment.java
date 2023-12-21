package com.sl1degod.kursovaya.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Chart;
import com.sl1degod.kursovaya.Models.Violations;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ViolationsViewModel;

import java.util.ArrayList;
import java.util.List;

public class DiagramFragment extends Fragment {

    private LineChart lineChart;

    ViolationsViewModel violationsViewModel;

    private List<Violations> violationsList = new ArrayList<>();

    List<Chart> charList = new ArrayList<>();

    private TextView desc;

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
        desc = rootView.findViewById(R.id.description_diagramm);
        context = getContext();
        setHasOptionsMenu(true);
        desc.setText("Данные графики показывают количество нарушений за все время");
        violationsViewModel = new ViewModelProvider(this).get(ViolationsViewModel.class);
        getViolations();

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

    private void chartLine() {
        List<String> dates = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        List<String> violations = new ArrayList<>();

        List<String> violations2 = new ArrayList<>();
        List<Integer> count2 = new ArrayList<>();
        List<String> dates2 = new ArrayList<>();

        List<String> violations3 = new ArrayList<>();
        List<Integer> count3 = new ArrayList<>();
        List<String> dates3 = new ArrayList<>();

        List<String> violations4 = new ArrayList<>();
        List<Integer> count4 = new ArrayList<>();
        List<String> dates4 = new ArrayList<>();

        List<String> violations5 = new ArrayList<>();
        List<Integer> count5 = new ArrayList<>();
        List<String> dates5 = new ArrayList<>();
//        for (int i = 0; i < charList.size(); i++) {
//            dates.add(charList.get(i).getDate());
//        }
//
//        for (int i = 0; i < charList.size(); i++) {
//            count.add(charList.get(i).getCount());
//        }

        for (int i = 0; i < charList.size(); i++) {
            if (charList.get(i).getViolation().equals("Поломка насоса")) {
                violations.add(charList.get(i).getViolation());
                count.add(charList.get(i).getCount());
                dates.add(charList.get(i).getDate());
            }
        }

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < violations.size(); i++) {
            entries.add(new Entry(i, count.get(i)));
        }

        for (int i = 0; i < charList.size(); i++) {
            if (charList.get(i).getViolation().equals("Поломка компрессора")) {
                violations2.add(charList.get(i).getViolation());
                count2.add(charList.get(i).getCount());
                dates2.add(charList.get(i).getDate());
            }
        }

        List<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < violations2.size(); i++) {
            entries2.add(new Entry(i, count2.get(i)));
        }

        for (int i = 0; i < charList.size(); i++) {
            if (charList.get(i).getViolation().equals("Поломка электрического оборудования")) {
                violations3.add(charList.get(i).getViolation());
                count3.add(charList.get(i).getCount());
                dates3.add(charList.get(i).getDate());
            }
        }

        List<Entry> entries3 = new ArrayList<>();
        for (int i = 0; i < violations3.size(); i++) {
            entries3.add(new Entry(i, count3.get(i)));
        }

        for (int i = 0; i < charList.size(); i++) {
            if (charList.get(i).getViolation().equals("Поломка систем освещения и вентиляции")) {
                violations4.add(charList.get(i).getViolation());
                count4.add(charList.get(i).getCount());
                dates4.add(charList.get(i).getDate());
            }
        }

        List<Entry> entries4 = new ArrayList<>();
        for (int i = 0; i < violations4.size(); i++) {
            entries4.add(new Entry(i, count4.get(i)));
        }

        for (int i = 0; i < charList.size(); i++) {
            if (charList.get(i).getViolation().equals("Поломка трубопровод и различного оборудования")) {
                violations5.add(charList.get(i).getViolation());
                count5.add(charList.get(i).getCount());
                dates5.add(charList.get(i).getDate());
            }
        }

        List<Entry> entries5 = new ArrayList<>();
        for (int i = 0; i < violations5.size(); i++) {
            entries5.add(new Entry(i, count5.get(i)));
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


        LineDataSet dataSet1 = new LineDataSet(entries5, "Нарушение 1");
        dataSet1.setColor(Color.CYAN);
        dataSet1.setLineWidth(2f);
        dataSet1.setCircleColor(Color.CYAN);
        dataSet1.setCircleRadius(4f);
        dataSet1.setDrawCircleHole(false);
        dataSet1.setDrawValues(false);

        LineDataSet dataSet2 = new LineDataSet(entries2, "Нарушение 2");
        dataSet2.setColor(Color.BLUE);
        dataSet2.setLineWidth(2f);
        dataSet2.setCircleColor(Color.BLUE);
        dataSet2.setCircleRadius(4f);
        dataSet2.setDrawCircleHole(false);
        dataSet2.setDrawValues(false);

        LineDataSet dataSet3 = new LineDataSet(entries3, "Нарушение 3");
        dataSet3.setColor(Color.RED);
        dataSet3.setLineWidth(2f);
        dataSet3.setCircleColor(Color.RED);
        dataSet3.setCircleRadius(4f);
        dataSet3.setDrawCircleHole(false);
        dataSet3.setDrawValues(false);

        LineDataSet dataSet4 = new LineDataSet(entries4, "Нарушение 4");
        dataSet4.setColor(Color.GREEN);
        dataSet4.setLineWidth(2f);
        dataSet4.setCircleColor(Color.GREEN);
        dataSet4.setCircleRadius(4f);
        dataSet4.setDrawCircleHole(false);
        dataSet4.setDrawValues(false);

        LineDataSet dataSet5 = new LineDataSet(entries5, "Нарушение 5");
        dataSet5.setColor(Color.CYAN);
        dataSet5.setLineWidth(2f);
        dataSet5.setCircleColor(Color.CYAN);
        dataSet5.setCircleRadius(4f);
        dataSet5.setDrawCircleHole(false);
        dataSet4.setDrawValues(false);

        LineData lineData = new LineData(dataSet1, dataSet2, dataSet3, dataSet4);
        lineChart.setData(lineData);


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