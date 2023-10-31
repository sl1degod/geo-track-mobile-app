package com.sl1degod.kursovaya.Network;

import com.sl1degod.kursovaya.Models.Objects;
import com.sl1degod.kursovaya.Models.ReportPoint;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.Models.UsersProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Routes {

    @GET("users/{id}")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<Users> getUser(@Path("id") String id);

    @GET("users")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Users>> getUsers(@Query("name") String user_login);

    @GET("reports")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Reports>> getAllReports();

    @GET("objects/{id}")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<ReportPoint>> getObject(@Path("id") int id);

    @GET("objects")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Objects>> getObjects();
}
