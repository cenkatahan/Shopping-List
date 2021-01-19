package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.concurrent.CopyOnWriteArrayList;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    private Context context;


    private static final String DB_NAME = "PRODUCT.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AMOUNT = "amount";



    public ProductDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_createTable = " CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AMOUNT + " TEXT)";

        db.execSQL(query_createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public void refreshCursor(Cursor newCursor){

    }

    public Cursor readAllProducts(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query_returnAll = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = null;
        if(database != null){
            cursor = database.rawQuery(query_returnAll, null);
        }

        return cursor;
    }

    public void addProduct(String name, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues productValues = new ContentValues();

        productValues.put(COLUMN_NAME, name);
        productValues.put(COLUMN_AMOUNT, amount);

        db.insert(TABLE_NAME, null, productValues);
    }

    public void deleteTheProduct(String selectedItemId){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COLUMN_ID + " = ? ", new String[]{selectedItemId});
    }

}
