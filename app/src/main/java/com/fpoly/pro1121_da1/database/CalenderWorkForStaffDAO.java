package com.fpoly.pro1121_da1.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.CalendarWorkForStaff;

import java.util.ArrayList;

public class CalenderWorkForStaffDAO {
    Activity activity;
    Dbhelper dbhelper;

    public CalenderWorkForStaffDAO(Activity activity, Dbhelper dbhelper) {
        this.activity = activity;
        this.dbhelper = dbhelper;
    }

    Boolean insertCalenderWorkForStaff(CalendarWorkForStaff calendarWorkForStaff, String messengerPositive, String messengerNegative){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("calendarworkforstaff_id",calendarWorkForStaff.getCalendarWorkForStaff_ID());
        ctv.put("clendarWork_id",calendarWorkForStaff.getCalender_ID());
        ctv.put("uer_id",calendarWorkForStaff.getUser_ID());

        long resul = sql.insert("CalendarWorkForStaff",null,ctv);

        if (resul > 0){
            Toast.makeText(activity, messengerPositive, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(activity, messengerNegative, Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    Boolean updateCalenderWorkForStaff(CalendarWorkForStaff calendarWorkForStaff, String messengerPositive, String messengerNegative){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("calendarworkforstaff_id",calendarWorkForStaff.getCalendarWorkForStaff_ID());
        ctv.put("clendarWork_id",calendarWorkForStaff.getCalender_ID());
        ctv.put("uer_id",calendarWorkForStaff.getUser_ID());

        long resul = sql.update("CalendarWorkForStaff",ctv,"calendarworkforstaff_id = ?",new String[]{String.valueOf(calendarWorkForStaff.getCalendarWorkForStaff_ID())});

        if (resul > 0){
            Toast.makeText(activity, messengerPositive, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(activity, messengerNegative, Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    Boolean deleteCalenderWorkForStaff(CalendarWorkForStaff calendarWorkForStaff, String messengerPositive, String messengerNegative){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("calendarworkforstaff_id",calendarWorkForStaff.getCalendarWorkForStaff_ID());
        ctv.put("clendarWork_id",calendarWorkForStaff.getCalender_ID());
        ctv.put("uer_id",calendarWorkForStaff.getUser_ID());

        long resul = sql.delete("CalendarWorkForStaff","calendarworkforstaff_id = ?",new String[]{String.valueOf(calendarWorkForStaff.getCalendarWorkForStaff_ID())});

        if (resul > 0){
            Toast.makeText(activity, messengerPositive, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(activity, messengerNegative, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public ArrayList<CalendarWorkForStaff> getCalenderForStaff(String query, String... agrs){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<CalendarWorkForStaff> list = new ArrayList<>();
        Cursor cursor = sql.rawQuery(query,agrs);
        try {
            sql.beginTransaction();
            if (cursor.getCount() > 1){
                cursor.moveToFirst();
                do {
                    String getCalenderWorkForStaffID = cursor.getString(0);
                    String getUserID = cursor.getString(1);
                    int getCalenderWorkID   = cursor.getInt(2);

                    CalendarWorkForStaff calendarWorkForStaff = new CalendarWorkForStaff(getCalenderWorkForStaffID,getUserID,getCalenderWorkID);
                    list.add(calendarWorkForStaff);

                }while (cursor.moveToNext());
            }


        }catch (Exception e){

        }


        return list;
    }


}
