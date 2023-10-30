package com.sl1degod.kursovaya.Viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.Models.UsersProfile;
import com.sl1degod.kursovaya.Network.RetrofitInstance;
import com.sl1degod.kursovaya.Network.Routes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private MutableLiveData<Users> usersData = new MutableLiveData<>();
    public MutableLiveData<Users> getUsersData() {
        return usersData;
    }

    public void getUser(String id) {
        Routes retrofitService = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        Call<Users> call = retrofitService.getUser(id);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                if (response.isSuccessful()) {
                    usersData.postValue(response.body());
                    Log.d("userDATA", String.valueOf(response.body()));
                    Log.d("UserViewModel", "test");
                } else {
                    usersData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                usersData.postValue(null);
            }
        });
    }

}

