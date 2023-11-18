package com.sl1degod.kursovaya.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Fragments.HomeFragment;
import com.sl1degod.kursovaya.Models.ReportCurrent;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Network.RetrofitInstance;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ReportsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.AdapterViewHolder> {

    List<Reports> reportsList = new ArrayList<>();

    ReportCurrent reportCurrent;
    Context context;

    private ReportsViewModel viewModel;

    public ReportsAdapter(Context context) {
        this.context = context;
    }

    public List<Reports> getReportsList() {
        return reportsList;
    }

    public void setReportsList(List<Reports> reportsList) {
        this.reportsList = reportsList;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_report_model, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.bind(reportsList.get(position));
//        new HomeFragment().getReport(position);
//
//        holder.itemView.setOnClickListener(v -> {
//            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
//            bottomSheetDialog.setContentView(R.layout.bottomsheetreport);
//            TextView fio = bottomSheetDialog.findViewById(R.id.set_fio_report);
//            TextView violations = bottomSheetDialog.findViewById(R.id.set_violations_report);
//            ImageView reportImage = bottomSheetDialog.findViewById(R.id.setImageReport);
//
//            fio.setText(reportCurrent.getFio());
//            violations.setText(reportCurrent.getName());
//
//            Glide.with(context)
//                    .load(RetrofitInstance.getRetrofitInstance().baseUrl() + "static/reports" + reportCurrent.getImage())
//                    .centerCrop()
//                    .into(reportImage);
//
//            bottomSheetDialog.show();
//        });
    }

    @Override
    public int getItemCount() {
        return reportsList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        TextView id, FIO, violations, object;
        ImageView violations_image;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.set_report_id);
            FIO = itemView.findViewById(R.id.set_report_fio);
            violations = itemView.findViewById(R.id.set_name_report);
            object = itemView.findViewById(R.id.set_object);
            violations_image = itemView.findViewById(R.id.setReportImage);
        }

        public void bind(Reports reports) {
            id.setText(reports.getId());
            FIO.setText(reports.getFio());
            violations.setText(reports.getViolations());
            object.setText(reports.getObject());

            Glide.with(context)
                    .load(RetrofitInstance.getRetrofitInstance().baseUrl() + "static/" + reports.getViolations_image())
                    .centerCrop()
                    .into(violations_image);
        }
    }


}
