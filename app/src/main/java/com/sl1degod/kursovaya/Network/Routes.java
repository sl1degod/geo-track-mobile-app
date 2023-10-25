package com.sl1degod.kursovaya.Network;

import com.sl1degod.kursovaya.Models.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Routes {

    @GET("users")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Users>> getAllUsers();
}
