package com.syncode.courirapps.data.model;

import com.google.gson.annotations.SerializedName;

public class MessageOnly {

    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public MessageOnly(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
