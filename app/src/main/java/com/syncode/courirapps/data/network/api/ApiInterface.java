package com.syncode.courirapps.data.network.api;

import com.syncode.courirapps.data.model.MessageOnly;
import com.syncode.courirapps.data.model.Transaction;
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
    @POST("courier/singletransaction")
    Call<Transaction> getSingleTransaction(@Field("id_transaction") String idTransaction);

    @FormUrlEncoded
    @POST("courier/transaction")
    Call<TransactionResponse> loadTransaction(@Field("idCourier") String idCourier);

    @GET("v1/routing?")
    Call<Direction> getDirection(@Query("waypoints") String wayPoints, @Query("mode") String mode, @Query("apiKey") String apiKey);

    @FormUrlEncoded
    @POST("transaction/changestatus")
    Call<MessageOnly> updateStatusTransaction(@Field("id_transaksi") String idTransaction, @Field("status_order") int statusOrder);
}
