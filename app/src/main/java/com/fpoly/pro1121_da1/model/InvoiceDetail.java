package com.fpoly.pro1121_da1.model;

public class InvoiceDetail {
    private int invoiceDetailID;
    private int drinkID;
    private int invoiceID;
    private int quantityDrink;
    private int priceDrink;
    private String dateExpiry;
    private double quantityIngredient;

    public InvoiceDetail(int invoiceDetailID, int drinkID, int invoiceID, int quantityDrink, int priceDrink, String dateExpiry, double quantityIngredient) {
        this.invoiceDetailID = invoiceDetailID;
        this.drinkID = drinkID;
        this.invoiceID = invoiceID;
        this.quantityDrink = quantityDrink;
        this.priceDrink = priceDrink;
        this.dateExpiry = dateExpiry;
        this.quantityIngredient = quantityIngredient;
    }

    public InvoiceDetail(int drinkID, int invoiceID, int quantityDrink, int priceDrink, String dateExpiry, double quantityIngredient) {
        this.drinkID = drinkID;
        this.invoiceID = invoiceID;
        this.quantityDrink = quantityDrink;
        this.priceDrink = priceDrink;
        this.dateExpiry = dateExpiry;
        this.quantityIngredient = quantityIngredient;
    }

    public int getInvoiceDetailID() {
        return invoiceDetailID;
    }

    public void setInvoiceDetailID(int invoiceDetailID) {
        this.invoiceDetailID = invoiceDetailID;
    }

    public int getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(int drinkID) {
        this.drinkID = drinkID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getQuantityDrink() {
        return quantityDrink;
    }

    public void setQuantityDrink(int quantityDrink) {
        this.quantityDrink = quantityDrink;
    }

    public int getPriceDrink() {
        return priceDrink;
    }

    public void setPriceDrink(int priceDrink) {
        this.priceDrink = priceDrink;
    }

    public String getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(String dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    public double getQuantityIngredient() {
        return quantityIngredient;
    }

    public void setQuantityIngredient(double quantityIngredient) {
        this.quantityIngredient = quantityIngredient;
    }
}
