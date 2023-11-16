package com.fpoly.pro1121_da1.model;

public class Drink {
    private int drinkID;
    private int ingredientID;
    private int voucherID;
    private String name;
    private String typeOfDrink;
    private String dateExpiry;
    private int price;
    private int quantity;

    public Drink(int drinkID, int ingredientID, int voucherID, String name, String typeOfDrink, String dateExpiry, int price, int quantity) {
        this.drinkID = drinkID;
        this.ingredientID = ingredientID;
        this.voucherID = voucherID;
        this.name = name;
        this.typeOfDrink = typeOfDrink;
        this.dateExpiry = dateExpiry;
        this.price = price;
        this.quantity = quantity;
    }

    public int getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(int drinkID) {
        this.drinkID = drinkID;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
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
}
