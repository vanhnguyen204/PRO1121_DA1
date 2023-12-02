package com.fpoly.pro1121_da1.model;

public class Notification {
    private int id;
    private String message;
    private String time;

    public Notification(int id, String message, String time) {
        this.id = id;
        this.message = message;
        this.time = time;
    }

    public Notification(String message, String time) {
        this.message = message;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
