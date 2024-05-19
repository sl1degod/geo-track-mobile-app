package com.sl1degod.kursovaya.Viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Chart;
import com.sl1degod.kursovaya.Models.Elimination;
import com.sl1degod.kursovaya.Models.Violations;
import com.sl1degod.kursovaya.Network.RetrofitInstance;
import com.sl1degod.kursovaya.Network.Routes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViolationsViewModel extends ViewModel {
    MutableLiveData<List<Violations>> listMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<Elimination>> listElMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<Chart>> charList = new MutableLiveData<>();

    public MutableLiveData<List<Violations>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public MutableLiveData<List<Elimination>> getListElMutableLiveData() {
        return listElMutableLiveData;
    }

    public MutableLiveData<List<Chart>> getCharList() {
        return charList;
    }

    public void getViolations() {
        Routes routes = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        String token = App.getInstance().getToken();
        Call<List<Violations>> call = routes.getViolations("Bearer " + token);
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

    public void getCharViolations() {
        Routes routes = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        String token = App.getInstance().getToken();
        Call<List<Chart>> call = routes.getCharViolations("Bearer " + token);
        call.enqueue(new Callback<List<Chart>>() {
            @Override
            public void onResponse(@NonNull Call<List<Chart>> call, @NonNull Response<List<Chart>> response) {
                if (response.isSuccessful()) {
                    charList.postValue(response.body());
                    Log.d("TAG", "onResponse: " + response.body());
                } else {
                    charList.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Chart>> call, @NonNull Throwable t) {
                charList.postValue(null);
            }
        });
    }

    public void getEliminations() {
        Routes routes = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        String token = App.getInstance().getToken();
        Call<List<Elimination>> call = routes.getEliminations("Bearer " + token);
        call.enqueue(new Callback<List<Elimination>>() {
            @Override
            public void onResponse(@NonNull Call<List<Elimination>> call, @NonNull Response<List<Elimination>> response) {
                if (response.isSuccessful()) {
                    listElMutableLiveData.postValue(response.body());
                    Log.d("TAG", "onResponse: " + response.body());
                } else {
                    listElMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Elimination>> call, @NonNull Throwable t) {
                listElMutableLiveData.postValue(null);
            }
        });
    }

}

