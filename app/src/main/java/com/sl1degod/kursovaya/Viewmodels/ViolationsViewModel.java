package com.sl1degod.kursovaya.Viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sl1degod.kursovaya.Models.Violations;
import com.sl1degod.kursovaya.Network.RetrofitInstance;
import com.sl1degod.kursovaya.Network.Routes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViolationsViewModel extends ViewModel {
    MutableLiveData<List<Violations>> listMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Violations>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void getViolations() {
        Routes routes = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        Call<List<Violations>> call = routes.getViolations();
        call.enqueue(new Callback<List<Violations>>() {
            @Override
            public void onResponse(@NonNull Call<List<Violations>> call, @NonNull Response<List<Violations>> response) {
                if (response.isSuccessful()) {
                    listMutableLiveData.postValue(response.body());
                    Log.d("TAG", "onResponse: " + response.body());
                } else {
                    listMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Violations>> call, @NonNull Throwable t) {
                listMutableLiveData.postValue(null);
            }
        });
    }

}

