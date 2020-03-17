package com.filip.shoppingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

import com.filip.shoppingapp.database.model.ShoppingList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ShoopingList.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ShoppingList.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ShoppingList.TABLE_NAME);
        onCreate(db);
    }

    public long insertProduct(String name,int quantity) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ShoppingList.COLUMN_NAME, name);
        values.put(ShoppingList.COLUMN_QUANTITY, quantity);
        long id = db.insert(ShoppingList.TABLE_NAME, null, values);
        Log.d("INSERTED PRODUCT", values.toString());
        db.close();

        return id;
    }
    public ShoppingList getProduct(long product_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " +  ShoppingList.TABLE_NAME  + " WHERE "
                + ShoppingList.COLUMN_ID + " = " + product_id;

        Log.e("Query", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        ShoppingList pt = new ShoppingList();
        pt.setId(c.getInt(c.getColumnIndex(ShoppingList.COLUMN_ID)));
        pt.setName((c.getString(c.getColumnIndex(ShoppingList.COLUMN_NAME))));
        pt.setQuantity(c.getInt(c.getColumnIndex(ShoppingList.COLUMN_QUANTITY)));

        return pt;
    }

    public ArrayList<ShoppingList> getAllProducts() {
        ArrayList<ShoppingList> shoppingLists = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ShoppingList.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
               ShoppingList list = new ShoppingList();
                list.setId(cursor.getInt(cursor.getColumnIndex(ShoppingList.COLUMN_ID)));
                list.setName(cursor.getString(cursor.getColumnIndex(ShoppingList.COLUMN_NAME)));
                list.setQuantity(cursor.getInt(cursor.getColumnIndex(ShoppingList.COLUMN_QUANTITY)));
                shoppingLists.add(list);
            } while (cursor.moveToNext());
        }

        db.close();


        return shoppingLists;
    }
    public int updateProduct(ShoppingList product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ShoppingList.COLUMN_QUANTITY, product.getQuantity());


        return db.update(ShoppingList.TABLE_NAME, values, ShoppingList.COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
    }

    public void deleteProduct(ShoppingList product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ShoppingList.TABLE_NAME, ShoppingList.COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();
    }

    public int getProductCount() {
        String countQuery = "SELECT  * FROM " + ShoppingList.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }

}
