package com.fpoly.pro1121_da1.model;

public class Booking {
    private int bookingID;
    private String tableID;
    private String userID;
    private String dayBooking;
    private String nameCustomer;
    private String phoneNumber;
    private String hourBooking;

    public Booking(int bookingID, String tableID, String userID, String dayBooking, String nameCustomer, String phoneNumber, String hourBooking) {
        this.bookingID = bookingID;
        this.tableID = tableID;
        this.userID = userID;
        this.dayBooking = dayBooking;
        this.nameCustomer = nameCustomer;
        this.phoneNumber = phoneNumber;
        this.hourBooking = hourBooking;
    }

    public Booking(String tableID, String userID, String dayBooking, String nameCustomer, String phoneNumber, String hourBooking) {
        this.tableID = tableID;
        this.userID = userID;
        this.dayBooking = dayBooking;
        this.nameCustomer = nameCustomer;
        this.phoneNumber = phoneNumber;
        this.hourBooking = hourBooking;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDayBooking() {
        return dayBooking;
    }

    public void setDayBooking(String dayBooking) {
        this.dayBooking = dayBooking;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHourBooking() {
        return hourBooking;
    }

    public void setHourBooking(String hourBooking) {
        this.hourBooking = hourBooking;
    }
}
