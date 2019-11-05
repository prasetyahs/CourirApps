package com.syncode.courirapps.data.network.repository;


import androidx.lifecycle.MutableLiveData;

import com.syncode.courirapps.data.network.api.ApiClient;
import com.syncode.courirapps.data.network.api.ApiInterface;
import com.syncode.courirapps.data.response.TransactionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionRepository {

    private MutableLiveData<TransactionResponse> responseMutableLiveData = new MutableLiveData<>();
    private ApiInterface apiInterface;

    public TransactionRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }


    public MutableLiveData<TransactionResponse> getResponseMutableLiveData(String idCourier) {
        Call<TransactionResponse> transactionResponseCall = apiInterface.loadTransaction(idCourier);
        transactionResponseCall.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if (response.body() != null) {
                    responseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {

            }
        });

        return  responseMutableLiveData;
    }
}
