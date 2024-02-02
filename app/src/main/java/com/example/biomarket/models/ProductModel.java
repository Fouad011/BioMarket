package com.example.biomarket.models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private String ID, title, description, category, classification;
    Double price;
    private Boolean visible, inStock;
    private String imageUrl;
    int quantityInStock, quantity;

    public ProductModel() {
    }

    public ProductModel(String title, String description, Double price, String category, String classification, Boolean visible, Boolean inStock, String imageUrl, int quantityInStock) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.classification = classification;
        this.visible = visible;
        this.inStock = inStock;
        this.imageUrl = imageUrl;
        this.quantityInStock = quantityInStock;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }




    @Override
    public String toString() {
        return "ProductModel{" +
                "ID='" + ID + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", classification='" + classification + '\'' +
                ", price=" + price +
                ", visible=" + visible +
                ", inStock=" + inStock +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
