package com.fpoly.pro1121_da1.model;

public class CalendarWorkForStaff {
   private String CalendarWorkForStaff_ID;
   private String User_ID;
   private int Calender_ID;

    public CalendarWorkForStaff(String calendarWorkForStaff_ID, String user_ID, int calender_ID) {
        CalendarWorkForStaff_ID = calendarWorkForStaff_ID;
        User_ID = user_ID;
        Calender_ID = calender_ID;
    }

    public String getCalendarWorkForStaff_ID() {
        return CalendarWorkForStaff_ID;
    }

    public void setCalendarWorkForStaff_ID(String calendarWorkForStaff_ID) {
        CalendarWorkForStaff_ID = calendarWorkForStaff_ID;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public int getCalender_ID() {
        return Calender_ID;
    }

    public void setCalender_ID(int calender_ID) {
        Calender_ID = calender_ID;
    }
}
