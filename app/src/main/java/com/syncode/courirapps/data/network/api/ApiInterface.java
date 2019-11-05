package com.syncode.courirapps.data.network.api;

import com.syncode.courirapps.data.model.direction.Direction;
import com.syncode.courirapps.data.response.LoginResponse;
import com.syncode.courirapps.data.response.TransactionResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("courier/login")
    Call<LoginResponse> loginCourier(@Field("username") String username, @Field("password") String password);


    @FormUrlEncoded
    @POST("courier/transaction")
    Call<TransactionResponse> loadTransaction(@Field("idCourier") String idCourier);

    @GET("v1/routing?")
    Call<Direction> getDirection(@Query("waypoints") String wayPoints, @Query("mode") String mode, @Query("apiKey") String apiKey);

}
