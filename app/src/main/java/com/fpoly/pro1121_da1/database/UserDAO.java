package com.fpoly.pro1121_da1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.User;
import com.fpoly.pro1121_da1.model.Voucher;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class UserDAO {
    Context context;
    Dbhelper dbhelper;

    public UserDAO(Context context, Dbhelper dbhelper) {
        this.context = context;
        this.dbhelper = dbhelper;
    }

    public boolean insertUser(User user, String messPositive, String messNegative) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        DateTimeFormatter f = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        try {


            LocalDate dateExpiry = LocalDate.parse(user.getDateOfBirth(), f);
            values.put("date_of_birth", String.valueOf(dateExpiry));
        } catch (DateTimeParseException e) {

        }

        values.put("user_id", user.getUserID());
        values.put("calendar_work", user.getCalendarWork());
        values.put("user_name", user.getUserName());
        values.put("pass_word", user.getPassWord());
        values.put("full_name", user.getFullName());

        values.put("address", user.getAddress());
        values.put("role", user.getRole());
        values.put("status", user.getStatus());
        long result = sql.insert("User", null, values);
        if (result > 0) {
            Toast.makeText(context, messPositive, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, messNegative, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean updateUser(User user, String messPositive, String messNegative) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        DateTimeFormatter f = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        try {


            LocalDate dateExpiry = LocalDate.parse(user.getDateOfBirth(), f);
            values.put("date_of_birth", String.valueOf(dateExpiry));
        } catch (DateTimeParseException e) {

        }

        values.put("user_id", user.getUserID());
        values.put("calendar_work", user.getCalendarWork());
        values.put("user_name", user.getUserName());
        values.put("pass_word", user.getPassWord());
        values.put("full_name", user.getFullName());

        values.put("address", user.getAddress());
        values.put("role", user.getRole());
        values.put("status", user.getStatus());
        long result = sql.update("User", values, "user_id = ?", new String[]{user.getUserID()});
        if (result > 0) {
            Toast.makeText(context, messPositive, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, messNegative, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean updateInForMation(String id, String fullName, String dayOfBirth, String addRess, String role) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        DateTimeFormatter f = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        try {

            LocalDate dateExpiry = LocalDate.parse(dayOfBirth, f);
            values.put("date_of_birth", String.valueOf(dateExpiry));
        } catch (DateTimeParseException e) {

        }

        values.put("full_name", fullName);

        values.put("address", addRess);
        values.put("role", role);

        long result = sql.update("User", values, "user_id = ?", new String[]{id});
        if (result > 0) {
            Toast.makeText(context, "Update user thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Update user không thành công", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public boolean deleteUser(User user, String messPositive, String messNegative) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();

        long result = sql.delete("User", "user_id = ?", new String[]{user.getUserID()});
        if (result > 0) {
            Toast.makeText(context, messPositive, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, messNegative, Toast.LENGTH_SHORT).show();
            return false;
        }
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
                    int getCalendarID = cursor.getInt(1);
                    String getUserName = cursor.getString(2);
                    String getPassWord = cursor.getString(3);
                    String getFullName = cursor.getString(4);
                    String getDateOfBirth = cursor.getString(5);
                    String getAddress = cursor.getString(6);
                    String getRole = cursor.getString(7);
                    int getStatus = cursor.getInt(8);
                    list.add(new User(getUserID,
                            getCalendarID,
                            getUserName,
                            getPassWord,
                            getFullName,
                            getDateOfBirth,
                            getAddress,
                            getRole,
                            getStatus
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

    public User getUserByID(String userID) {
        ArrayList<User> list = getUser("SELECT * FROM User WHERE user_id = ?", userID);

        return list.get(0);
    }

    public User getUserUserName(String userName) {
        ArrayList<User> list = getUser("SELECT * FROM User WHERE user_name = ?", userName);

        return list.get(0);
    }

    public ArrayList<User> getAllUser() {
        return getUser("SELECT * FROM User");
    }

    public boolean checkUserLogin(String userName, String passWord) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<User> list = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery("SELECT user_name FROM User WHERE user_name = ? AND pass_word = ?", new String[]{userName, passWord});
            if (cursor.getCount() > 0) {
                return false;
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {
            return true;
        } finally {
            sql.endTransaction();
        }
        return true;

    }


}
