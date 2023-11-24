package com.fpoly.pro1121_da1.model;

import java.io.Serializable;

public class Drink implements Serializable {
    private int drinkID;
    private String ingredientID;
    private int voucherID;
    private String name;
    private String typeOfDrink;
    private String dateExpiry;
    private String dateAdd;
    private int price;
    private int quantity;
    private int image;

    public Drink(int drinkID, String ingredientID, int voucherID, String name, String typeOfDrink, String dateExpiry, String dateAdd, int price, int quantity, int image) {
        this.drinkID = drinkID;
        this.ingredientID = ingredientID;
        this.voucherID = voucherID;
        this.name = name;
        this.typeOfDrink = typeOfDrink;
        this.dateExpiry = dateExpiry;
        this.dateAdd = dateAdd;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public Drink(String ingredientID, int voucherID, String name, String typeOfDrink, String dateExpiry, String dateAdd, int price, int quantity, int image) {
        this.ingredientID = ingredientID;
        this.voucherID = voucherID;
        this.name = name;
        this.typeOfDrink = typeOfDrink;
        this.dateExpiry = dateExpiry;
        this.dateAdd = dateAdd;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public int getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(int drinkID) {
        this.drinkID = drinkID;
    }

    public String getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
    }

    public int getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(int voucherID) {
        this.voucherID = voucherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfDrink() {
        return typeOfDrink;
    }

    public void setTypeOfDrink(String typeOfDrink) {
        this.typeOfDrink = typeOfDrink;
    }

    public String getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(String dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
