package com.fpoly.pro1121_da1.model;

public class TopDrink {
    private int drinkId;
    private int quantity;

    public TopDrink(int drinkId, int quantity) {
        this.drinkId = drinkId;
        this.quantity = quantity;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
