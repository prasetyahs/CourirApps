package com.syncode.courirapps.data.response;

import com.google.gson.annotations.SerializedName;
import com.syncode.courirapps.data.model.Courier;

public class LoginResponse {

    @SerializedName("userdata")
    private Courier courier;

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;


    public LoginResponse(Courier courier, boolean status,String message) {
        this.courier = courier;
        this.status = status;
    }


    public Courier getCourier() {
        return courier;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
