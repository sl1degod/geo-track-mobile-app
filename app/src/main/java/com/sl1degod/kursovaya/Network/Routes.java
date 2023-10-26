package com.sl1degod.kursovaya.Network;

import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Routes {

    @GET("users")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Users>> getAllUsers();

    @GET("users")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Users>> getUsers(@Query("name") String user_login);

    @GET("reports")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Reports>> getAllReports();
}
