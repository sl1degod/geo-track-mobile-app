package com.sl1degod.kursovaya.Viewmodels;

import android.util.Log;

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

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<Users>> usersData = new MutableLiveData<>();
    public MutableLiveData<List<Users>> getUsersData() {
        return usersData;
    }

    public void getAllUsers() {
        Routes routes = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        Call<List<Users>> call = routes.getAllUsers();
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(@NonNull Call<List<Users>> call, @NonNull Response<List<Users>> response) {
                if (response.isSuccessful()) {
                    usersData.postValue(response.body());
                    Log.d("TAG", "onResponse: " + response.body());
                } else {
                    usersData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Users>> call, @NonNull Throwable t) {
                usersData.postValue(null);
            }
        });
    }

}

