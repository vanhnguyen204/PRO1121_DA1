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
import java.util.SplittableRandom;

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
        values.put("user_name", user.getUserName());
        values.put("pass_word", user.getPassWord());
        values.put("full_name", user.getFullName());

        values.put("address", user.getAddress());
        values.put("role", user.getRole());
        values.put("status", user.getStatus());
        values.put("phone_number", user.getPhoneNumber());
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
        values.put("user_name", user.getUserName());
        values.put("pass_word", user.getPassWord());
        values.put("full_name", user.getFullName());

        values.put("address", user.getAddress());
        values.put("role", user.getRole());
        values.put("status", user.getStatus());
        values.put("phone_number", user.getPhoneNumber());
        long result = sql.update("User", values, "user_id = ?", new String[]{user.getUserID()});
        if (result > 0) {
            Toast.makeText(context, messPositive, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, messNegative, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean updatePassWordUser(String user_id,String newPassWord,String messengerPositive,String messengerNegative){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues ctv = new ContentValues();

        ctv.put("pass_word", newPassWord);

        long result = sql.update("User", ctv, "user_id = ?", new String[]{user_id});
        if (result > 0){
            Toast.makeText(context, messengerPositive, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(context, messengerNegative, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean updateInForMation(String id, String fullName, String dayOfBirth, String addRess, String phoneNumber) {
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

        values.put("phone_number", phoneNumber);
        long result = sql.update("User", values, "user_id = ?", new String[]{id});
        if (result > 0) {
            Toast.makeText(context, "Update user thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Update user không thành công", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean deleteUserStaff(String id) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("status", 1);
        long result = sql.update("User", values, "user_id = ?", new String[]{id});
        if (result > 0) {
            Toast.makeText(context, "Xoá user thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Xoá user không thành công", Toast.LENGTH_SHORT).show();
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
            Cursor cursor = sql.rawQuery("SELECT user_name FROM User WHERE user_name = ? AND pass_word = ? AND status = 0", new String[]{userName, passWord});
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

    public ArrayList<User> userArrayList() {
        return getUser("SELECT * FROM User WHERE NOT EXISTS (SELECT 1 FROM CalendarWorkForStaff WHERE CalendarWorkForStaff.user_id = User.user_id )");
    }

    public boolean checkUserName(String userName) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();


        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery("SELECT *  FROM User WHERE user_name = ?", new String[]{userName});
            if (cursor.getCount() > 0) {
                return true;

            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sql.endTransaction();
        }
        return false;

    }
    public boolean checkAccountForgot (String user_id, String userName){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery("SELECT *  FROM User WHERE user_id = ? AND user_name = ?", new String[]{user_id, userName});
            if (cursor.getCount() > 0) {
                return true;

            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {
            Toast.makeText(context, "Err Check Update Pass Word By ID", Toast.LENGTH_SHORT).show();
        } finally {
            sql.endTransaction();
        }
        return false;

    }


}
