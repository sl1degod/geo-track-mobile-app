package com.sl1degod.kursovaya.Viewmodels;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.Network.RetrofitInstance;
import com.sl1degod.kursovaya.Network.Routes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<List<Users>> loadUserData = new MutableLiveData<>();

    public MutableLiveData<List<Users>> getLoadUserData() {
        return loadUserData;
    }


    public void getUsers(String user_login) {
        Routes retrofitService = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        Call<List<Users>> call = retrofitService.getUsers(user_login);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(@NonNull Call<List<Users>> call, @NonNull Response<List<Users>> response) {
                if (response.isSuccessful()) {
                    loadUserData.postValue(response.body());
                    Log.d("1230", String.valueOf(response.body()));
                } else {
                    loadUserData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Users>> call, @NonNull Throwable t) {
                loadUserData.postValue(null);
            }
        });
    }


}
