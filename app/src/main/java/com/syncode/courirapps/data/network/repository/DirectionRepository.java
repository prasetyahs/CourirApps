package com.syncode.courirapps.data.network.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.syncode.courirapps.data.model.direction.Direction;
import com.syncode.courirapps.data.network.api.ApiClientDirection;
import com.syncode.courirapps.data.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectionRepository {

    private MutableLiveData<Direction> mutableLiveData = new MutableLiveData<>();
    private ApiInterface apiInterface;


    public DirectionRepository() {
        apiInterface = ApiClientDirection.getClientDirection().create(ApiInterface.class);
    }


    public MutableLiveData<Direction> getDirection(String wayPoints, String mode, String apiKey) {
        Call<Direction> requestDirection = apiInterface.getDirection(wayPoints, mode, apiKey);
        requestDirection.enqueue(new Callback<Direction>() {
            @Override
            public void onResponse(@NonNull Call<Direction> call, @NonNull Response<Direction> response) {
                if (response.body() != null) {
                    mutableLiveData.postValue(response.body());
                } else {
                    System.out.println(this.getClass().getSimpleName() + "null");
                }
                System.out.println(response.raw().request().url());
            }

            @Override
            public void onFailure(@NonNull Call<Direction> call, @NonNull Throwable t) {
                System.out.println(this.getClass().getSimpleName() + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
