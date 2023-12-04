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
        values.put("expiry_drink", invoiceDetail.getDateExpiry());

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
                    int getInvoiceDetailID = cursor.getInt(0);
                    int getDrinkID =  cursor.getInt(1);
                    int getInvoiceID =  cursor.getInt(2);
                    int getQuantityDrink =  cursor.getInt(3);
                    int getPriceOfDrink =  cursor.getInt(4);
                    String getDateExpiry = cursor.getString(5);

                    InvoiceDetail invoiceDetail = new InvoiceDetail(
                            getInvoiceDetailID,
                            getDrinkID,
                            getInvoiceID,
                            getQuantityDrink,
                            getPriceOfDrink,
                            getDateExpiry
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
public ArrayList<InvoiceDetail> getInvoiceDetailByInvoiceID(int invoiceID){
        return getInvoiceDetail("SELECT * FROM InvoiceDetail WHERE invoice_id = ?", String.valueOf(invoiceID));
}
}
