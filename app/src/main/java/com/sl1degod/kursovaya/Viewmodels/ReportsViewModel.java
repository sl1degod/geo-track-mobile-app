package com.sl1degod.kursovaya.Viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.Network.RetrofitInstance;
import com.sl1degod.kursovaya.Network.Routes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsViewModel extends ViewModel {

    MutableLiveData <List<Reports>> listMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Reports>> getListMutableLiveData() {
        return listMutableLiveData;
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

}

