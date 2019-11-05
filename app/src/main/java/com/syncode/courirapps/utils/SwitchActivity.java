package com.syncode.courirapps.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.syncode.courirapps.data.model.Transaction;

import java.util.ArrayList;

public class SwitchActivity {


    public static void mainSwitch(Context from, Class to) {
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
    }

    public static void mainSwitch(Context from, Class to, String value, String key) {
        Intent intent = new Intent(from, to);
        intent.putExtra(key, value);
        from.startActivity(intent);
    }

    public static void mainSwitch(Context from, Class to, String key2, ArrayList<Transaction> parcelable) {
        Intent intent = new Intent(from, to);
        intent.putParcelableArrayListExtra(key2, parcelable);
        from.startActivity(intent);
    }

    public static void mainSwitch(Context from, Class to, String key1, String key2, String key3, Parcelable parcelable, String value, ArrayList<Transaction> parceList) {
        Intent intent = new Intent(from, to);
        intent.putExtra(key1, parcelable);
        intent.putExtra(key3, parceList);
        intent.putExtra(key2, value);
        from.startActivity(intent);
    }

    public static void mainSwitch(Context from, Class to, Parcelable parcel, String key) {
        Intent intent = new Intent(from, to);
        intent.putExtra(key, parcel);
        from.startActivity(intent);
    }


}
