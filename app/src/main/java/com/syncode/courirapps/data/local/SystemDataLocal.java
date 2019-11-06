package com.syncode.courirapps.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.syncode.courirapps.data.model.Courier;


public class SystemDataLocal {

    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String KEY_USER = "User";
    private static final String KEY_STS = "status";
    private Courier courier;


    public SystemDataLocal(Context context, Courier courier) {
        this.context = context;
        this.courier = courier;
    }

    public SystemDataLocal(Context context) {
        this.context = context;
    }

    public void setSessionLogin() {
        sharedPreferences = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", courier.getUsername());
        editor.putString("lname", courier.getFname());
        editor.putString("fname", courier.getLname());
        editor.putString("email", courier.getEmail());
        editor.putString("idcourier", courier.getIdCourier());
        editor.putString("password", courier.getPassword());
        editor.putString("email", courier.getEmail());
        editor.putString("type", courier.getType());
        editor.putBoolean("login", true);
        editor.apply();
    }

    public void editAllSessionLogin(String username, String fname, String lname, String email, String password, String type) {
        sharedPreferences = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("lname", lname);
        editor.putString("fname", fname);
        editor.putString("email", email);
        editor.putString("type", type);
        editor.putBoolean("login", true);
        editor.apply();
    }

    public void editEmailIsVerified(int isVerified) {
        sharedPreferences = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("isverified", isVerified);
        editor.apply();
    }

    public Courier getLoginData() {
        sharedPreferences = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String lname = sharedPreferences.getString("lname", "");
        String fname = sharedPreferences.getString("fname", "");
        String email = sharedPreferences.getString("email", "");
        String idCourier = sharedPreferences.getString("idcourier", "");
        String password = sharedPreferences.getString("password", "");
        String type = sharedPreferences.getString("type", "");
        return new Courier(username, fname, lname, email, type, idCourier, password);
    }

    public void setStatusCourier(boolean status) {
        sharedPreferences = context.getSharedPreferences(KEY_STS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("status", status);
        editor.apply();
    }

    public boolean getStatusCourier() {
        sharedPreferences = context.getSharedPreferences(KEY_STS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("status", false);
    }

    public void destroyStatus() {
        sharedPreferences = context.getSharedPreferences(KEY_STS, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public boolean getCheckLogin() {
        sharedPreferences = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("login", false);
    }


    public void destroySessionLogin() {
        sharedPreferences = context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

}
