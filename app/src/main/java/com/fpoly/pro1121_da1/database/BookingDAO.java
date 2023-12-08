package com.fpoly.pro1121_da1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.pro1121_da1.model.Booking;
import com.fpoly.pro1121_da1.model.Drink;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class BookingDAO {
    Context context;
    Dbhelper dbhelper;

    public BookingDAO(Context context) {
        this.context = context;
        dbhelper = new Dbhelper(context);

    }

    public boolean insertBooking(Booking booking) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("table_id", booking.getTableID());
        values.put("user_id", booking.getUserID());
        values.put("day_booking", booking.getDayBooking());
        values.put("hour_booking", booking.getHourBooking());
        values.put("name_customer", booking.getNameCustomer());
        values.put("phone_number", booking.getPhoneNumber());

        long result = sql.insert("Booking", null, values);
        return result > 0;
    }


    public boolean cancelBooking(String tableID, String nameCustomer, String phoneNumber) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        long result
                = sql.delete("Booking",
                "table_id = ?  AND phone_number = ?",
                new String[]{tableID, nameCustomer, phoneNumber});
        return result > 0;
    }

    public boolean deleteBookingByTableID(String tableID) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        long result
                = sql.delete("Booking",
                "table_id = ?",
                new String[]{tableID});
        return result > 0;
    }

    public ArrayList<Booking> getBooking(String query, String... agrs) {
        ArrayList<Booking> bookingArrayList = new ArrayList<>();
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery(query, agrs);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int getBookingID = cursor.getInt(0);
                    String getTableID = cursor.getString(1);
                    String getUserID = cursor.getString(2);
                    String getDayBooking = cursor.getString(3);
                    String getNameCustomer = cursor.getString(4);
                    String getPhoneNumber = cursor.getString(5);
                    String getHourBooking = cursor.getString(6);
                    bookingArrayList.add(new Booking(getBookingID, getTableID, getUserID, getDayBooking, getNameCustomer, getPhoneNumber, getHourBooking));

                } while (cursor.moveToNext());
            }

            sql.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sql.endTransaction();
        }
        return bookingArrayList;
    }

    public ArrayList<Booking> getBookingByTableIDandDay(String tableID, String dayBooking) {
        return getBooking("SELECT * FROM Booking WHERE table_id = ? and day_booking = ?", tableID, dayBooking);
    }

    public boolean checkBooking(String tableID,String dayBooking, String phoneNumber, String hourBooking) {
        return getBooking("SELECT * FROM Booking WHERE table_id = ? AND day_booking = ? AND phone_number = ? AND hour_booking = ?", tableID, dayBooking, phoneNumber, hourBooking).size() > 0;
    }

}
