package com.syncode.courirapps.data.model;

import com.google.gson.annotations.SerializedName;

public class Courier {

    @SerializedName("username")
    private String username;

    @SerializedName("fname")
    private String fname;

    @SerializedName("lname")
    private String lname;

    @SerializedName("email")
    private String email;

    @SerializedName("type")
    private String type;

    @SerializedName("id_courier")
    private String idCourier;

    @SerializedName("password")
    private String password;


    public String getUsername() {
        return username;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getIdCourier() {
        return idCourier;
    }

    public Courier(String username, String fname, String lname, String email, String type, String idCourier, String password) {
        this.username = username;
        this.fname = fname;
        this.password = password;
        this.lname = lname;
        this.email = email;
        this.type = type;
        this.idCourier = idCourier;
    }

    public String getPassword() {
        return password;
    }
}

