package com.fpoly.pro1121_da1.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userID;
    private int calendarWork;
    private String userName;
    private String passWord;
    private String fullName;
    private String dateOfBirth;
    private String address;
    private String role;
    private int status;


    public User(String userID, int calendarWork, String userName, String passWord, String fullName, String dateOfBirth, String address, String role, int status) {
        this.userID = userID;
        this.calendarWork = calendarWork;
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.role = role;
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getCalendarWork() {
        return calendarWork;
    }

    public void setCalendarWork(int calendarWork) {
        this.calendarWork = calendarWork;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
