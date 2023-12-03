package com.fpoly.pro1121_da1.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.Invoice;

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
        values.put("invoice_id", invoice.getInvoiceID());
        values.put("user_id", invoice.getUserID());
        values.put("customer_id", invoice.getCustomerID());
        values.put("total_bill", invoice.getTotalBill());
        values.put("drink_id", invoice.getDrinkID());
        values.put("table_id", invoice.getTableID());
        values.put("status", invoice.getStatus());
        DateTimeFormatter f = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        try {
            LocalDate dateCreate = LocalDate.parse(invoice.getDateCreate(), f);
            values.put("date_created", String.valueOf(dateCreate));
        } catch (DateTimeParseException e) {

        }
        values.put("serve", invoice.getServe());
        long result = sql.insert("Invoice", null, values);
        if (result > 0) {

            Toast.makeText(context, "Thêm hoá đơn thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Thêm hoá đơn thất bại", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean updateInvoice(Invoice invoice) {
        SQLiteDatabase sql = dbhelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("invoice_id", invoice.getInvoiceID());
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
        values.put("serve", invoice.getServe());
        values.put("status", invoice.getStatus());
        long result = sql.update("Invoice", values, "invoice_id = ?", new String[]{String.valueOf(invoice.getInvoiceID())});
        if (result > 0) {

            Toast.makeText(context, "Update hoá đơn thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Update hoá đơn thất bại", Toast.LENGTH_SHORT).show();
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
                    String getServe = cursor.getString(7);
                    String getStatus = cursor.getString(8);
                    list.add(new Invoice(getInvoiceID, getUserID, getCustomerID, getDrinkID, getTableID, getTotalBill, getDateCreate, getServe, getStatus));
                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {
            Toast.makeText(context, "Err invoice", Toast.LENGTH_SHORT).show();
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

    public int getRevenue() {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<Integer> doanhThu = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery("SELECT SUM(total_bill) AS DoanhThu FROM Invoice ", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    @SuppressLint("Range") int getDoanhThu = Integer.parseInt(cursor.getString(cursor.getColumnIndex("DoanhThu")));
                    doanhThu.add(getDoanhThu);
                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("ERROR", "Lỗi lấy doanh thu");
        } finally {
            sql.endTransaction();
        }
        return doanhThu.get(0);

    }

    public int countInvoiceExported() {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<Integer> count = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery("SELECT Count(invoice_id) AS QuantityExport FROM Invoice ", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    @SuppressLint("Range") int getExport = Integer.parseInt(cursor.getString(cursor.getColumnIndex("QuantityExport")));
                    count.add(getExport);
                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("ERROR", "Lỗi đếm hoá đơn");
        } finally {
            sql.endTransaction();
        }
        return count.get(0);
    }

    public Invoice getInvoiceByTableID(String tableID) {
        return getInvoice("SELECT * FROM Invoice WHERE table_id = ?", tableID).get(0);
    }

    @SuppressLint("Range")
    public int getTotalBill(String startDay, String endDay) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = sql.rawQuery("SELECT SUM(total_bill) as TurnOver FROM Invoice WHERE date_created BETWEEN ? AND ?", new String[]{startDay, endDay});

        cursor.moveToFirst();

        while (cursor.moveToNext()) {
            try {
                int doanhThu = Integer.parseInt(cursor.getString(cursor.getColumnIndex("TurnOver")));
                list.add(doanhThu);
            } catch (Exception e) {
                list.add(0);
            }

        }
        return list.get(0);
    }


}
