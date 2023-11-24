package com.fpoly.pro1121_da1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.Invoice;
import com.fpoly.pro1121_da1.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class InvoiceDAO {
    Context context;
    Dbhelper dbhelper;

    public InvoiceDAO(Context context, Dbhelper dbhelper) {
        this.context = context;
        this.dbhelper = dbhelper;
    }

    public boolean insertInvoice(Invoice invoice) {
        SQLiteDatabase sql = dbhelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", invoice.getUserID());
        values.put("customer_id", invoice.getCustomerID());
        values.put("total_bill", invoice.getTotalBill());
        values.put("drink_id", invoice.getDrinkID());
        values.put("table_id", invoice.getTableID());

        DateTimeFormatter f = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        try {
            LocalDate dateCreate = LocalDate.parse(invoice.getDateCreate(), f);
            values.put("date_created", String.valueOf(dateCreate));
        } catch (DateTimeParseException e) {

        }
        long result = sql.insert("Invoice", null, values);
        if (result > 0) {

            Toast.makeText(context, "Thêm hoá đơn thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Thêm hoá đơn thất bại", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean deleteInvoice(String invoiceID) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        long result = sql.delete("Invoice", "invoice_id = ?", new String[]{invoiceID});
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Invoice> getInvoice(String query, String... agrs) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<Invoice> list = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery(query, agrs);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int getInvoiceID = cursor.getInt(0);
                    String getUserID = cursor.getString(1);

                    int getCustomerID = cursor.getInt(2);
                    String getDrinkID = cursor.getString(3);
                    String getTableID = cursor.getString(4);

                    int getTotalBill = cursor.getInt(5);
                    String getDateCreate = cursor.getString(6);

                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sql.endTransaction();
        }
        return list;

    }

    public ArrayList<Invoice> getAllInvoice() {
        return getInvoice("SELECT * FROM Invoice");
    }

    public Invoice getInvoiceByID(String invoiceID) {
        return getInvoice("SELECT * FROM Invoice WHERE invoice_id = ?", invoiceID).get(0);
    }
}