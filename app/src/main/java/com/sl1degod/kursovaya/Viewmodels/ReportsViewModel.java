package com.sl1degod.kursovaya.Viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sl1degod.kursovaya.Models.PostReports;
import com.sl1degod.kursovaya.Models.ReportCurrent;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.ReportsVio;
import com.sl1degod.kursovaya.Network.RetrofitInstance;
import com.sl1degod.kursovaya.Network.Routes;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsViewModel extends ViewModel {

    MutableLiveData <List<Reports>> listMutableLiveData = new MutableLiveData<>();
    MutableLiveData <ReportsVio> postReportVio = new MutableLiveData<>();
    MutableLiveData <PostReports> postReport = new MutableLiveData<>();
    MutableLiveData <Reports> reportCurrentMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Reports> getReportCurrentMutableLiveData() {
        return reportCurrentMutableLiveData;
    }

    public MutableLiveData<PostReports> getPostReport() {
        return postReport;
    }

    public MutableLiveData<List<Reports>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public MutableLiveData<ReportsVio> getPostReportVio() {
        return postReportVio;
    }

    public void setListMutableLiveData(MutableLiveData<List<Reports>> listMutableLiveData) {
        this.listMutableLiveData = listMutableLiveData;
    }

    public void getAllReports() {
        Routes routes = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        Call<List<Reports>> call = routes.getAllReports();
        call.enqueue(new Callback<List<Reports>>() {
            @Override
            public void onResponse(@NonNull Call<List<Reports>> call, @NonNull Response<List<Reports>> response) {
                if (response.isSuccessful()) {
                    listMutableLiveData.postValue(response.body());
                    Log.d("TAG", "onResponse: " + response.body());
                } else {
                    listMutableLiveData.postValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Reports>> call, @NonNull Throwable t) {
                listMutableLiveData.postValue(null);
            }
        });
    }

    public void getReport(int id) {
        Routes routes = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        Call<Reports> call = routes.getReport(id);
        call.enqueue(new Callback<Reports>() {
            @Override
            public void onResponse(@NonNull Call<Reports> call, @NonNull Response<Reports> response) {
                if (response.isSuccessful()) {
                    reportCurrentMutableLiveData.postValue(response.body());
                    Log.d("TAG", "onResponse: " + response.body());
                } else {
                    reportCurrentMutableLiveData.postValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Reports> call, @NonNull Throwable t) {
                reportCurrentMutableLiveData.postValue(null);
            }
        });
    }

    public void postReportVio(int user_id, int violations_id, MultipartBody.Part requestBody) {
        Routes routes = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        Call<ReportsVio> call = routes.sendReportVio(user_id, violations_id, requestBody);
        call.enqueue(new Callback<ReportsVio>() {
            @Override
            public void onResponse(@NonNull Call<ReportsVio> call, @NonNull Response<ReportsVio> response) {
                if (response.isSuccessful()) {
                    postReportVio.postValue(response.body());
                    Log.d("TAG", "onResponse: " + response.body());
                } else {
                    postReportVio.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReportsVio> call, @NonNull Throwable t) {
                postReportVio.postValue(null);
            }
        });
    }

    public void postReport(PostReports postReports) {
        Routes routes = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        Call<PostReports> call = routes.sendReport(postReports);
        call.enqueue(new Callback<PostReports>() {
            @Override
            public void onResponse(@NonNull Call<PostReports> call, @NonNull Response<PostReports> response) {
                if (response.isSuccessful()) {
                    postReport.postValue(response.body());
                    Log.d("TAG", "onResponse: " + response.body());
                } else {
                    postReport.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostReports> call, @NonNull Throwable t) {
                postReport.postValue(null);
            }
        });
    }

}

