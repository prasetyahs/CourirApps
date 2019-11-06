package com.syncode.courirapps.data.model;

public class TrackingModel {


    private String idTransaction, status;
    private double lat, lont, lat2, lot2;

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public double getLat2() {
        return lat2;
    }

    public void setLat2(double lat2) {
        this.lat2 = lat2;
    }

    public double getLot2() {
        return lot2;
    }

    public void setLot2(double lot2) {
        this.lot2 = lot2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLont(double lont) {
        this.lont = lont;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public double getLat() {
        return lat;
    }

    public double getLont() {
        return lont;
    }
}
