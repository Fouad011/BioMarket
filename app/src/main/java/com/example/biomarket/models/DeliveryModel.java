package com.example.biomarket.models;

import androidx.annotation.NonNull;

public class DeliveryModel {
    public String id, fullName, cni, mobile, imageUrl;
    public boolean state;


    public DeliveryModel() {
    }

    public DeliveryModel(String fullName, String cni, String mobile, String imageUrl, boolean state) {
        this.fullName = fullName;
        this.cni = cni;
        this.mobile = mobile;
        this.imageUrl = imageUrl;
        this.state = state;
    }



    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @NonNull
    @Override
    public String toString() {
        return "DeliveryModel{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", cni='" + cni + '\'' +
                ", mobile='" + mobile + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", state=" + state +
                '}';
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
