package com.fpoly.pro1121_da1.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.IngredientForDrink;

import java.util.ArrayList;

public class IngredientForDrinkDAO {
    Context context;
    Dbhelper dbhelper;

    public IngredientForDrinkDAO(Context context) {
        this.context = context;
        dbhelper = new Dbhelper(context);

    }

    public boolean insertValues(IngredientForDrink ingredientForDrink) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("drink_id", ingredientForDrink.getDrink_id());
        contentValues.put("ingredient_id", ingredientForDrink.getIngredientID());
        contentValues.put("quantity", ingredientForDrink.getQuantity());
        long result = sql.insert("IngredientForDrink", null, contentValues);
        if (result > 0) {
            Log.d("Success", "Thêm nguyên liệu cho đồ uống thành công");
            return true;
        } else {
            Log.d("Error", "Thêm nguyên liệu cho đồ uống thất bại");
            return false;
        }
    }


    public boolean deleteIngredientForDrink(int drinkId) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        sql.beginTransaction();
        try {
            long result = sql.delete("IngredientForDrink", "drink_id = ? ", new String[]{String.valueOf(drinkId)});
            sql.setTransactionSuccessful();
            if (result > 0) {
                return true;
            } else {
                Log.e("ok", "loi" + result);
                return false;
            }

        } catch (Exception x) {
            x.printStackTrace();
            return false;
        } finally {
            sql.endTransaction();
        }

    }

    public ArrayList<IngredientForDrink> getIngreForDrink(String query, String... agrs) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<IngredientForDrink> list = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery(query, agrs);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int getID = cursor.getInt(0);
                    int getDrinkID = cursor.getInt(1);
                    String getIngreID = cursor.getString(2);
                    double getQuantity = cursor.getDouble(3);
                    list.add(new IngredientForDrink(getID, getDrinkID, getIngreID, getQuantity));
                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sql.endTransaction();
        }
        return list;

    }

    public IngredientForDrink getModelIngreForDrink(int drinkID, String ingreID) {
        return getIngreForDrink("SELECT * FROM IngredientForDrink WHERE drink_id = ? and ingredient_id = ?", new String[]{String.valueOf(drinkID), ingreID}).get(0);
    }

    public IngredientForDrink getIngredientByDrinkID(int drink_id) {
        return getIngreForDrink("SELECT * FROM IngredientForDrink WHERE drink_id = ?", String.valueOf(drink_id)).get(0);
    }
}
