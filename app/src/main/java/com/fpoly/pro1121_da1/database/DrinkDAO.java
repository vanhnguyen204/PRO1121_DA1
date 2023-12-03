package com.fpoly.pro1121_da1.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


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
        DateTimeFormatter f = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        try {
            LocalDate dateAdd = LocalDate.parse(drink.getDateAdd(), f);

            LocalDate dateExpiry = LocalDate.parse(drink.getDateExpiry(), f);
            values.put("date_expiry", String.valueOf(dateExpiry));
            values.put("date_add", String.valueOf(dateAdd));
        } catch (DateTimeParseException e) {

        }


        values.put("voucher_id", drink.getVoucherID());
        values.put("name", drink.getName());
        values.put("typeOf_drink", drink.getTypeOfDrink());

        values.put("price", drink.getPrice());
        values.put("quantity", drink.getQuantity());
        values.put("image_drink", drink.getImage());
        values.put("unit", drink.getUnit());
        values.put("status", drink.getStatus());
        long reslut = sql.insert("Drink", null, values);
        if (reslut > 0) {
            Toast.makeText(context, "Thêm đồ uống thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Thêm đồ uống không thành công", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean updateDrink(Drink drink) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        DateTimeFormatter f = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        try {

            LocalDate dateExpiry = LocalDate.parse(drink.getDateExpiry(), f);
            LocalDate dateAdd = LocalDate.parse(drink.getDateAdd(), f);
            values.put("date_expiry", String.valueOf(dateExpiry));
            values.put("date_add", String.valueOf(dateAdd));

        } catch (DateTimeParseException e) {

        }

        values.put("voucher_id", drink.getVoucherID());
        values.put("name", drink.getName());
        values.put("typeOf_drink", drink.getTypeOfDrink());

        values.put("price", drink.getPrice());
        values.put("quantity", drink.getQuantity());
        values.put("image_drink", drink.getImage());
        values.put("unit", drink.getUnit());
        long reslut = sql.update("Drink", values, "drink_id = ?", new String[]{String.valueOf(drink.getDrinkID())});
        if (reslut > 0) {
            Toast.makeText(context, "Update đồ uống thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Update đồ uống không thành công", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean updateQuantityDrink(int drinkID, int quanity) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", quanity);

        long result = sql.update("Drink", values, "drink_id = ?", new String[]{String.valueOf(drinkID)});

        return result > 0;
    }

    public boolean deleteDrink(int drinkID, int status) {

        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", status);
        long result = sql.update("Drink", values, "drink_id = ?", new String[]{String.valueOf(drinkID)});
        if (result > 0) {
            Toast.makeText(context, "Xoá đồ uống thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Xoá đồ uống không thành công", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public ArrayList<Drink> getDrink(String query, String... agrs) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<Drink> list = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery(query, agrs);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int getDrinkID = cursor.getInt(0);

                    int getVoucherID = cursor.getInt(1);
                    String getNameDrink = cursor.getString(2);
                    String getTyofDrink = cursor.getString(3);
                    String getDateAdd = cursor.getString(4);
                    String getDateExpiry = cursor.getString(5);

                    int getPrice = cursor.getInt(6);
                    int getQuantity = cursor.getInt(7);
                    int getImage = cursor.getInt(8);
                    String getUnit = cursor.getString(9);
                    int getStatus = cursor.getInt(10);
                    list.add(new Drink(
                            getDrinkID,
                            getVoucherID,
                            getNameDrink,
                            getTyofDrink,
                            getDateExpiry,
                            getDateAdd,
                            getPrice,
                            getQuantity,
                            getImage,
                            getUnit,
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

    public Drink getDrinkByID(String drinkID) {
        ArrayList<Drink> list = getDrink("SELECT * FROM Drink WHERE drink_id = ?", drinkID);

        return list.get(0);
    }

    public ArrayList<Drink> getAllDrink() {
        return getDrink("SELECT * FROM Drink");
    }

    public ArrayList<Ingredient> getIngredientFromDrinkID(int drinkID){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<Ingredient> listIngredient = new ArrayList<>();

        sql.beginTransaction();

        try {
            Cursor cursor = sql.rawQuery("SELECT Ingredient.* " +
                    "FROM IngredientForDrink " +
                    "JOIN Drink ON IngredientForDrink.drink_id = Drink.drink_id " +
                    "JOIN Ingredient ON IngredientForDrink.ingredient_id = Ingredient.ingredient_id" +
                    " WHERE Drink.drink_id = ?", new String[]{String.valueOf(drinkID)});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                  String getIngredient = cursor.getString(0);
                  String getName= cursor.getString(1);
                  String getDateAdd = cursor.getString(2);
                  String getDateExpiry = cursor.getString(3);
                  int getPrice = cursor.getInt(4);
                  double getQuantity = cursor.getDouble(5);
                  int getImage = cursor.getInt(6);
                  String getUnit = cursor.getString(7);
                  Ingredient ingredient = new Ingredient(
                          getIngredient,
                          getName,
                          getDateAdd,
                          getDateExpiry,
                          getPrice,
                          getQuantity,
                          getImage,
                          getUnit
                          );
                  listIngredient.add(ingredient);
                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {
            Toast.makeText(context, "Errrr", Toast.LENGTH_SHORT).show();
        } finally {
            sql.endTransaction();
        }
        return listIngredient;
    }

    public ArrayList<Drink> getDrinkNow(){
        return getDrink("SELECT * FROM Drink WHERE status = 0");
    }

}
