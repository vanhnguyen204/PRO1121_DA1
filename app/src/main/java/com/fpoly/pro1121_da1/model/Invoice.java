package com.fpoly.pro1121_da1.model;

public class Invoice {
    private int invoiceID;
    private String userID;
    private int customerID;
    private String drinkID;
    private String tableID;
    private int totalBill;
    private String dateCreate;

    public Invoice(int invoiceID, String userID, int customerID, String drinkID, String tableID, int totalBill, String dateCreate) {
        this.invoiceID = invoiceID;
        this.userID = userID;
        this.customerID = customerID;
        this.drinkID = drinkID;
        this.tableID = tableID;
        this.totalBill = totalBill;
        this.dateCreate = dateCreate;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(String drinkID) {
        this.drinkID = drinkID;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }
}

