package com.example.biomarket.models;

import java.io.Serializable;

public class LinkModel implements Serializable {
    String id, deliveryId, userId;
    boolean validity;

    public LinkModel() {
    }

    public LinkModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public boolean isValidity() {
        return validity;
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return "LinkModel{" +
                "id='" + id + '\'' +
                ", deliveryId='" + deliveryId + '\'' +
                ", userId='" + userId + '\'' +
                ", validity=" + validity +
                '}';
    }
}
