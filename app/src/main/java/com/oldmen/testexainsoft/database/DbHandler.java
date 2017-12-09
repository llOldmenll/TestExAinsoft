package com.oldmen.testexainsoft.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.oldmen.testexainsoft.model.Product;

import java.util.ArrayList;
import java.util.List;

import static com.oldmen.testexainsoft.utills.Constants.*;

/**
 * Created by MVP on 09.12.2017.
 */

public class DbHandler extends SQLiteOpenHelper {


    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PRICE + " REAL" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(sqLiteDatabase);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, product.getmId());
        values.put(KEY_NAME, product.getmName());
        values.put(KEY_PRICE, product.getmPrice());

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public Product getProduct(int id) {
        Product product = new Product();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTS,
                new String[]{KEY_ID, KEY_NAME, KEY_PRICE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            product.setmId(cursor.getInt(0));
            product.setmName(cursor.getString(1));
            product.setmPrice(cursor.getDouble(2));
            cursor.close();
        }

        return product;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_PRODUCTS, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setmId(cursor.getInt(0));
                product.setmName(cursor.getString(1));
                product.setmPrice(cursor.getDouble(2));
                productList.add(product);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return productList;
    }

    public int getProductsCount() {
        int count;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_PRODUCTS, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getmName());
        values.put(KEY_PRICE, product.getmPrice());

        db.update(TABLE_PRODUCTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(product.getmId())});
    }

    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_ID + " = ?",
                new String[]{String.valueOf(product.getmId())});
        db.close();
    }
}
