package com.fpoly.pro1121_da1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fpoly.pro1121_da1.model.Drink;
import com.fpoly.pro1121_da1.model.Table;

import java.util.ArrayList;

public class TableDAO {
    Context context;
    Dbhelper dbhelper;

    public TableDAO(Context context, Dbhelper dbhelper) {
        this.context = context;
        this.dbhelper = dbhelper;
    }

    public boolean insertTable(Table table){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("table_id", table.getTableID());
        values.put("status", table.getStatus());
        long result = sql.insert("TableDrink", null, values);
        if (result > 0){
            Toast.makeText(context, "Thêm bàn thành công", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(context, "Thêm bàn thất bại", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean deleteTable(String tableID){
        SQLiteDatabase sql = dbhelper.getWritableDatabase();

        long result = sql.delete("TableDrink", "table_id = ?", new String[]{tableID});
        if (result > 0){
            Toast.makeText(context, "Xoá bàn thành công", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(context, "Xoá bàn thất bại", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public ArrayList<Table> getTable(String query, String... agrs) {
        SQLiteDatabase sql = dbhelper.getWritableDatabase();
        ArrayList<Table> list = new ArrayList<>();

        sql.beginTransaction();
        try {
            Cursor cursor = sql.rawQuery(query, agrs);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                   String getTableID = cursor.getString(0);
                   int getStatus = cursor.getInt(1);
                   list.add(new Table(getTableID, getStatus));
                } while (cursor.moveToNext());
            }
            sql.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            sql.endTransaction();
        }
        return list;

    }

    public ArrayList<Table> getAllTable(){
        return getTable("SELECT * FROM TableDrink");
    }
    public Table getTableByID(String tableID){
        return getTable("SELECT * FROM TableDrink WHERE table_id = ?", tableID).get(0);
    }
}
