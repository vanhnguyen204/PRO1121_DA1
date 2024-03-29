package com.fpoly.pro1121_da1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.CalenderWork;

import java.util.ArrayList;
import java.util.Calendar;

public class CalenderDAO {
    Context context;
    Dbhelper dbhelper;

    public CalenderDAO(Context context, Dbhelper dbhelper) {
        this.context = context;
        this.dbhelper = dbhelper;
    }

    public boolean insertCalender(CalenderWork calenderWork) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("date_work", calenderWork.getDayofWork());
        ctv.put("shift_work", calenderWork.getShiftWork());

        long result = sql.insert("Calendar", "null", ctv);
        if (result > 1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean updateCalender(CalenderWork calenderWork, String messengerPositive,String messengerNegative) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues ctv = new ContentValues();


        ctv.put("date_work", calenderWork.getDayofWork());
        ctv.put("shift_work", calenderWork.getShiftWork());

        long result = sql.update("Calendar",ctv,"calendar_id = ?",new String[]{String.valueOf(calenderWork.getId_calender())} );

        if (result > 0) {
            Toast.makeText(context, messengerPositive, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, messengerNegative, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public boolean deleteCalender(int CalenderID, String messengerPositive, String messengerNegative) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();


        long result = sql.delete("Calendar","calendar_id = ?",new String[]{String.valueOf(CalenderID)});
        if (result>0){
            Toast.makeText(context, messengerPositive, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, messengerNegative, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public ArrayList<CalenderWork> getCalender(String query, String... agrs) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<CalenderWork> list = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor  = sql.rawQuery(query,agrs);
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    int id_Calender = cursor.getInt(0);
                    String dateWork = cursor.getString(1);
                    int shiftWork = cursor.getInt(2);

                    CalenderWork calenderWork = new CalenderWork(id_Calender,dateWork,shiftWork);
                    list.add(calenderWork);


                }while (cursor.moveToNext());
            }

        }catch (Exception e){

        }finally {
            sql.endTransaction();
        }
        return list;
    }
    public CalenderWork getCalenderByID (int CalenderID){
        ArrayList<CalenderWork> list = getCalender("SELECT * FROM Calendar WHERE calendar_id = ?",String.valueOf(CalenderID));
        return list.get(0);
    }

    public ArrayList<CalenderWork> getAllCalendar (){
        return getCalender("SELECT * FROM Calendar");
    }

    public CalenderWork getIDCalendarByShiftWorkAndDateWork (int ShiftWork,String DateWork){
        ArrayList<CalenderWork> list = getCalender("SELECT * FROM Calendar WHERE  date_work = ? AND shift_work = ?",DateWork,String.valueOf(ShiftWork));
        return list.get(0);
    }

    public ArrayList<CalenderWork> getAllCalendarByDay (String day){
        return getCalender("SELECT * FROM Calendar WHERE date_work = ?", day);
    }


    public ArrayList<CalenderWork> getAllCanlendarOfUser(String userID){
        return getCalender(
                "SELECT Calendar.* " +
                        "From CalendarWorkForStaff" +
                        " JOIN Calendar ON CalendarWorkForStaff.calendar_id = Calendar.calendar_id " +
                        "JOIN User ON User.user_id = CalendarWorkForStaff.user_id" +
                        " WHERE User.user_id = ?"
                , userID
        );
    }
    public boolean checkDayAdd(String query) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        sql.beginTransaction();
        try {
            Cursor cursor  = sql.rawQuery("SELECT * FROM Calendar WHERE date_work = ? ",new String[]{query});
            return cursor.getCount() > 0;

        }catch (Exception e){

        }finally {
            sql.endTransaction();
        }
        return false;
    }
}
