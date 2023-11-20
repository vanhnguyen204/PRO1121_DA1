package com.fpoly.pro1121_da1.model;

public class Ingredient {
    private String ingredientID;
    private String name;
    private String dateAdd;
    private String dateExpiry;
    private int price;
    private Double quantity;
    private int image;

    public Ingredient(String ingredientID, String name, String dateAdd, String dateExpiry, int price, Double quantity, int image) {
        this.ingredientID = ingredientID;
        this.name = name;
        this.dateAdd = dateAdd;
        this.dateExpiry = dateExpiry;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public String getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(String dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
