package com.syncode.courirapps.data.model.direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Direction {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("features")
    @Expose
    private List<FeatureDirection> features = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FeatureDirection> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureDirection> features) {
        this.features = features;
    }

}
