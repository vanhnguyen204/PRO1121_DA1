package com.fpoly.pro1121_da1.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    public boolean insertValues(IngredientForDrink ingredientForDrink){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", ingredientForDrink.getId());
        contentValues.put("drink_id", ingredientForDrink.getDrink_id());
        contentValues.put("ingredient_id", ingredientForDrink.getIngredientID());
        long result = sql.insert("IngredientForDrink", null, contentValues);
        if (result > 0){
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteIngredientForDrink(int id){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        long reslut = sql.delete("IngredientForDrink","id = ?", new String[]{String.valueOf(id)});
        if (reslut > 0){
            return true;
        }else {
            return false;
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
                   list.add(new IngredientForDrink(getID, getDrinkID, getIngreID));
                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sql.endTransaction();
        }
        return list;

    }
}
