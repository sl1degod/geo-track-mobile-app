package com.sl1degod.kursovaya.Network;

import com.sl1degod.kursovaya.Models.Chart;
import com.sl1degod.kursovaya.Models.Objects;
import com.sl1degod.kursovaya.Models.PostReports;
import com.sl1degod.kursovaya.Models.ReportPoint;
import com.sl1degod.kursovaya.Models.Reports;
import com.sl1degod.kursovaya.Models.ReportsVio;
import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.Models.Violations;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("reports/{id}")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Reports>> getAdminReports(@Path("id") int id);

    @GET("objects/{id}")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<ReportPoint>> getObject(@Path("id") int id);

    @GET("objects")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Objects>> getObjects();

    @GET("violations")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Violations>> getViolations();

    @GET("violationsChar")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<Chart>> getCharViolations();

    @Multipart
    @POST("reportsvio")
    Call<ReportsVio> sendReportVio(@Part("user_id") int user_id,
                                   @Part("violations_id") int violations_id,
                                   @Part MultipartBody.Part image);

    @POST("reports")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<PostReports> sendReport(@Body PostReports postReports);
}
