package com.fpoly.pro1121_da1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fpoly.pro1121_da1.model.User;

public class UserDAO {
    Context context;
    Dbhelper dbhelper;

    public UserDAO(Context context, Dbhelper dbhelper) {
        this.context = context;
        this.dbhelper = dbhelper;
    }

    public boolean insertUser(User user){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", user.getUserID());
        values.put("calendar_work", user.getCalendarWork());
        values.put("user_name", user.getUserName());
        values.put("pass_word", user.getPassWord());
        values.put("full_name", user.getFullName());
        values.put("date_of_birth", user.getDateOfBirth());
        values.put("address", user.getAddress());
        values.put("role", user.getRole());

        long result = sql.insert("User", null, values);
        if (result > 0 ){
            return true;
        }else {
            return false;
        }
    }
}
