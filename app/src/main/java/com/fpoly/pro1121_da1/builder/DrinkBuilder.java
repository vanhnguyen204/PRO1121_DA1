package com.fpoly.pro1121_da1.builder;

import com.fpoly.pro1121_da1.model.Drink;

public class DrinkBuilder implements Builder{
    private int drinkID;
    private int ingredientID;
    private int voucherID;
    private String name;
    private String typeOfDrink;
    private String dateExpiry;
    private int price;
    private int quantity;
    @Override
    public Builder setDrinkId(int drinkID) {
        this.drinkID = drinkID;
        return this;
    }

    @Override
    public Drink build() {
        return new Drink(drinkID, ingredientID, voucherID,name, typeOfDrink, dateExpiry, price, quantity);
    }

}
