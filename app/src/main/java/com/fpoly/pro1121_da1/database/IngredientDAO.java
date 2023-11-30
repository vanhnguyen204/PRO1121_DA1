package com.fpoly.pro1121_da1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class IngredientDAO {
    Context context;
    Dbhelper dbhelper;

    public IngredientDAO(Context context, Dbhelper dbhelper) {
        this.context = context;
        this.dbhelper = dbhelper;
    }

    public boolean insertIngredient(Ingredient ingredient, String mess1, String mess2) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        DateTimeFormatter f = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        values.put("ingredient_id", ingredient.getIngredientID());
        values.put("name", ingredient.getName());
        try {
            LocalDate dateAdd = LocalDate.parse(ingredient.getDateAdd(), f);
            LocalDate dateExpiry = LocalDate.parse(ingredient.getDateExpiry(), f);
            values.put("date_expiry", String.valueOf(dateExpiry));
            values.put("date_added", String.valueOf(dateAdd));
        } catch (DateTimeParseException e) {
            Toast.makeText(context, "Error format date", Toast.LENGTH_SHORT).show();
        }
        values.put("price", ingredient.getPrice());
        values.put("quantity", ingredient.getQuantity());
        values.put("image", ingredient.getImage());
        values.put("unit", ingredient.getUnit());
        long result = sql.insert("Ingredient", null, values);
        if (result > 0) {
            Toast.makeText(context, mess1, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, mess2, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean updateIngredient(Ingredient ingredient, String mess1, String mess2) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        DateTimeFormatter f = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();

        values.put("name", ingredient.getName());
        try {

            LocalDate dateExpiry = LocalDate.parse(ingredient.getDateExpiry(), f);
            values.put("date_expiry", String.valueOf(dateExpiry));

        } catch (DateTimeParseException ignored) {

        }
        values.put("price", ingredient.getPrice());
        values.put("quantity", ingredient.getQuantity());
        values.put("image", ingredient.getImage());
        values.put("unit", ingredient.getUnit());
        long result = sql.update("Ingredient", values, "ingredient_id = ?", new String[]{ingredient.getIngredientID()});
        if (result > 0) {
            Toast.makeText(context, mess1, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, mess2, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean deleteIngredient(String ingredientID, String mess1, String mess2) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();

        long result = sql.delete("Ingredient", "ingredient_id = ?", new String[]{ingredientID});
        if (result > 0) {
            Toast.makeText(context, mess1, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, mess2, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public ArrayList<Ingredient> getIngredient(String query, String... agrs) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<Ingredient> list = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery(query, agrs);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String getIngredient = cursor.getString(0);
                    String getName = cursor.getString(1);
                    String getDateAdd = cursor.getString(2);
                    String getDateExpiry = cursor.getString(3);
                    int getPrice = cursor.getInt(4);
                    double getQuantity = cursor.getDouble(5);
                    int getImage = cursor.getInt(6);
                    String getUnit= cursor.getString(7);
                    list.add(new Ingredient(getIngredient, getName, getDateAdd, getDateExpiry, getPrice, getQuantity, getImage, getUnit));
                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sql.endTransaction();
        }
        return list;

    }

    public Ingredient getIngredientByID(String ingredientID) {
        ArrayList<Ingredient> list = getIngredient("SELECT * FROM Ingredient WHERE ingredient_id = ?", ingredientID);

        return list.get(0);
    }

    public ArrayList<Ingredient> getAllIngredient() {
        return getIngredient("SELECT * FROM Ingredient");
    }

    public boolean checkDeleteIngredient(String ingredienID) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        String[] projecttion = {"drink_id"};
        String selection = "ingredient_id = ?";
        String[] selectionArgs = {ingredienID};
        Cursor cursor = sql.query("IngredientForDrink", projecttion, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }
}
