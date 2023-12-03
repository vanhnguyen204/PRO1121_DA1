package com.fpoly.pro1121_da1.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.Ingredient;
import com.fpoly.pro1121_da1.model.InvoiceDetail;

import java.util.ArrayList;

public class InvoiceDetailDao {
    Context context;
    Dbhelper dbhelper;

    public InvoiceDetailDao(Context context) {
        this.context = context;
        dbhelper = new Dbhelper(context);
    }

    public boolean insertInvoiceDetail(InvoiceDetail invoiceDetail) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("drink_id", invoiceDetail.getDrinkID());
        values.put("invoice_id", invoiceDetail.getInvoiceID());
        values.put("quantity_drink", invoiceDetail.getQuantityDrink());
        values.put("price_drink", invoiceDetail.getPriceDrink());
        values.put("expiry_price", invoiceDetail.getDateExpiry());
        values.put("quantity_ingredient", invoiceDetail.getQuantityIngredient());
        long result = sql.insert("InvoiceDetail", null, values);
        return result > 0;
    }

    @SuppressLint("Range")
    public ArrayList<InvoiceDetail> getInvoiceDetail(String query, String... arg) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<InvoiceDetail> list = new ArrayList<>();

        sql.beginTransaction();

        try {
            Cursor cursor = sql.rawQuery(query, arg);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int getInvoiceDetailID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("invoice_detail_id")));
                    int getDrinkID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("drink_id")));
                    int getInvoiceID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("invoice_id")));
                    int getQuantityDrink = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity_drink")));
                    int getPriceOfDrink = Integer.parseInt(cursor.getString(cursor.getColumnIndex("price_drink")));
                    String getDateExpiry = cursor.getString(cursor.getColumnIndex("expiry_price"));
                    double getQuantityIngredient = Double.parseDouble(cursor.getString(cursor.getColumnIndex("quantity_ingredient")));
                    InvoiceDetail invoiceDetail = new InvoiceDetail(
                            getInvoiceDetailID,
                            getDrinkID,
                            getInvoiceID,
                            getQuantityDrink,
                            getPriceOfDrink,
                            getDateExpiry,
                            getQuantityIngredient
                    );
                    list.add(invoiceDetail);
                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Error", "Can not receive invoice detail !");
        } finally {
            sql.endTransaction();
        }
        return list;
    }



}
