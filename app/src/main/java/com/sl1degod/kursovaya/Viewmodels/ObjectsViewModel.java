package com.sl1degod.kursovaya.Viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sl1degod.kursovaya.Models.Objects;
import com.sl1degod.kursovaya.Network.RetrofitInstance;
import com.sl1degod.kursovaya.Network.Routes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjectsViewModel extends ViewModel {

    MutableLiveData <List<Objects>> listMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Objects>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void setListMutableLiveData(MutableLiveData<List<Objects>> listMutableLiveData) {
        this.listMutableLiveData = listMutableLiveData;
    }

    public void getObjects() {
        Routes routes = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        Call<List<Objects>> call = routes.getObjects();
        call.enqueue(new Callback<List<Objects>>() {
            @Override
            public void onResponse(@NonNull Call<List<Objects>> call, @NonNull Response<List<Objects>> response) {
                if (response.isSuccessful()) {
                    listMutableLiveData.postValue(response.body());
                    Log.d("TAG", "onResponse: " + response.body());
                } else {
                    listMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Objects>> call, @NonNull Throwable t) {
                listMutableLiveData.postValue(null);
            }
        });
    }

}

