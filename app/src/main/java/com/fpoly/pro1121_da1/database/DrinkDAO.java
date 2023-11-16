package com.fpoly.pro1121_da1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.pro1121_da1.builder.DrinkBuilder;
import com.fpoly.pro1121_da1.model.Drink;


public class DrinkDAO {
    Context context;
    Dbhelper dbhelper;

    public DrinkDAO(Context context, Dbhelper dbhelper) {
        this.context = context;
        this.dbhelper = dbhelper;
    }

    public boolean insertDrink(Drink drink) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("drink_id", drink.getDrinkID());

        long reslut = sql.insert("Drink", null, values);
        if (reslut  > 0 ){
            Toast.makeText(context, "Thêm đồ uống thành công", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(context, "Thêm đồ uống không thành công", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
