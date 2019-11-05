package com.syncode.courirapps.data.model.direction;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeatureDirection {

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("type")
    @Expose
    private String type;


    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
