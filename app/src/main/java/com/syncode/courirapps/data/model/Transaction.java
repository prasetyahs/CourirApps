package com.syncode.courirapps.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Transaction implements Parcelable {

    @SerializedName("fname")
    private String fname;
    @SerializedName("lname")
    private String lname;

    @SerializedName("kordinat")
    private String coordinate;

    @SerializedName("street")
    private String street;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("id_order")
    private String idOrder;

    @SerializedName("id_transaksi")
    private String idTransaction;


    private double distance;

    public Transaction(String fname, String lname, String coordinate, String street, String productName, String idOrder, String idTransaction) {
        this.fname = fname;
        this.lname = lname;
        this.coordinate = coordinate;
        this.street = street;
        this.productName = productName;
        this.idOrder = idOrder;
        this.idTransaction = idTransaction;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public String getStreet() {
        return street;
    }

    public String getProductName() {
        return productName;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getIdTransaction() {
        return idTransaction;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fname);
        dest.writeString(this.lname);
        dest.writeString(this.coordinate);
        dest.writeString(this.street);
        dest.writeString(this.productName);
        dest.writeString(this.idOrder);
        dest.writeString(this.idTransaction);
        dest.writeDouble(this.distance);
    }

    protected Transaction(Parcel in) {
        this.fname = in.readString();
        this.lname = in.readString();
        this.coordinate = in.readString();
        this.street = in.readString();
        this.productName = in.readString();
        this.idOrder = in.readString();
        this.idTransaction = in.readString();
        this.distance = in.readDouble();
    }

    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
