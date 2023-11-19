package com.fpoly.pro1121_da1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {

    public Dbhelper(@Nullable Context context) {
        super(context, "drink.db", null, 3);
    }

    String createCalendarWork
            = "CREATE TABLE Calendar(calendar_id INTEGER PRIMARY KEY AUTOINCREMENT, date_work DATE NOT NULL, shift_work TEXT NOT NULL, time_start TEXT NOT NULL, time_end TEXT NOT NULL)";
    String createCustomer
            = "CREATE TABLE Customer(customer_id INTEGER PRIMARY KEY AUTOINCREMENT, full_name TEXT, phone_number TEXT)";
    String createUser
            = "CREATE TABLE User(user_id TEXT PRIMARY KEY, calendar_work REFERENCES Calendar(calendar_id), user_name TEXT, pass_word TEXT, full_name TEXT, date_of_birth DATE, address TEXT, role TEXT)";
    String createInvoice
            = "CREATE TABLE Invoice(invoice_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id REFERENCES User(user_id), customer_id REFERENCES Customer(customer_id), total_bill INTEGER, date_created DATE)";
    String createIngredient
            = "CREATE TABLE Ingredient(ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT, date_added DATE, date_expiry DATE, price INTEGER, quantity REAL)";
    String createVoucher
            = "CREATE TABLE Voucher(voucher_id INTEGER PRIMARY KEY, price_reduce INTEGER, date_expiry DATE, condition_reduce INTEGER)";
    String createDrink
            = "CREATE TABLE Drink(drink_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " ingredient_id REFERENCES Ingredient(ingredient_id)," +
            " voucher_id REFERENCES Voucher(voucher_id)," +
            " name TEXT," +
            " typeOf_drink TEXT," +
            " date_add DATE," +
            " date_expiry DATE," +
            " price INTEGER," +
            " quantity INTEGER)";
    String createInvoiceDetail
            = "CREATE TABLE InvoiceDetail(invoice_detail_id INTEGER PRIMARY KEY AUTOINCREMENT, drink_id REFERENCES Drink(drink_id), invoice_id REFERENCES Invoice(invoice_id), price_drink INTEGER, quantity_drink INTEGER)";
    String insertAdmin = "INSERT INTO User VALUES('033204003937', 1, 'admin', 'admin', 'Nguyễn Việt Anh', '28/08/2004', 'Hưng Yên','admin' )";
String insertDrink =
        "INSERT INTO Drink VALUES(1, 1, 1, 'Trà sữa', 'Pha chế', '18/11/2023', '18/11/2025', 50000, 1),(2, 1, 1, 'Trà sữa', 'Pha chế', '18/11/2023', '18/11/2025', 50000, 1)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createCalendarWork);
        db.execSQL(createUser);
        db.execSQL(createCustomer);
        db.execSQL(createInvoice);
        db.execSQL(createIngredient);
        db.execSQL(createVoucher);
        db.execSQL(createDrink);
        db.execSQL(createInvoiceDetail);
        db.execSQL(insertAdmin);
        db.execSQL(insertDrink);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
