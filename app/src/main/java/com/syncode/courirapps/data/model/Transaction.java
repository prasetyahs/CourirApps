package com.syncode.courirapps.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Transaction implements Parcelable {

    @SerializedName("fname")
    private String fname;
    @SerializedName("lname")
    private String lname;

    public void setDistance(double distance) {
        this.distance = distance;
    }

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

    @SerializedName("quality")
    private String quality;


    @SerializedName("jumlah_order")
    private int orderAmount;

    @SerializedName("phone")
    private String phone;

    @SerializedName("total_transaksi")
    private int totalTransaction;


    private double distance;


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

    public String getQuality() {
        return quality;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public String getPhone() {
        return phone;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getTotalTransaction() {
        return totalTransaction;
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
        dest.writeString(this.quality);
        dest.writeInt(this.orderAmount);
        dest.writeString(this.phone);
        dest.writeDouble(this.distance);
        dest.writeInt(this.totalTransaction);
    }

    public Transaction() {
    }

    protected Transaction(Parcel in) {
        this.fname = in.readString();
        this.lname = in.readString();
        this.coordinate = in.readString();
        this.street = in.readString();
        this.productName = in.readString();
        this.idOrder = in.readString();
        this.idTransaction = in.readString();
        this.quality = in.readString();
        this.orderAmount = in.readInt();
        this.phone = in.readString();
        this.distance = in.readDouble();
        this.totalTransaction = in.readInt();
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
