package com.sl1degod.kursovaya.Network;

import android.accounts.AccountManager;
import android.util.Log;

import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Chart;
import com.sl1degod.kursovaya.Models.Elimination;
import com.sl1degod.kursovaya.Models.Login;
import com.sl1degod.kursovaya.Models.Objects;
import com.sl1degod.kursovaya.Models.PostReports;
import com.sl1degod.kursovaya.Models.ReportPoint;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.ReportsVio;
import com.sl1degod.kursovaya.Models.Token;
import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.Models.Violations;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Routes {

    String token = App.getInstance().getToken();

    @POST("login")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<Token> login(@Body Login login);

    @GET("users/{id}")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<Users> getUser(@Path("id") String id, @Header("Authorization") String USER_TOKEN);

    @GET("users")
    @Headers({"Accept:application/json", "Content-Type:application/json",
            "Authorization: Bearer {token}"})
    Call<List<Users>> getUsers(@Query("name") String user_login);

    @GET("/reports")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Reports>> getAllReports(@Header("Authorization") String USER_TOKEN);

    @GET("reports/admin/{id}")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Reports>> getAdminReports(@Path("id") int id, @Header("Authorization") String USER_TOKEN);

    @GET("objects/{id}")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<ReportPoint>> getObject(@Path("id") int id, @Header("Authorization") String USER_TOKEN);

    @GET("objects")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Objects>> getObjects(@Header("Authorization") String USER_TOKEN);

    @GET("violations")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Violations>> getViolations(@Header("Authorization") String USER_TOKEN);

    @GET("violationsChar")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Chart>> getCharViolations(@Header("Authorization") String USER_TOKEN);

    @GET("elimination")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Elimination>> getEliminations(@Header("Authorization") String USER_TOKEN);

    @Multipart
    @POST("reportsvio")
    Call<ReportsVio> sendReportVio(@Part("user_id") int user_id,
                                   @Part("violations_id") int violations_id,
                                   @Part("elimination_id") int types_id,
                                   @Part MultipartBody.Part image,
                                   @Header("Authorization") String USER_TOKEN);

    @POST("reports")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<PostReports> sendReport(@Body PostReports postReports, @Header("Authorization") String USER_TOKEN);
}
