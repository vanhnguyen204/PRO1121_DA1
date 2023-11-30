package com.fpoly.pro1121_da1.model;

public class IngredientForDrink {
    private int id;
    private int drink_id;
    private String ingredientID;

    public IngredientForDrink(int id, int drink_id, String ingredientID) {
        this.id = id;
        this.drink_id = drink_id;
        this.ingredientID = ingredientID;
    }

    public IngredientForDrink(int drink_id, String ingredientID) {
        this.drink_id = drink_id;
        this.ingredientID = ingredientID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrink_id() {
        return drink_id;
    }

    public void setDrink_id(int drink_id) {
        this.drink_id = drink_id;
    }

    public String getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
    }
}
