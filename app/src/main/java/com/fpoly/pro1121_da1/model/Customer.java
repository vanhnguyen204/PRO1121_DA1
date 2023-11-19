package com.fpoly.pro1121_da1.model;

public class Customer {
    private int customerID;
    private String fullName;
    private String phoneNumber;

    public Customer(int customerID, String fullName, String phoneNumber) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String fullName, String phoneNumber) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
