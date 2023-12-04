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
            = "CREATE TABLE Calendar(calendar_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "date_work DATE NOT NULL," +
            "shift_work INTEGER NOT NULL)";
    String createIngredientForDrink = "CREATE TABLE IngredientForDrink(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " drink_id INTEGER, " +
            "ingredient_id TEXT," +
            "quantity REAL," +
            "FOREIGN KEY (ingredient_id) REFERENCES Ingredient(ingredient_id) ON DELETE CASCADE," +
            "FOREIGN KEY (drink_id) REFERENCES Drink(drink_id) ON DELETE CASCADE)";
    String createCustomer
            = "CREATE TABLE Customer(customer_id INTEGER PRIMARY KEY, " +
            "full_name TEXT," +
            " phone_number TEXT)";
    String createUser
            = "CREATE TABLE User(user_id TEXT PRIMARY KEY," +
            "user_name TEXT, pass_word TEXT," +
            " full_name TEXT, date_of_birth DATE, " +
            "address TEXT," +
            " role TEXT," +
            " status INTEGER," +
            " phone_number TEXT)";
    String createInvoice
            = "CREATE TABLE Invoice(invoice_id INTEGER PRIMARY KEY ," +
            "user_id REFERENCES User(user_id)," +
            "customer_id REFERENCES Customer(customer_id)," +
            "table_id REFERENCES TableDrink(table_id)," +
            "total_bill INTEGER, " +
            "date_created DATE , " +
            "serve TEXT  , " +
            "status TEXT )";

    String createTableInvoiceDetail = "CREATE TABLE InvoiceDetail(" +
            "invoice_detail_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " drink_id REFERENCES Drink(drink_id), " +
            "invoice_id REFERENCES Invoice(invoice_id)," +
            " quantity_drink INTEGER," +
            " price_drink INTEGER," +
            " expiry_drink DATE)";
    String createIngredient
            = "CREATE TABLE Ingredient(ingredient_id TEXT PRIMARY KEY," +
            "name TEXT, " +
            "date_added DATE, " +
            "date_expiry DATE, " +
            "price INTEGER, " +
            "quantity REAL," +
            " image BLOB," +
            " unit TEXT)";
    String createVoucher
            = "CREATE TABLE Voucher(voucher_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " price_reduce INTEGER," +
            " date_expiry DATE)";
    String createDrink
            = "CREATE TABLE Drink(drink_id INTEGER PRIMARY KEY," +
            " voucher_id REFERENCES Voucher(voucher_id)," +
            " name TEXT," +
            " typeOf_drink TEXT," +
            " date_add DATE," +
            " date_expiry DATE," +
            " price INTEGER," +
            "quantity INTEGER ," +
            "image_drink BLOB ," +
            "unit TEXT ," +
            "status INTEGER)";

    String createCalendarWorkForStaff
            = "CREATE TABLE CalendarWorkForStaff(" +
            "calendarworkforstaff_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " user_id REFERENCES User(user_id) ON DELETE CASCADE," +
            " calendar_id REFERENCES Calendar(calendar_id) ON DELETE CASCADE)";
    String createTable = "CREATE TABLE TableDrink(table_id TEXT PRIMARY KEY, status INTEGER)";
    String insertAdmin = "INSERT INTO User VALUES('033204003937', 'admin', 'admin', 'Nguyễn Việt Anh', '28/08/2004', 'Hưng Yên','admin',0, '0339207001')";
    String insertVoucher = "INSERT INTO Voucher VALUES (2023,0, '2099-12-12')";
    String createNotification = "CREATE TABLE Notification(id INTEGER PRIMARY KEY AUTOINCREMENT, time DATE, message TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createCalendarWork);
        db.execSQL(createUser);
        db.execSQL(createIngredientForDrink);
        db.execSQL(createCustomer);
        db.execSQL(createInvoice);
        db.execSQL(createIngredient);
        db.execSQL(createVoucher);
        db.execSQL(createDrink);
        db.execSQL(insertAdmin);
        db.execSQL(insertVoucher);
        db.execSQL(createTable);
        db.execSQL(createCalendarWorkForStaff);
        db.execSQL(createTableInvoiceDetail);
        db.execSQL(createNotification);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
