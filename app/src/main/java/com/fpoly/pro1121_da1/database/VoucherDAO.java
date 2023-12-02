package com.fpoly.pro1121_da1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Voucher;

import java.util.ArrayList;

public class VoucherDAO {
    Context context;
    Dbhelper dbhelper;

    public VoucherDAO(Context context, Dbhelper dbhelper) {
        this.context = context;
        this.dbhelper = dbhelper;
    }

    public boolean insertVoucher(Voucher voucher) {

        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put("price_reduce", voucher.getPriceReduce());
        value.put("date_expiry", voucher.getDateExpiry());

        long result = sql.insert("Voucher", null, value);
        if (result > 0) {
            Toast.makeText(context, "Thêm voucher thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Thêm voucher không thành công", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public boolean updateVoucher(Voucher voucher, String messPositive, String messNegative) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put("price_reduce", voucher.getPriceReduce());
        value.put("date_expiry", voucher.getDateExpiry());

        long result = sql.update("Voucher", value, "voucher_id = ?", new String[]{String.valueOf(voucher.getVoucherID())});
        if (result > 0) {
            Toast.makeText(context, messPositive, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, messNegative, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean deleteVoucher(int voucherID, String messPositive, String messNegative) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        long result = sql.delete("Voucher", "voucher_id = ?", new String[]{String.valueOf(voucherID)});
        if (result > 0) {
            Toast.makeText(context, messPositive, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, messNegative, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public ArrayList<Voucher> getVoucher(String query, String... agrs) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<Voucher> list = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery(query, agrs);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                int getVoucherID = cursor.getInt(0);
                int getPriceReduce = cursor.getInt(1);
                String getDateExpiry = cursor.getString(2);

                list.add(new Voucher(getVoucherID, getPriceReduce, getDateExpiry));
                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sql.endTransaction();
        }
        return list;

    }


    public Voucher getVoucherByID(String voucherID) {
        ArrayList<Voucher> list = getVoucher("SELECT * FROM Voucher WHERE voucher_id = ?", voucherID);

        return list.get(0);
    }

    public ArrayList<Voucher> getAllVoucher() {
        return getVoucher("SELECT * FROM Voucher");
    }
}
