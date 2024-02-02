package com.example.biomarket.models;

import com.google.type.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductResume {
    String id, commandId, userId;
    int quantity;

    public ProductResume() {
    }

    public ProductResume(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public List<ProductResume> toProductResume(List<ProductModel> productModelList){
        List<ProductResume> productResumes = new ArrayList<>();
        for (ProductModel product : productModelList){
            ProductResume productResume = new ProductResume(product.getID(), product.getQuantity());
            productResumes.add(productResume);
        }

        return productResumes;
    }

    public ProductModel toProductModel(ProductResume productResume){
        ProductModel productModel = null;

        productModel.setID(productResume.getId());
        productModel.setQuantity(productResume.getQuantity());

        return productModel;
    }
}
