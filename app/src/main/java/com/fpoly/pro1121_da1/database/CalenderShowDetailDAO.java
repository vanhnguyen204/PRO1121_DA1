package com.fpoly.pro1121_da1.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.CalendarWorkForStaff;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;

public class CalenderShowDetailDAO {
    Activity activity;
    Dbhelper dbhelper;


    public CalenderShowDetailDAO(Activity activity, Dbhelper dbhelper) {
        this.activity = activity;
        this.dbhelper = dbhelper;
    }

    public boolean insertCalendarWorkForStaff(CalendarWorkForStaff calendarWorkForStaff, String MessengerPositive, String MessengerNegative){
        SQLiteDatabase sqL = dbhelper.getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("calendarworkforstaff_id",calendarWorkForStaff.getCalendarWorkForStaff_ID());
        ctv.put("uer_id",calendarWorkForStaff.getUser_ID());
        ctv.put("clendarWork_id",calendarWorkForStaff.getCalender_ID());

        long result = sqL.insert("CalendarWorkForStaff",null,ctv);
        if (result > 0){
            Toast.makeText(activity, MessengerPositive, Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(activity, MessengerNegative, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public boolean updateCalenderWorkForStaff(CalendarWorkForStaff calendarWorkForStaff, String messengerPositive,String messengerNegative){
        SQLiteDatabase sqL = dbhelper.getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("calendarworkforstaff_id",calendarWorkForStaff.getCalendarWorkForStaff_ID());
        ctv.put("uer_id",calendarWorkForStaff.getUser_ID());
        ctv.put("clendarWork_id",calendarWorkForStaff.getCalender_ID());

        long result = sqL.update("CalendarWorkForStaff",ctv,"calendarworkforstaff_id = ?",new String[]{String.valueOf(calendarWorkForStaff.getCalendarWorkForStaff_ID())});
        if (result > 0){
            Toast.makeText(activity, messengerPositive, Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(activity, messengerNegative, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    boolean deleteCalendarWorkForStaff(CalendarWorkForStaff calendarWorkForStaff, String MessengerPositive, String MessengerNegative){
        SQLiteDatabase sqL = dbhelper.getWritableDatabase();



        long resul = sqL.delete("CalendarWorkForStaff","calendarworkforstaff_id = ?",new String[]{String.valueOf(calendarWorkForStaff.getCalendarWorkForStaff_ID())});
        if (resul > 0){
            Toast.makeText(activity, MessengerPositive, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(activity, MessengerPositive, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public ArrayList<CalendarWorkForStaff> getCalendarWorkForStaff(String query, String... agrs){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<CalendarWorkForStaff> list = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery(query,agrs);
            if (cursor.getCount() >0){
                cursor.moveToFirst();
                do {
                    String calendarWorkForStaff = cursor.getString(0);
                    String UserID = cursor.getString(1);
                    int calendarWork = cursor.getInt(2);

                    list.add(new CalendarWorkForStaff(calendarWorkForStaff,UserID,calendarWork));

                }while (cursor.moveToNext());
            }

        }catch (Exception e){

        }finally {
            sql.endTransaction();
        }
        return list;
    }
    public ArrayList<CalendarWorkForStaff> getAllCalendarWorkForStaff(){
        return getCalendarWorkForStaff("SELECT * FROM CalendarWorkForStaff");
    }

        public ArrayList<User> getUser(String query, String... agrs) {
            SQLiteDatabase sql = dbhelper.getWritableDatabase();
            ArrayList<User> list = new ArrayList<>();

            sql.beginTransaction();
            try {
                Cursor cursor = sql.rawQuery(query, agrs);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    do {
                        String getUserID = cursor.getString(0);
                        String getUserName = cursor.getString(1);
                        String getPassWord = cursor.getString(2);
                        String getFullName = cursor.getString(3);
                        String getDateOfBirth = cursor.getString(4);
                        String getAddress = cursor.getString(5);
                        String getRole = cursor.getString(6);
                        int getStatus = cursor.getInt(7);

                        String getPhoneNumber = cursor.getString(8);
                        list.add(new User(getUserID,
                                getUserName,
                                getPassWord,
                                getFullName,
                                getDateOfBirth,
                                getAddress,
                                getRole,
                                getStatus,
                                getPhoneNumber
                        ));
                    } while (cursor.moveToNext());
                }
                sql.setTransactionSuccessful();
            } catch (Exception e) {

            } finally {
                sql.endTransaction();
            }
            return list;

        }


        public ArrayList<User> getUserWithShiftWork(int shiftWork, String dateWork){
            return getUser("SELECT User.* FROM CalendarWorkForStaff JOHN User ON User.user_id  =  CalendarWorkForStaff.uer_id JOHN Calendar ON Calendar.calendar_id =? CalendarWorkForStaff.clendarWork_id WHERE Calendar.shift_work = ? and Calendar.date_work = ?",String.valueOf(shiftWork), dateWork);
        }
}
