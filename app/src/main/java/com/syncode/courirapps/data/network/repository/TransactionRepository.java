package com.syncode.courirapps.data.network.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.syncode.courirapps.data.model.MessageOnly;
import com.syncode.courirapps.data.network.api.ApiClient;
import com.syncode.courirapps.data.network.api.ApiInterface;
import com.syncode.courirapps.data.response.TransactionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionRepository {

    private MutableLiveData<TransactionResponse> responseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<MessageOnly> responMessageOnly = new MutableLiveData<>();
    private ApiInterface apiInterface;

    public TransactionRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }


    public MutableLiveData<TransactionResponse> getResponseMutableLiveData(String idCourier) {
        Call<TransactionResponse> transactionResponseCall = apiInterface.loadTransaction(idCourier);
        transactionResponseCall.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(@NonNull Call<TransactionResponse> call, @NonNull Response<TransactionResponse> response) {
                if (response.body() != null) {
                    responseMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<TransactionResponse> call, @NonNull Throwable t) {

            }
        });

        return responseMutableLiveData;
    }


    public MutableLiveData<MessageOnly> requestUpdateStatus(String idTransaction, int status) {
        Call<MessageOnly> requestUpdateStatus = apiInterface.updateStatusTransaction(idTransaction, status);
        requestUpdateStatus.enqueue(new Callback<MessageOnly>() {
            @Override
            public void onResponse(@NonNull Call<MessageOnly> call, @NonNull Response<MessageOnly> response) {
                if (response.body() != null) {
                    responMessageOnly.postValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<MessageOnly> call, @NonNull Throwable t) {

            }
        });

        return responMessageOnly;
    }
}
