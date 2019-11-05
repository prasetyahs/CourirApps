package com.syncode.courirapps.data.network.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientDirection {
    private static Retrofit retrofit = null;

    public static Retrofit getClientDirection() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("https://api.geoapify.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
