package com.sl1degod.kursovaya.Viewmodels;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Login;
import com.sl1degod.kursovaya.Models.Token;
import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.Network.RetrofitInstance;
import com.sl1degod.kursovaya.Network.Routes;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Token> token = new MutableLiveData<>();

    public MutableLiveData<Token> getToken() {
        return token;
    }


    public void login(Login login) {
        Routes retrofitService = RetrofitInstance.getRetrofitInstance().create(Routes.class);
        Call<Token> call = retrofitService.login(login);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                if (response.isSuccessful()) {
                    token.postValue(response.body());
                    App.getInstance().setUser_id(response.body().getUserId());
                    App.getInstance().setToken(response.body().getToken());
                    Log.d("token", String.valueOf(App.getInstance().getToken()));
                    Log.d("userId", String.valueOf(response.body().getUserId()));
                } else {
                    token.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                token.postValue(null);
            }
        });
    }


}
