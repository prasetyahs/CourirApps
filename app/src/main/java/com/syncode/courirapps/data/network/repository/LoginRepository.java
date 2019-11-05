package com.syncode.courirapps.data.network.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.syncode.courirapps.data.network.api.ApiClient;
import com.syncode.courirapps.data.network.api.ApiInterface;
import com.syncode.courirapps.data.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private MutableLiveData<LoginResponse> loginResponseMutableLiveDat = new MutableLiveData<>();
    private ApiInterface apiInterface;


    public LoginRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }


    public MutableLiveData<LoginResponse> login(String username, String password) {
        Call<LoginResponse> loginResponseCall = apiInterface.loginCourier(username, password);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    loginResponseMutableLiveDat.postValue(response.body());
                } else {
                    Log.d(this.getClass().getSimpleName(), "not Found");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(this.getClass().getSimpleName(), t.getMessage());
            }
        });

        return loginResponseMutableLiveDat;
    }
}
