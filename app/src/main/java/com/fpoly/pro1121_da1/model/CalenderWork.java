package com.fpoly.pro1121_da1.model;

public class CalenderWork {
    private int id_calender;
    private String dayofWork;
    private String shiftWork;

    public CalenderWork(int id_calender, String dayofWork, String shiftWork) {
        this.id_calender = id_calender;
        this.dayofWork = dayofWork;
        this.shiftWork = shiftWork;
    }

    public CalenderWork(String dayofWork, String shiftWork) {
        this.dayofWork = dayofWork;
        this.shiftWork = shiftWork;
    }

    public int getId_calender() {
        return id_calender;
    }

    public void setId_calender(int id_calender) {
        this.id_calender = id_calender;
    }

    public String getDayofWork() {
        return dayofWork;
    }

    public void setDayofWork(String dayofWork) {
        this.dayofWork = dayofWork;
    }

    public String getShiftWork() {
        return shiftWork;
    }

    public void setShiftWork(String shiftWork) {
        this.shiftWork = shiftWork;
    }
}
