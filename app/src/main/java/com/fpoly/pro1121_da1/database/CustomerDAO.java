package com.fpoly.pro1121_da1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.Customer;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;

public class CustomerDAO {
    Context context;
    Dbhelper dbhelper;

    public CustomerDAO(Context context, Dbhelper dbhelper) {
        this.context = context;
        this.dbhelper = dbhelper;
    }

    public boolean insertCustomer(Customer customer, String messPositive, String messNegative) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("full_name", customer.getFullName());
        values.put("phone_number", customer.getPhoneNumber());

        long result = sql.insert("Customer", null, values);

        if (result > 0) {
            Toast.makeText(context, messPositive, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, messNegative, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean updateCustomer(Customer customer, String messPositive, String messNegative) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("full_name", customer.getFullName());
        values.put("phone_number", customer.getPhoneNumber());

        long result = sql.update("Customer", values, "customer_id = ?", new String[]{String.valueOf(customer.getCustomerID())});

        if (result > 0) {
            Toast.makeText(context, messPositive, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, messNegative, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean deleteCustomer(Customer customer, String messPositive, String messNegative) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();

        long result = sql.delete("Customer", "customer_id = ?", new String[]{String.valueOf(customer.getCustomerID())});

        if (result > 0) {
            Toast.makeText(context, messPositive, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, messNegative, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public ArrayList<Customer> getCustomer(String query, String... agrs) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<Customer> list = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery(query, agrs);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int getCustomerID = cursor.getInt(0);
                    String getFullName = cursor.getString(1);
                    String getPhoneNumber = cursor.getString(2);

                    list.add(new Customer(
                            getCustomerID,
                            getFullName,
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

    public Customer getCustomerByID(int customerID) {
        ArrayList<Customer> list = getCustomer("SELECT * FROM Customer WHERE customer_id", String.valueOf(customerID));
        return list.get(0);
    }
}
