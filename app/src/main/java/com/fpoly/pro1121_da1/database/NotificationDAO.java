package com.fpoly.pro1121_da1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.pro1121_da1.model.Notification;
import com.fpoly.pro1121_da1.model.User;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class NotificationDAO {
    Context context;
    Dbhelper dbhelper;

    public NotificationDAO(Context context) {
        this.context = context;
        dbhelper = new Dbhelper(context);
    }

    public boolean insertNotifi(Notification notification) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String strDate= formatter.format(date);
        values.put("time", strDate);
        values.put("message", notification.getMessage());

        long result = sql.insert("Notification", null, values);

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean deleteNotification(String message){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        long result = sql.delete("Notification", "message = ?", new String[]{message});

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean deleteAll(){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
       return sql.delete("Notification",null, null) > 0;

    }

    public ArrayList<Notification> getNotification(String query, String... agrs) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<Notification> list = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery(query, agrs);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int getNotify = cursor.getInt(0);
                    String getMessage = cursor.getString(1);
                    String getTime = cursor.getString(2);
                    list.add(new Notification(getNotify, getMessage, getTime));
                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sql.endTransaction();
        }
        return list;
    }
    public ArrayList<Notification> getAllNotification(){
        return getNotification("SELECT * FROM Notification");
    }
}
